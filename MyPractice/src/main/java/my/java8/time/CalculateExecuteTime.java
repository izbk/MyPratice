package my.java8.time;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CalculateExecuteTime {

	public static void main(String[] args) {
		try {
			useNanoTime();
			useCurrentTimeMillis();
			useEpochMilli();
			useTime();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void useNanoTime() throws Exception {
		// start
		long lStartTime = System.nanoTime();

		// task
		calculation();

		// end
		long lEndTime = System.nanoTime();

		// time elapsed
		long output = lEndTime - lStartTime;

		System.out.println("Elapsed time in milliseconds: " + output / 1000000);
	}
	
	private static void useCurrentTimeMillis() throws Exception {
		long lStartTime = System.currentTimeMillis();

        calculation();

        long lEndTime = System.currentTimeMillis();

        long output = lEndTime - lStartTime;

        System.out.println("Elapsed time in milliseconds: " + output);

	}
	
	private static void useEpochMilli() throws Exception {
		long lStartTime = Instant.now().toEpochMilli();

        calculation();

        long lEndTime = Instant.now().toEpochMilli();

        long output = lEndTime - lStartTime;

        System.out.println("Elapsed time in milliseconds: " + output);
		
	}
	private static void useTime() throws Exception {
		long lStartTime = new Date().getTime();

        calculation();

        long lEndTime = new Date().getTime();

        long output = lEndTime - lStartTime;

        System.out.println("Elapsed time in milliseconds: " + output);
		
	}

	private static void calculation() throws InterruptedException {
		TimeUnit.SECONDS.sleep(2);
	}

}
