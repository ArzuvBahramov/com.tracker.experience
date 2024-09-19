package org.acme.service;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.acme.dto.TrackerExperienceMatrix;
import org.acme.dto.request.TrackerExperienceRequest;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class TrackerExperienceServiceTest {
    @Inject
    TrackerExperienceService trackerExperienceService;

    @Test
    public void testTrackerReturnsNotNull() {
        TrackerExperienceRequest request = TrackerExperienceRequest.builder().build();
        TrackerExperienceMatrix result = trackerExperienceService.tracker(request);

        assertNotNull(result, "Result should not be null");
    }
}
