package org.acme.service;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.acme.dto.ProjectDto;
import org.acme.dto.TechnologiesDto;
import org.acme.dto.TechnologyExperience;
import org.acme.dto.request.TrackerExperienceRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class ExperienceCalculatorTest {
    @Inject
    ExperienceCalculator experienceCalculator;

    private TechnologiesDto technologies;
    @BeforeEach
    public void setupTechnologies() {
        technologies = TechnologiesDto.builder()
                .name("Programming languages")
                .technologies(Arrays
                        .stream("Java 8,Java 8,Java 11, Java 17, Python, JavaScript, TypeScript"
                                .split(","))
                        .toList())
                .build();
    }

    @Test
    public void testBuildMatrix() {
        Map<String, List<TechnologyExperience>> experienceMap = experienceCalculator.buildMatrix(List.of(technologies));

        assertNotNull(experienceMap, "Result should not be null");
        assertNotNull(experienceMap.get("Programming languages"), "Result should not be null");
        assertNotNull(experienceMap.get("Programming languages").stream().filter(experience-> {
            return experience.getName().equals("Python");
        }), "Python is not exists in experiences map");
    }
}
