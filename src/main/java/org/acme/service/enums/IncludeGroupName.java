package org.acme.service.enums;

import lombok.Getter;

@Getter
public enum IncludeGroupName {
    ENGLISH_BACKEND("Backend"),
    ENGLISH_WITH_BACKEND("Back-end"),
    RUSSIAN_BACKEND("Бэкенд");

    private final String value;

    IncludeGroupName(String value) {
        this.value=value;
    }

}
