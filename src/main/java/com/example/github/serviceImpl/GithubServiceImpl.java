package com.example.github.serviceImpl;

import com.example.github.dto.GithubRepositoryDTO;
import com.example.github.exception.GithubUserNotFoundException;
import com.example.github.model.GithubBranch;
import com.example.github.model.GithubRepository;
import com.example.github.service.GithubService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class GithubServiceImpl implements GithubService {
    private final String GITHUB_API_URL = "https://api.github.com";
    private final RestTemplate restTemplate;
    private HttpHeaders headers;

    public GithubServiceImpl() {
        this.restTemplate = new RestTemplate();
        headers = new HttpHeaders();
    }

    @Value("${github.personalAccessToken:}")
    private String personalAccessToken;

    @Override
    public List<GithubRepositoryDTO> getUserRepositories(String username) {
        String url = GITHUB_API_URL + "/users/" + username + "/repos?type=owner";
        HttpEntity<String> entity = createHttpEntity();

        try {
            ResponseEntity<GithubRepository[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, GithubRepository[].class);

            if (response.getStatusCode() == HttpStatus.OK) {
                List<GithubRepository> repositories = Arrays.asList(Objects.requireNonNull(response.getBody()));

                // Fetch branch information for each repository
                for (GithubRepository repository : repositories) {
                    List<GithubBranch> branches = fetchBranchesForRepository(username, repository.getName());
                    repository.setBranches(branches);
                }

                return repositories.stream()
                        .map(GithubRepository::toDto)
                        .collect(Collectors.toList());
            } else {
                return null;
            }

        } catch (HttpClientErrorException.NotFound notFoundException) {
            throw new GithubUserNotFoundException("GitHub user not found: " + username);
        }
    }

    private List<GithubBranch> fetchBranchesForRepository(String username, String repoName) {
        HttpEntity<String> entity = createHttpEntity();

        String url = GITHUB_API_URL + "/repos/" + username + "/" + repoName + "/branches";
        ResponseEntity<GithubBranch[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, GithubBranch[].class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return Arrays.asList(Objects.requireNonNull(response.getBody()));
        } else {
            throw new RuntimeException("Failed to retrieve branches for repository: " + repoName);
        }
    }

    private HttpEntity<String> createHttpEntity() {

        if (personalAccessToken.isEmpty()) {
            return null;
        }

        headers.set("Authorization", "Bearer " + personalAccessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return entity;
    }
}
