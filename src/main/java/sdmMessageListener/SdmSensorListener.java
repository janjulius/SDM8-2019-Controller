package sdmMessageListener;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import enums.ComponentType;
import enums.LaneType;
import mqtt.SdmController;
import mqtt.SdmMessage;
import mqtt.SdmTopic;
import util.SdmHelper;

public class SdmSensorListener extends SdmMessageListener{
	public SdmSensorListener(SdmController controller) throws MqttException {
		super(controller);
	}

	public void messageArrived(String topic, MqttMessage message) throws Exception {
		System.out.println("received: " + topic);
		
		
		SdmTopic sdmTopic = new SdmTopic(topic);

		SdmTopic correspondingTopic = sdmTopic.GetCorrespondingTrafficLight();

		SdmTopic bam = new SdmTopic("8", LaneType.MOTORISED, 5, ComponentType.TRAFFIC_LIGHT, 0);

		SdmMessage msg = SdmMessage.createMessage(bam, SdmHelper.intToBytes(2));
		
		// Check if it can be turned on, else queue.
		controller.sendMessage(msg);
	}
}
