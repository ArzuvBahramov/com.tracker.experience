package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.acme.dto.ProjectDto;
import org.acme.dto.TechnologiesDto;
import org.acme.dto.TrackerExperienceMatrix;
import org.acme.dto.request.TrackerExperienceRequest;

import java.util.List;

@ApplicationScoped
@Slf4j
public class TrackerExperienceService {

    @Inject
    ExperienceCalculator experienceCalculator;

    public TrackerExperienceMatrix tracker(TrackerExperienceRequest trackerExperience) {
        List<TechnologiesDto> technologies = trackerExperience.technologies();
        List<ProjectDto> projects = trackerExperience.projects();

        return experienceCalculator.tracker(technologies, projects);
    }
}
