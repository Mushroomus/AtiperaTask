package com.example.github.service;

import com.example.github.dto.GithubRepositoryDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GithubService {
    List<GithubRepositoryDTO> getUserRepositories(String username);
}
