package Utilities;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class ConvertTime {

    public static ZonedDateTime getEndTimeZoned(String endTime, LocalDate endDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh':'mm a");
        LocalTime selectedTime = LocalTime.parse(endTime, formatter);
        ZoneId myZoneId = ZoneId.of(TimeZone.getDefault().getID());
        ZonedDateTime localZoneDateAndTime = ZonedDateTime.of(endDate,selectedTime,myZoneId);
        return localZoneDateAndTime;
    }
    public static LocalTime getEndESTTime(String endTime, LocalDate endDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh':'mm a");
        ZoneId ESTZone = ZoneId.of("US/Eastern");
        ZonedDateTime selectedTimeToEST = getEndTimeZoned(endTime, endDate).withZoneSameInstant(ESTZone);
        String ESTString = selectedTimeToEST.format(formatter);
        LocalTime ESTTimeOnly = LocalTime.parse(ESTString, formatter);
        return  ESTTimeOnly;
    }
    public static Instant endTimeToGMT(String endTime, LocalDate endDate){
        Instant selectedEndTimeToGMT = getEndTimeZoned(endTime, endDate).toInstant();
        return selectedEndTimeToGMT;
    }
    public static ZonedDateTime getStartTimeZoned(String startTime, LocalDate startDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh':'mm a");
        LocalTime selectedTime = LocalTime.parse(startTime, formatter);
        ZoneId myZoneId = ZoneId.of(TimeZone.getDefault().getID());
        ZonedDateTime localZoneDateAndTime = ZonedDateTime.of(startDate,selectedTime,myZoneId);
        return localZoneDateAndTime;
    }
    public static LocalTime getStartESTTime(String startTime, LocalDate startDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh':'mm a");
        ZoneId ESTZone = ZoneId.of("US/Eastern");
        ZonedDateTime selectedTimeToEST = getStartTimeZoned(startTime,startDate).withZoneSameInstant(ESTZone);
        String ESTString = selectedTimeToEST.format(formatter);
        LocalTime ESTTimeOnly = LocalTime.parse(ESTString, formatter);
        return  ESTTimeOnly;
    }
    public static Instant startTimeToGMT(String startTime, LocalDate startDate){
        Instant selectedStartTimeToGMT = getStartTimeZoned(startTime,startDate).toInstant();
        return selectedStartTimeToGMT;
    }
}
