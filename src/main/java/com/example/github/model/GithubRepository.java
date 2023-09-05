package com.example.github.model;

import com.example.github.dto.GithubBranchDTO;
import com.example.github.dto.GithubRepositoryDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class GithubRepository {

    private Owner owner;

    private String name;

    private List<GithubBranch> branches;

    public GithubRepositoryDTO toDto() {
        List<GithubBranchDTO> branchDTOs = branches.stream()
                .map(GithubBranch::toDto)
                .collect(Collectors.toList());

        return GithubRepositoryDTO.builder()
                .name(this.name)
                .ownerLogin(this.owner.login)
                .branches(branchDTOs)
                .build();
    }

    @Data
    public static class Owner {
        String login;
    }
}