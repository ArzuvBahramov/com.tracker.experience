package org.acme.dto.request;

import lombok.Builder;
import org.acme.dto.ProjectDto;
import org.acme.dto.TechnologiesDto;

import java.util.List;

@Builder
public record TrackerExperienceRequest(
        List<TechnologiesDto> technologies,
        List<ProjectDto> projects
){

}
