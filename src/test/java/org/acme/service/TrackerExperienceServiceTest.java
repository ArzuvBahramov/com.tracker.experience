package org.acme.service;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.acme.dto.ProjectDto;
import org.acme.dto.TechnologiesDto;
import org.acme.dto.TechnologyExperience;
import org.acme.dto.TrackerExperienceMatrix;
import org.acme.dto.request.TrackerExperienceRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class TrackerExperienceServiceTest {
    @Inject
    TrackerExperienceService trackerExperienceService;

    private TechnologiesDto technologies;
    private ProjectDto projectDto;
    private TrackerExperienceRequest request;
    @BeforeEach
    public void setupTechnologies() {
        technologies = TechnologiesDto.builder()
                .name("Programming languages")
                .technologies(Arrays
                        .stream("Java, Java 8,Java 11, Java 17, Python, JavaScript, TypeScript"
                                .split(","))
                        .toList())
                .build();

        projectDto = ProjectDto.builder()
                .name("INSURANCE COMPANY SYSTEM")
                .description("Project for one of the biggest European insurance companies. It is a big Enterprise application for internal employees from different countries that gives them efficient tools to work with clients and various documents. We had a big remote team from anywhere and worked together with QA’s, DevOps’es, and support.\n")
                .role("Software Engineer")
                .period("12.2018 – 02.2021")
                .environment(Arrays.stream(
                        "Java 8, JavaScript, TypeScript, MySQL, FlyWay, Docker-Compose, Docker, NodeJS, Angular, Redux, Jest, HTML, CSS,TestContainers, JUnit, Mockito, Jenkins, Maven, Lombok, Swagger, Git, GitLab, Trello".split(", ")
                ).toList())
                .build();

        request = TrackerExperienceRequest.builder()
                .projects(List.of(projectDto))
                .technologies(List.of(technologies))
                .build();
    }

    @Test
    public void testTrackerReturnsNotNull() {
        TrackerExperienceMatrix result = trackerExperienceService.tracker(request);

        assertNotNull(result, "Result should not be null");
        assertNotNull(result.technologies(), "Result of matrix should not be null");
    }

    @Test
    public void textTrackerTechnologies() {
        TrackerExperienceMatrix result = trackerExperienceService.tracker(request);

        assertNotNull(result.technologies().get("Programming languages"), "Programming languages is not exists");

        List<TechnologyExperience> experiences = result.technologies().get("Programming languages");
        TechnologyExperience experience = experiences.get(0);

        assertNotNull(experience.getName(), "Technology is not exists");
        assertEquals(experience.getExperience(), 2);
        assertEquals(experience.getYear(), 2021);
    }
}
