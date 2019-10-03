/**
 * 
 */
package mqtt;

import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * SdmMessage
 * Represents a SdmMessage
 * @author Jan Julius de Lang
 * @date Oct 3, 2019
 */
public class SdmMessage extends MqttMessage {

	/**
	 * The topic
	 */
	private final SdmTopic topic;
	
	/**
	 * The qos priority
	 */
	private static int QOS = 0;
	
	/**
	 * If the message is retained
	 */
	private static boolean RETAINED = true;
	
	/**
	 * Constructs a new {@link SdmMessage}
	 */
	public SdmMessage(SdmTopic topic, byte[] message) {
		this(topic, message, QOS, RETAINED);
	}
	
	/**
	 * Constructs a new {@link SdmMessage}
	 */
	public SdmMessage(SdmTopic topic, byte[] message, int qos, boolean retained) {
		super(message);
		this.topic = topic;
		setQos(qos);
		setRetained(retained);
	}
	
	/**
	 * Creates a {@linkplain SdmMessage}
	 * @param topic the topic to write on
	 * @param message the message
	 * @return
	 */
	public static SdmMessage createMessage(SdmTopic topic, byte[] message) {
		return new SdmMessage(topic, message);
	}
	
	/**
	 * Returns the message as the payload of the {@link MqttMessage}
	 * @return byte array of payload
	 */
	public byte[] getMessage() {
		return getPayload();
	}
	
	/**
	 * @return The topic string
	 */
	public SdmTopic getTopic() {
		return topic;
	}
	
}
