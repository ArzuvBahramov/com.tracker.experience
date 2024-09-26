package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.dto.TechnologyExperience;
import org.acme.service.enums.IncludeGroupName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class OptimizeExperience {
    private final Set<String> groupKeys;
    public OptimizeExperience() {
        groupKeys = Arrays.stream(IncludeGroupName.values()).map(IncludeGroupName::getValue).collect(Collectors.toSet());
    }

    public void optimize(Map<String, List<TechnologyExperience>> matrix) {
        for (Map.Entry<String, List<TechnologyExperience>> entry: matrix.entrySet()) {
            String key = entry.getKey();
            if (groupKeys.contains(key)) {
                continue;
            }

            List<TechnologyExperience> experiences = entry.getValue();
            optimizeExperiences(experiences);
        }
    }

    private void optimizeExperiences(List<TechnologyExperience> experiences) {
        String experiencesString = experiences.stream().map(TechnologyExperience::getName).collect(Collectors.joining(", "));
        List<TechnologyExperience> removeExperiences = new ArrayList<>();
        for (TechnologyExperience experience: experiences) {
            String baseTech = experience.getName();
            String [] splitText = baseTech.split(" ");
            if (splitText.length == 1) {
                continue;
            }

            boolean removeExperienceFlag = false;
            List<String> splitTech = getSplitTech(splitText);
            for (String tech: splitTech) {
                int sizeOfSubText = sizeOfSubText(experiencesString, tech.trim());
                if (sizeOfSubText == 0) {
                    break;
                }
                if (!checkTechnology(experiencesString, tech)) {
                    continue;
                }
                if (sizeOfSubText > 1) {
                    removeExperienceFlag = true;
                    experiences.stream().filter(exp -> exp.getName().equals(tech.trim())).findFirst().ifPresent(exp-> {
                                exp.setYear(Math.max(exp.getYear(), experience.getYear()));
                            }
                    );
                    break;
                }
            }

            if (removeExperienceFlag) {
                removeExperiences.add(experience);
            }
        }

        experiences.removeIf(removeExperiences::contains);
    }

    private List<String> getSplitTech(String [] splitText) {
        List<String> result = new ArrayList<>();

        for (int i = 0; i < splitText.length - 1; i++) {
            StringBuilder appendResult = new StringBuilder();

            for (int j = 0; j < splitText.length - i - 1; j++) {
                appendResult.append(splitText[j]).append(" ");
            }

            result.add(appendResult.toString());
        }
        return result;
    }

    private Integer sizeOfSubText(String text, String sub) {
        if (text == null || sub == null || sub.isEmpty()) {
            return 0;
        }

        int count = 0;
        int index = 0;

        while ((index = text.indexOf(sub, index)) != -1) {
            count++;
            index += sub.length();
        }

        return count;
    }

    private boolean checkTechnology(String text, String tech) {
        return Arrays.stream(text.split(", ")).filter(sub -> sub.equals(tech.trim())).count() == 1;
    }
}
