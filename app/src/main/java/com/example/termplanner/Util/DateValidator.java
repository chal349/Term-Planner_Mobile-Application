package com.example.termplanner.Util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateValidator {

    public boolean isDateValid(String date){

        boolean validity = false;

        if (date.matches("\\d{2}/\\d{2}/\\d{4}")){
            validity = true;
        }
        return validity;
    }

    public boolean isDateOrderValid(String startDate, String endDate){

        boolean validity = false;

        DateTimeFormatter formatStart = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.ENGLISH);
        LocalDate localStart = LocalDate.parse(startDate, formatStart);

        DateTimeFormatter formatEnd = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.ENGLISH);
        LocalDate localEnd = LocalDate.parse(endDate, formatEnd);

        if (localStart.isBefore(localEnd)){
            validity = true;
        }
        return validity;
    }
}
