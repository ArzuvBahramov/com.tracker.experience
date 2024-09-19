package org.acme.controller;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.acme.dto.ProjectDto;
import org.acme.dto.TechnologiesDto;
import org.acme.dto.TechnologyExperience;
import org.acme.dto.TrackerExperienceMatrix;
import org.acme.dto.request.TrackerExperienceRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
public class TrackerExperienceControllerTest {
    private TrackerExperienceRequest request;
    @BeforeEach
    public void setupTechnologies() {
        TechnologiesDto technologies = TechnologiesDto.builder()
                .name("Programming languages")
                .technologies(Arrays
                        .stream("Java 8,Java 11, Java 17, Python, JavaScript, TypeScript"
                                .split(","))
                        .toList())
                .build();

        ProjectDto projectDto = ProjectDto.builder()
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
    void tracker(){
        TrackerExperienceMatrix expectedMatrix = TrackerExperienceMatrix.builder()
                .technologies(Map.of("Programming languages", List.of(
                        TechnologyExperience.builder().name("Java").experience(2).year(2022).build(),
                        TechnologyExperience.builder().name("JavaScript").experience(1).year(2021).build())))
                .build();

        given()
                .contentType(ContentType.JSON)
                    .body(request)
                        .when().post("/tracker/experience")
                    .then()
                    .statusCode(200)
                    .contentType(ContentType.JSON)
                .body("technologies['Programming languages'][0].name", equalTo("Java 8"))
                .body("technologies['Programming languages'][0].experience", equalTo(2))
                .body("technologies['Programming languages'][0].year", equalTo(2021));
    }
}
