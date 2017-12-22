package my.java8.time;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/*
Letter	Description	       		Examples
	y	Year	             		  2013
	M	Month in year	July,  		  07, 7
	d	Day in month	              1-31
	E	Day name in week	Friday,  Sunday
	a	Am/pm marker			  AM, PM
	H	Hour in day	              0-23
	h	Hour in am/pm	          1-12
	m	Minute in hour	              0-60
	s	Second in minute	          0-60
*/
public class ConvertStringToDate {

	public static void main(String[] args) {
		String dateInString = "2014-10-05T15:23:01Z";

        Instant instant = Instant.parse(dateInString);

        System.out.println(instant);

        //get date time only
        LocalDateTime result = LocalDateTime.ofInstant(instant, ZoneId.of(ZoneOffset.UTC.getId()));

        System.out.println(result);

        //get date time + timezone
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of("Africa/Tripoli"));
        System.out.println(zonedDateTime);

        //get date time + timezone
        ZonedDateTime zonedDateTime2 = instant.atZone(ZoneId.of("Europe/Athens"));
        System.out.println(zonedDateTime2);

	}

}
