package Utilities;

import javafx.scene.control.Alert;

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
    public static LocalTime endTimeToGMT(String endTime, LocalDate endDate){
        Instant endGMT = getEndTimeZoned(endTime, endDate).toInstant();
        ZoneId GMT = ZoneId.of("GMT");
        LocalTime selectedEndTimeGMT = LocalTime.ofInstant(endGMT,GMT);
        return selectedEndTimeGMT;
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
    public static LocalTime startTimeToGMT(String startTime, LocalDate startDate){
        ZoneId GMT = ZoneId.of("GMT");
        Instant startGMT = getStartTimeZoned(startTime,startDate).toInstant();
        LocalTime selectedStartTimeToGMT = LocalTime.ofInstant(startGMT,GMT);
        return selectedStartTimeToGMT;
    }
    public static boolean hoursOfOperation(String startTime, String endTime, LocalDate startDate, LocalDate endDate){
        LocalTime open = LocalTime.of(8, 00);
        LocalTime close = LocalTime.of(22, 00);

        LocalTime myStartTime = ConvertTime.getStartESTTime(startTime,startDate);
        LocalTime myEndTime = ConvertTime.getEndESTTime(endTime,endDate);
        if (myStartTime.compareTo(open) < 0 || myStartTime.compareTo(close) >= 0 || myEndTime.compareTo(open) < 0 || myEndTime.compareTo(close) > 0) {
           return false;
        }
        else{
            return true;
        }
    }
}
