/**
 * 
 */
package mqtt;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * SdmSubscriber Subscribes to a topic
 * 
 * @author Lars Schipper
 * @date Oct 3, 2019
 */
public class SdmSubscriber extends SdmController implements Runnable {

	/**
	 * The listener
	 */
	private final IMqttMessageListener listener;

	/**
	 * The subscribed topic
	 */
	private final SdmTopic topic;

	/**
	 * Constructs a new {@link SdmSubscriber}
	 */
	public SdmSubscriber(SdmTopic topic, IMqttMessageListener listener) throws MqttException {
		this.topic = topic;
		this.listener = listener;
	}

	/**
	 * Subscribes to topic
	 */
	public void run() {
		try {
			client.subscribe(topic.toString(), listener);
			System.out.println("Subscribing to: " + topic.toString());
		} catch (MqttException e) {
			e.printStackTrace();
			//SdmLogger.Log(this.toString(), e.getStackTrace().toString());
		}
	}
}
