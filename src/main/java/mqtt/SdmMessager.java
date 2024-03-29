/**
 * 
 */
package mqtt;

import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * SdmMessager Publishes messages
 * 
 * @author Lars Schipper
 * @date Oct 3, 2019
 */
public class SdmMessager extends SdmController implements Runnable {

	/**
	 * The message
	 */
	private final byte[] message;

	/**
	 * The topic
	 */
	private final SdmTopic topic;

	/**
	 * Constructs a new {@link SdmMessager}
	 */
	public SdmMessager(SdmTopic topic, byte[] message) throws MqttException {
		this.topic = topic;
		this.message = message;
	}

	/**
	 * Publishes message
	 */
	public void run() {
		try {
			client.publish(topic.toString(), message, Settings.QOS, Settings.MESSAGE_RETAINED);
		} catch (Exception e) {
			e.printStackTrace();
			//SdmLogger.Log(this.toString(), e.getStackTrace().toString());
		}

		System.out.println("Message sent succesfully (" + topic.toString() + ") (" + new String(message) + ")");
	}

}
