package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.dto.TrackerExperienceMatrix;
import org.acme.dto.request.TrackerExperienceRequest;

@ApplicationScoped
public class TrackerExperienceService {
    public TrackerExperienceMatrix tracker(TrackerExperienceRequest trackerExperience) {
        return TrackerExperienceMatrix.builder().build();
    }
}
