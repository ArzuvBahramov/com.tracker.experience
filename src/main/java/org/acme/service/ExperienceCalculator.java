package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.acme.dto.ProjectDto;
import org.acme.dto.TechnologiesDto;
import org.acme.dto.TechnologyExperience;
import org.acme.dto.TrackerExperienceMatrix;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
@Slf4j
public class ExperienceCalculator {
    private final static Integer START_DATE_INDEX = 0;
    private final static Integer END_DATE_INDEX = 1;
    @Inject
    DateManagement dateManagement;
    public TrackerExperienceMatrix tracker(List<TechnologiesDto> technologies, List<ProjectDto> projects) {

        Map<String, List<TechnologyExperience>> experienceMap = buildMatrix(technologies);

        for (ProjectDto project : projects) {
            String period = project.period().trim();
            List<LocalDate> dates = dateManagement.parseStartDateAndEndDate(period);
            LocalDate startDate = dates.get(START_DATE_INDEX);
            LocalDate endDate = dates.get(END_DATE_INDEX);
            int years = (int) ChronoUnit.YEARS.between(startDate, endDate);

            List<String> environments = project.environment();
            experienceMap.values().stream()
                    .flatMap(Collection::stream)
                    .filter(techExp -> environments.contains(techExp.getName()))
                    .forEach(techExp -> {
                        techExp.setExperience(Math.addExact(techExp.getExperience(), years));
                        techExp.setYear(Math.max(techExp.getYear(), endDate.getYear()));
                    });

        }

        return TrackerExperienceMatrix.builder()
                .technologies(experienceMap)
                .build();
    }

    public Map<String, List<TechnologyExperience>> buildMatrix(List<TechnologiesDto> technologies) {
        log.info("Build Matrix");
        return technologies.stream()
                .collect(Collectors.toMap(
                        TechnologiesDto::name,
                        dto ->
                            dto.technologies().stream().map(tech -> TechnologyExperience.builder()
                                    .name(tech.trim())
                                    .experience(0)
                                    .year(0)
                                    .build()
                            ).collect(Collectors.toList()),
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                ));
    }


}
