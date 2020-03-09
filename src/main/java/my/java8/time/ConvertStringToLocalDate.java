package my.java8.time;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ConvertStringToLocalDate {

	public static void main(String[] args) {
		String date = "2016-08-16";
        LocalDate localDate = LocalDate.parse(date);
        System.out.println(localDate);
        LocalDate localDate2 = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.println(localDate2);
        
        String date2 = "2011-12-03T10:15:30";
        LocalDateTime localDate3 = LocalDateTime.parse(date2);
        System.out.println(localDate3);
        LocalDateTime localDate4 = LocalDateTime.parse(date2, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        System.out.println(localDate4);
        
        String dateInString = "2016-08-16T15:23:01Z";
        Instant instant = Instant.parse(dateInString);
        System.out.println("Instant : " + instant);
        System.out.println("ZoneId : " + ZoneId.systemDefault());
        System.out.println(LocalDateTime.ofInstant(instant, ZoneId.systemDefault()));
        //get date time only
        LocalDateTime result = LocalDateTime.ofInstant(instant, ZoneId.of(ZoneOffset.UTC.getId()));
        //get localdate
        System.out.println("LocalDate : " + result.toLocalDate());
        //get date time + timezone
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of("Asia/Tokyo"));
        System.out.println(zonedDateTime);
        //get date time + timezone
        ZonedDateTime zonedDateTime2 = instant.atZone(ZoneId.of("Europe/Athens"));
        System.out.println(zonedDateTime2);
        
        String date3 = "2016-08-16T10:15:30+08:00";
        ZonedDateTime result2 = ZonedDateTime.parse(date3, DateTimeFormatter.ISO_DATE_TIME);
        System.out.println("ZonedDateTime : " + result2);
        System.out.println("TimeZone : " + result2.getZone());
        System.out.println("LocalDate : " + result2.toLocalDate());

	}

}
