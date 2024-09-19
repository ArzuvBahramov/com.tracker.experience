package org.acme.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ProjectDto(
        String name,
        String description,
        String role,
        String period,
        List<String> responsibilities,
        List<String> environment
) {
}
