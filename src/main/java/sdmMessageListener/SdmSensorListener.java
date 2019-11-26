package sdmMessageListener;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import mqtt.SdmController;
import mqtt.SdmTopic;
import util.SdmHelper;

/**
 * Represents Mqtt Message Listener
 * @author Lars Schipper
 *
 */
public class SdmSensorListener implements IMqttMessageListener {
	private final SdmController publisher;
	
	public SdmSensorListener(SdmController publisher) throws MqttException {
		this.publisher = publisher;
	}

	public void messageArrived(String topic, MqttMessage message) throws Exception {
		int value = Integer.parseInt(new String(message.getPayload()));
		System.out.println("Received: " + topic + " value: " + value);

		if(value == 1) {
			SdmTopic correspondingTrafficLightTopic = new SdmTopic(topic).getCorrespondingTrafficLight();
			
			// Check if it can be turned on, else queue.
			publisher.publish(correspondingTrafficLightTopic, SdmHelper.intToBytes(1));
		}
	}
}
