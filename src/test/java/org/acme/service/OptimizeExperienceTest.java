package org.acme.service;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.acme.dto.TechnologyExperience;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class OptimizeExperienceTest {
    @Inject
    OptimizeExperience optimizeExperience;

    @Test
    void testOptimize_whenGroupKeyNotPresent_shouldOptimize() {
        // Given a map without group keys that should be optimized
        List<TechnologyExperience> techExperiences = new ArrayList<>(List.of(
                TechnologyExperience.builder().name("Java Developer").year(2).experience(2024).build(),
                TechnologyExperience.builder().name("Java").year(2).experience(2024).build(),
                TechnologyExperience.builder().name("Spring Boot Developer").year(2).experience(2024).build()
        ));
        Map<String, List<TechnologyExperience>> matrix = new HashMap<>();
        matrix.put("techKey", techExperiences);

        // When optimizing
        optimizeExperience.optimize(matrix);

        // Then only the relevant TechnologyExperience objects should remain
        List<TechnologyExperience> result = matrix.get("techKey");
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(te -> te.getName().equals("Java")));
        assertTrue(result.stream().anyMatch(te -> te.getName().equals("Spring Boot Developer")));
    }

    @Test
    void testOptimize_whenSubTextAppearsMoreThanOnce_shouldRemoveExperience() {
        // Given a map with experiences that contain repeated substrings
        List<TechnologyExperience> techExperiences = new ArrayList<>(List.of(
                TechnologyExperience.builder().name("Google Cloud Platform").year(2).experience(2024).year(2).experience(2024).build(),
                TechnologyExperience.builder().name("Google Cloud").year(2).experience(2024).build(),
                TechnologyExperience.builder().name("Cloud").year(2).experience(2024).build()
        ));
        Map<String, List<TechnologyExperience>> matrix = new HashMap<>();
        matrix.put("techKey", techExperiences);

        // When optimizing
        optimizeExperience.optimize(matrix);

        // Then the shorter or redundant TechnologyExperience objects should be removed
        List<TechnologyExperience> result = matrix.get("techKey");
        assertEquals(2, result.size());
        assertEquals("Google Cloud", result.get(0).getName());
    }

    @Test
    void testOptimize_whenNoSubTextMatch_shouldNotRemoveExperience() {
        // Given a map with experiences that don't contain repeated substrings
        List<TechnologyExperience> techExperiences = new ArrayList<>(List.of(
                TechnologyExperience.builder().name("AWS Lambda").year(2).experience(2024).build(),
                TechnologyExperience.builder().name("Google Cloud Platform").year(2).experience(2024).build(),
                TechnologyExperience.builder().name("Azure DevOps").year(2).experience(2024).build()
        ));
        Map<String, List<TechnologyExperience>> matrix = new HashMap<>();
        matrix.put("techKey", techExperiences);

        // When optimizing
        optimizeExperience.optimize(matrix);

        // Then no TechnologyExperience objects should be removed
        List<TechnologyExperience> result = matrix.get("techKey");
        assertEquals(3, result.size());
    }
}
