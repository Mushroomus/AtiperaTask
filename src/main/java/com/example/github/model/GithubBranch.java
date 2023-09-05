package com.example.github.model;

import com.example.github.dto.GithubBranchDTO;
import lombok.Data;

@Data
public class GithubBranch {

    private String name;

    private Commit commit;

    public GithubBranchDTO toDto() {
        return GithubBranchDTO.builder()
                .name(this.name)
                .lastCommitSha(this.commit.sha)
                .build();
    }

    @Data
    public static class Commit {
        private String sha;
    }
}