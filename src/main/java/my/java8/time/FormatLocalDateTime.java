package my.java8.time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormatLocalDateTime {

	public static void main(String[] args) {
		//Get current date time
        LocalDateTime now = LocalDateTime.now();
        System.out.println("Before : " + now);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = now.format(formatter);
        System.out.println("After : " + formatDateTime);
        
        String nowStr = "2016-11-09 10:30";
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime formatDateTime2 = LocalDateTime.parse(nowStr, formatter2);
        System.out.println("Before : " + nowStr);
        System.out.println("After : " + formatDateTime2);
        System.out.println("After : " + formatDateTime2.format(formatter2));
	}

}
