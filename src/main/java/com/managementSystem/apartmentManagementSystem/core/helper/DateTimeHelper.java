package com.managementSystem.apartmentManagementSystem.core.helper;

import java.time.LocalDate;
import java.time.Period;

public class DateTimeHelper {

    public static int findAgeFromBirthdate(LocalDate birthdate) {
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(birthdate, currentDate);
        return period.getYears();
    }
}
