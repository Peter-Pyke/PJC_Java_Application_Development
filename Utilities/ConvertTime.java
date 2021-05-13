package Utilities;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

/**
 * The ConvertTime class hold multiple public methods that can be used to convert times.
 * */
public class ConvertTime {
    /**
     * The getEndTimeZoned method returns a ZonedDateTime when given a time in the form
     * of a string and a LocalDate.
     * @param endTime String of time the appointment will end
     * @param endDate LocalDate the appointment will end on
     * */
    public static ZonedDateTime getEndTimeZoned(String endTime, LocalDate endDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh':'mm a");
        LocalTime selectedTime = LocalTime.parse(endTime, formatter);
        ZoneId myZoneId = ZoneId.of(TimeZone.getDefault().getID());
        ZonedDateTime localZoneDateAndTime = ZonedDateTime.of(endDate,selectedTime,myZoneId);
        return localZoneDateAndTime;
    }
    /**
     * The getEndESTTime method returns a LocalTime in EST when given a endTime in the form of a string
     * and a LocalDate.
     * @param endDate LocalDate of when the appointment will end.
     * @param endTime time the appointment will end in the form of a formatted string.
     * */
    public static LocalTime getEndESTTime(String endTime, LocalDate endDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh':'mm a");
        ZoneId ESTZone = ZoneId.of("US/Eastern");
        ZonedDateTime selectedTimeToEST = getEndTimeZoned(endTime, endDate).withZoneSameInstant(ESTZone);
        String ESTString = selectedTimeToEST.format(formatter);
        LocalTime ESTTimeOnly = LocalTime.parse(ESTString, formatter);
        return  ESTTimeOnly;
    }
    /**
     * The endTimeToGMT method returns a LocalTime in GMT when given a endtime in the form of a formatted string
     * and a LocalDate the appointment will end on.
     * @param endDate LocalDate of when the appointment will end.
     * @param endTime time the appointment will end in the form of a formatted string.
     * */
    public static LocalTime endTimeToGMT(String endTime, LocalDate endDate){
        Instant endGMT = getEndTimeZoned(endTime, endDate).toInstant();
        ZoneId GMT = ZoneId.of("UTC");
        LocalTime selectedEndTimeGMT = LocalTime.ofInstant(endGMT,GMT);
        return selectedEndTimeGMT;
    }
    /**
     * The getStartTimeZoned returns a zonedDateTime when given a start time in the form of a formatted string
     * and a localDate the appointment will start on.
     * @param startDate start date of the appointment
     * @param startTime start time in the form of a formatted string
     * */
    public static ZonedDateTime getStartTimeZoned(String startTime, LocalDate startDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh':'mm a");
        LocalTime selectedTime = LocalTime.parse(startTime, formatter);
        ZoneId myZoneId = ZoneId.of(TimeZone.getDefault().getID());
        ZonedDateTime localZoneDateAndTime = ZonedDateTime.of(startDate,selectedTime,myZoneId);
        return localZoneDateAndTime;
    }
    /**
     * The getStartESTTime method returns a localtime when given a starttime in the form of a formatted string
     * and a  localDate the appointment will start on.
     * @param startDate start date of the appointment
     * @param startTime start time in the form of a formatted string
     * */
    public static LocalTime getStartESTTime(String startTime, LocalDate startDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh':'mm a");
        ZoneId ESTZone = ZoneId.of("US/Eastern");
        ZonedDateTime selectedTimeToEST = getStartTimeZoned(startTime,startDate).withZoneSameInstant(ESTZone);
        String ESTString = selectedTimeToEST.format(formatter);
        LocalTime ESTTimeOnly = LocalTime.parse(ESTString, formatter);
        return  ESTTimeOnly;
    }
    /**
     * The startTimeToGMT method returns a LocalTime in GMT when given a startTime in the form of a formatted
     * string and a LocalDate the appointment starts on.
     * @param startDate start date of the appointment
     * @param startTime start time in the form of a formatted string
     * */
    public static LocalTime startTimeToGMT(String startTime, LocalDate startDate){
        ZoneId GMT = ZoneId.of("GMT");
        Instant startGMT = getStartTimeZoned(startTime,startDate).toInstant();
        LocalTime selectedStartTimeToGMT = LocalTime.ofInstant(startGMT,GMT);
        return selectedStartTimeToGMT;
    }
    /**
     * The hoursOfOperation method returns false if the start or end time of the appointment is outside of the
     * hours of operation else it returns true.
     * @param startTime start time from combo box
     * @param startDate start date from start date picker
     * @param endTime end time from combo box
     * @param endDate end date from end date picker
     * */
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
