package my.akka.iot.message;

public final class RequestAllTemperatures {
	final long requestId;

	public RequestAllTemperatures(long requestId) {
		this.requestId = requestId;
	}
}
