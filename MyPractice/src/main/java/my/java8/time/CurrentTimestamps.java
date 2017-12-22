package my.java8.time;

import java.sql.Timestamp;
import java.time.Instant;

public class CurrentTimestamps {

	public static void main(String[] args) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println(timestamp);

        //return number of milliseconds since January 1, 1970, 00:00:00 GMT
        System.out.println(timestamp.getTime());

        // Convert timestamp to instant
        Instant instant = timestamp.toInstant();
        System.out.println(instant);

        //return number of milliseconds since the epoch of 1970-01-01T00:00:00Z
        System.out.println(instant.toEpochMilli());

        // Convert instant to timestamp
        Timestamp tsFromInstant = Timestamp.from(instant);
        System.out.println(tsFromInstant.getTime());

	}

}
