package my.java8.time;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class CalcDate {
	private static final String DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";
    private static final DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    private static final DateTimeFormatter dateFormat8 = DateTimeFormatter.ofPattern(DATE_FORMAT);
    
	public static void main(String[] args) {
		// Get current date
        Date currentDate = new Date();
        System.out.println("date : " + dateFormat.format(currentDate));

        // convert date to localdatetime
        LocalDateTime localDateTime = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        System.out.println("localDateTime : " + dateFormat8.format(localDateTime));

        // plus one
        localDateTime = localDateTime.plusYears(1).plusMonths(1).plusDays(1);
        localDateTime = localDateTime.plusHours(1).plusMinutes(2).minusMinutes(1).plusSeconds(1);

        // convert LocalDateTime to date
        Date currentDatePlusOneDay = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        System.out.println("\nOutput : " + dateFormat.format(currentDatePlusOneDay));
        
        //minus one
        localDateTime = localDateTime.minusYears(1).minusMonths(1).minusDays(1);
        localDateTime = localDateTime.minusHours(1).minusMinutes(2).minusMinutes(1).minusSeconds(1);
        
        // convert LocalDateTime to date
        Date currentDateMinusOneDay = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        System.out.println("\nOutput : " + dateFormat.format(currentDateMinusOneDay));
	}
}
