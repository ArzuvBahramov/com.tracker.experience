package org.acme.service;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class DateManagementTest {
    @Inject
    DateManagement dateManagement;

    @Test
    public void testParseStartDateAndEndDateWithBothDates() {
        String period = "12.2018 - 02.2021";

        List<LocalDate> result = dateManagement.parseStartDateAndEndDate(period);

        assertEquals(LocalDate.of(2018, 12, 1), result.get(0));
        assertEquals(LocalDate.of(2021, 2, 1), result.get(1));
    }

    @Test
    public void testParseStartDateAndEndDateWithTillNowEN() {
        String period = "01.2020 till now";

        List<LocalDate> result = dateManagement.parseStartDateAndEndDate(period);

        assertEquals(LocalDate.of(2020, 1, 1), result.get(0));
        assertEquals(LocalDate.now(), result.get(1));
    }

    @Test
    public void testParseStartDateAndEndDateWithTillNowRU() {
        String period = "03.2019 по настоящее время";

        List<LocalDate> result = dateManagement.parseStartDateAndEndDate(period);

        assertEquals(LocalDate.of(2019, 3, 1), result.get(0));
        assertEquals(LocalDate.now(), result.get(1));
    }
}
