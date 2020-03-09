package my.java8.time;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * @author Binke Zhang
 * @date 2020/3/9 8:55
 */
public class DateUtils {
    /**
     *
     * @param localDate
     */
    public static Date localDate2Date(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneOffset.ofHours(8)).toInstant());
    }
    /**
     *
     * @param localDateTime
     */
    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneOffset.ofHours(8)).toInstant());
    }
    /**
     *
     * @param localDate
     */
    public static long localDate2Timestamp(LocalDate localDate) {
        return localDate.atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli();
    }
    /**
     *
     * @param localDateTime
     */
    public static long localDateTime2Timestamp(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneOffset.ofHours(8)).toInstant().toEpochMilli();
    }

    /**
     *
     * @param date
     */
    public static LocalDate date2LocalDate(Date date) {
        return date.toInstant().atZone(ZoneOffset.ofHours(8)).toLocalDate();
    }
    /**
     *
     * @param date
     */
    public static LocalDateTime date2LocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
    }
    /**
     *
     * @param timestamp
     */
    public static LocalDate timestamp2LocalDate(long timestamp) {
        return Instant.ofEpochMilli(timestamp).atZone(ZoneOffset.ofHours(8)).toLocalDate();
    }
    /**
     *
     * @param timestamp
     */
    public static LocalDateTime timestamp2LocalDateTime(long timestamp) {
        return Instant.ofEpochMilli(timestamp).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
    }

    public static void main(String[] args){
        System.out.println(LocalDate.now());
        System.out.println(localDate2Date(LocalDate.now()));
    }

}
