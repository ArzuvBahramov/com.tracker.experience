package org.acme.dto;

import java.util.List;

public record ProjectDto(
        String name,
        String description,
        String role,
        String period,
        List<String> responsibilities,
        List<String> environment
) {
}
