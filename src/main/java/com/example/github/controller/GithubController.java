package com.example.github.controller;

import com.example.github.dto.GithubRepositoryDTO;
import com.example.github.service.GithubService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@RestController
@RequestMapping("/github")
public class GithubController {

    private GithubService githubService;

    GithubController(GithubService githubService) {
        this.githubService = githubService;
    }

    @GetMapping("/{username}/repositories")
    public List<GithubRepositoryDTO> getUserRepositories(@PathVariable String username) throws HttpMediaTypeNotAcceptableException {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String acceptHeader = request.getHeader("Accept");

        if (acceptHeader != null && !acceptHeader.contains(MediaType.APPLICATION_JSON_VALUE)) {
            throw new HttpMediaTypeNotAcceptableException("Not acceptable format. Please use 'application/json'.");
        }

        return githubService.getUserRepositories(username);
    }
}
