package sdmMessageListener;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import mqtt.SdmController;
import mqtt.SdmHandler;
import mqtt.SdmMessage;
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

		if(value > 0) {
			System.out.println("Received: " + topic + " value: " + value);
			SdmTopic correspondingTrafficLightTopic = new SdmTopic(topic).getCorrespondingTrafficLight();
			SdmMessage sdmMessage = new SdmMessage(correspondingTrafficLightTopic, message.getPayload());
			publisher.queue(sdmMessage);
			
			System.out.println(publisher.getSdmMessageQ().peek() + " " + sdmMessage);

			if(publisher.getSdmMessageQ().peek() == sdmMessage)
				publisher.handleMessage(sdmMessage);
		}
	}
	
	
}
