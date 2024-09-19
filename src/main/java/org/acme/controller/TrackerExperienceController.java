package org.acme.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.acme.dto.TrackerExperienceMatrix;
import org.acme.dto.request.TrackerExperienceRequest;
import org.acme.service.TrackerExperienceService;

@Path("/v1/tracker-experience")
@Slf4j
public class TrackerExperienceController {
    @Inject
    TrackerExperienceService trackerExperienceService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response tracker(TrackerExperienceRequest trackerExperience) {
        log.info("Post request tracker is called!");
        TrackerExperienceMatrix matrix = trackerExperienceService.tracker(trackerExperience);
        return Response.ok(matrix).build();
    }

}
