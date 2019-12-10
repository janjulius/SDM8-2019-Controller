package mqtt;

import util.SdmHelper;

public class SdmMessage {

	private final SdmTopic topic;

	private byte[] message;

	public SdmMessage(SdmTopic topic, byte[] message) {
		this.topic = topic;
		this.message = message;
	}

	public SdmMessage(SdmTopic topic, int message) {
		this.topic = topic;
		this.message = SdmHelper.intToBytes(message);
	}

	public void setMessage(byte[] message) {
		this.message = message;
	}

	public SdmTopic getTopic() {
		return topic;
	}

	public byte[] getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "[SdmMessage] topic: " + topic.toString() + " message: " + message;
	}
}
