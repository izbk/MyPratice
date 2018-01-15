package my.akka.iot.message;

public final class ReadTemperature {
	long requestId;

	public ReadTemperature(long requestId) {
		this.requestId = requestId;
	}

	public long getRequestId() {
		return requestId;
	}
	
}
