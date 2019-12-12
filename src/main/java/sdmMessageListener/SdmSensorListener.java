package sdmMessageListener;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import enums.ComponentType;
import mqtt.SdmController;
import mqtt.SdmMessage;
import mqtt.SdmTopic;

/**
 * Represents Mqtt Message Listener
 * 
 * @author Lars Schipper
 * @author Jan Julius de Lang
 */
public class SdmSensorListener implements IMqttMessageListener {

	private final SdmController publisher;

	public SdmSensorListener(SdmController publisher) throws MqttException {
		this.publisher = publisher;
	}

	public void messageArrived(String topic, MqttMessage message) throws Exception {
		byte[] messageBytes = message.getPayload();
		int value = Integer.parseInt(new String(messageBytes));

		if (value > 0) {
			SdmTopic receivedTopic = new SdmTopic(topic);

			System.out.println("Received: " + topic + " value: " + value);

			if (receivedTopic.getComponentType() == ComponentType.SENSOR)
				publisher.updateSensorStatus(receivedTopic, messageBytes);

			SdmTopic correspondingTrafficLightTopic = receivedTopic.getCorrespondingTrafficLight();
			SdmMessage sdmMessage = new SdmMessage(correspondingTrafficLightTopic, message.getPayload());
			publisher.queue(sdmMessage);

			if (publisher.getSdmMessageQ().get(0) == sdmMessage)
				publisher.handleMessage(sdmMessage);
		}
	}

}
