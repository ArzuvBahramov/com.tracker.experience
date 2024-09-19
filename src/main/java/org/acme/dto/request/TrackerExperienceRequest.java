package org.acme.dto.request;

import lombok.Builder;
import org.acme.dto.ProjectDto;
import org.acme.dto.TechnologiesDto;

@Builder
public record TrackerExperienceRequest(
        TechnologiesDto technologies,
        ProjectDto projects
){

}
