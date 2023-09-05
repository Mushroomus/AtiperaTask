package com.example.github.controller;

import com.example.github.dto.GithubRepositoryDTO;
import com.example.github.service.GithubService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/github")
public class GithubController {

    private GithubService githubService;

    GithubController(GithubService githubService) {
        this.githubService = githubService;
    }

    @GetMapping("/{username}/repositories")
    public List<GithubRepositoryDTO> getUserRepositories(@PathVariable String username) {
        return githubService.getUserRepositories(username);
    }
}
