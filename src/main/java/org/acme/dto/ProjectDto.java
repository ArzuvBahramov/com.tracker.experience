package org.acme.dto;

import lombok.Builder;

import java.util.List;
import java.util.Set;

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
