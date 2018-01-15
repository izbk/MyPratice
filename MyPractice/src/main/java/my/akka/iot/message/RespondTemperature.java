package my.akka.iot.message;

import java.util.Optional;

public final class RespondTemperature {
	long requestId;
	Optional<Double> value;

	public RespondTemperature(long requestId, Optional<Double> value) {
		this.requestId = requestId;
		this.value = value;
	}

	public long getRequestId() {
		return requestId;
	}

	public Optional<Double> getValue() {
		return value;
	}
	
}
