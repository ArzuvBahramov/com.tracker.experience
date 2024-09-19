package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.acme.dto.ProjectDto;
import org.acme.dto.TechnologiesDto;
import org.acme.dto.TechnologyExperience;
import org.acme.dto.TrackerExperienceMatrix;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
@Slf4j
public class ExperienceCalculator {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM.yyyy");
    public TrackerExperienceMatrix tracker(List<TechnologiesDto> technologies, List<ProjectDto> projects) {

        Map<String, List<TechnologyExperience>> experienceMap = buildMatrix(technologies);

        for (ProjectDto project : projects) {
            String period = project.period().trim();
            LocalDate startDate = parseDate(period.substring(0, period.indexOf(" ")).trim());
            LocalDate endDate = parseDate(period.substring(project.period().lastIndexOf(" ")).trim());
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

    private LocalDate parseDate(String dateString) {
        return YearMonth.parse(dateString, DATE_FORMATTER).atDay(1);
    }
}
