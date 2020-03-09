package my.java8.time;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Date -> Instant + System default time zone = LocalDate
 * Date -> Instant + System default time zone = LocalDateTime
 * Date -> Instant + System default time zone = ZonedDateTime
 * 
 * @author F
 *
 */
public class ConvertDateToLocalDate {

	public static void main(String[] args) {
		convert();
		trevnoc();
	}
	
	public static void convert() {

        //Asia/ShanghaiAsia
        ZoneId defaultZoneId = ZoneId.systemDefault();
        System.out.println("System Default TimeZone : " + defaultZoneId);

        //toString() append +8 automatically.
        Date date = new Date();
        System.out.println("date : " + date);

        //1. Convert Date -> Instant
        Instant instant = date.toInstant();
        System.out.println("instant : " + instant); //Zone : UTC+0

        //2. Instant + system default time zone + toLocalDate() = LocalDate
        LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();
        System.out.println("localDate : " + localDate);

        //3. Instant + system default time zone + toLocalDateTime() = LocalDateTime
        LocalDateTime localDateTime = instant.atZone(defaultZoneId).toLocalDateTime();
        System.out.println("localDateTime : " + localDateTime);

        //4. Instant + system default time zone = ZonedDateTime
        ZonedDateTime zonedDateTime = instant.atZone(defaultZoneId);
        System.out.println("zonedDateTime : " + zonedDateTime);

    }
	public static void trevnoc() {
		
		//Asia/ShanghaiAsia
        ZoneId defaultZoneId = ZoneId.systemDefault();
        System.out.println("System Default TimeZone : " + defaultZoneId);

        LocalDate localDate = LocalDate.now();
        Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
        System.out.println("\n1. LocalDate -> Date");
        System.out.println("localDate : " + localDate);
        System.out.println("date : " + date);

        LocalDateTime localDateTime = LocalDateTime.now();
        Date date2 = Date.from(localDateTime.atZone(defaultZoneId).toInstant());
        System.out.println("\n2. LocalDateTime -> Date");
        System.out.println("localDateTime : " + localDateTime);
        System.out.println("date2 : " + date2);

        ZonedDateTime zonedDateTime = localDateTime.atZone(defaultZoneId);
        Date date3 = Date.from(zonedDateTime.toInstant());
        System.out.println("\n3. ZonedDateTime -> Date");
        System.out.println("zonedDateTime : " + zonedDateTime);
        System.out.println("date3 : " + date3);

		
	}

}
