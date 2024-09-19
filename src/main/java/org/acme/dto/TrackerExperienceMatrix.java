package org.acme.dto;

import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record TrackerExperienceMatrix(
        Map<String, List<TechnologyExperience>> technologies
) {
}
