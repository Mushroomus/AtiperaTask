package com.example.github.dto;

import com.example.github.model.GithubBranch;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GithubRepositoryDTO {
    private String name;

    private String ownerLogin;

    private List<GithubBranchDTO> branches;
}