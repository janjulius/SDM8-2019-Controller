package mqtt;

import util.SdmHelper;

/**
 * SdmMessage Represents a message that can be sent using mqtt
 * 
 * @author Jan Julius de Lang
 * @date Dec 12, 2019
 */
public class SdmMessage {

	/**
	 * The topic of the message
	 */
	private final SdmTopic topic;

	/**
	 * The content of the message
	 */
	private byte[] message;

	/**
	 * Constructs a new {@link SdmMessage}
	 */
	public SdmMessage(SdmTopic topic, byte[] message) {
		this.topic = topic;
		this.message = message;
	}

	/**
	 * Constructs a new {@link SdmMessage}
	 */
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
