package org.acme.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Builder
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TechnologyExperience {
    String name;
    Integer year;
    Integer experience;
}
