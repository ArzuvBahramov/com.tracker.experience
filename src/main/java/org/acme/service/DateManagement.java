package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

@ApplicationScoped
public class DateManagement {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM.yyyy");
    private static final String TILL_NOW_RU = "по настоящее время";
    private static final String TILL_NOW_EN = "till now";
    public LocalDate parseDate(String dateString) {
        return YearMonth.parse(dateString, DATE_FORMATTER).atDay(1);
    }

    public List<LocalDate> parseStartDateAndEndDate(String period) {
        String dateString = period.substring(0, period.indexOf(" ")).trim();
        LocalDate startDate = parseDate(dateString);
        LocalDate endDate;

        if (period.toLowerCase().endsWith(TILL_NOW_EN) || period.toLowerCase().endsWith(TILL_NOW_RU)) {
            endDate = LocalDate.now();
        } else {
            dateString = period.substring(period.lastIndexOf(" ")).trim();
            endDate = parseDate(dateString);
        }

        return List.of(startDate, endDate);
    }
}
