package com.example.geektrust.util;


import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateValidator {

    public static boolean validateSubscriptionDate(String date) {
        boolean isValidDate = true;
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("d-M-yyyy");
        if(date!=null) {
            try {
                LocalDate.parse(date, dateFormat);
            } catch (DateTimeParseException ex) {
                isValidDate = false;
            } catch (DateTimeException de) {
                isValidDate = false;

            }
        }
        else isValidDate=false;
        return isValidDate;
    }
}
