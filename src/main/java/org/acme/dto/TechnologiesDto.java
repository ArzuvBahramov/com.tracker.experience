package org.acme.dto;

import java.util.List;

public record TechnologiesDto(
        String name,
        List<String> technologies
) {
}
