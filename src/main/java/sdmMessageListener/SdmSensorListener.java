package sdmMessageListener;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import enums.ComponentType;
import enums.LaneType;
import mqtt.SdmController;
import mqtt.SdmMessage;
import mqtt.SdmTopic;
import util.Constants;

/**
 * Represents Mqtt Message Listener
 * 
 * @author Lars Schipper
 * @author Jan Julius de Lang
 */
public class SdmSensorListener implements IMqttMessageListener {

	/**
	 * The publisher
	 */
	private final SdmController publisher;

	/**
	 * Filtered topics for regular traffic lights queue
	 */
	private final SdmTopic[] filteredTopics = {
			new SdmTopic(Constants.CONNECTED_TEAM, LaneType.VESSEL, 0, ComponentType.SENSOR, 1),
			new SdmTopic(Constants.CONNECTED_TEAM, LaneType.VESSEL, 0, ComponentType.SENSOR, 3),
			new SdmTopic(Constants.CONNECTED_TEAM, LaneType.TRACK, 0, ComponentType.SENSOR, 1) 
			};

	/**
	 * Constructs a new {@link SdmSensorListener}
	 */
	public SdmSensorListener(SdmController publisher) throws MqttException {
		this.publisher = publisher;
	}

	/**
	 * Event when a message is received
	 * 
	 * @param topic   The topic as string
	 * @param message The message as a {@link MqttMessage}
	 */
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		byte[] messageBytes = message.getPayload();
		int value = Integer.parseInt(new String(messageBytes));

		SdmTopic receivedTopic = new SdmTopic(topic);

		System.out.println("Received: " + topic + " value: " + value);

		if (receivedTopic.getComponentType().equals(ComponentType.SENSOR))
			publisher.updateSensorStatus(new SdmTopic(receivedTopic.toString()), messageBytes);
		
		if(isBoat(receivedTopic.toString())) {
			publisher.handleBoat(new SdmMessage(new SdmTopic(topic), value));
			return;
		}
		
		if (isFilteredTopic(receivedTopic))
			return;

		SdmTopic correspondingTrafficLightTopic = receivedTopic.getCorrespondingTrafficLight();
		SdmMessage sdmMessage = new SdmMessage(correspondingTrafficLightTopic, message.getPayload());
		publisher.queue(sdmMessage);

		if (publisher.getSdmMessageQ().get(0) == sdmMessage)
			publisher.handleMessage(sdmMessage);

	}
	

	private boolean isBoat(String topic) {
		return topic.equals(new SdmTopic(LaneType.VESSEL, 0, ComponentType.SENSOR, 0).toString())
				|| topic.equals(new SdmTopic(LaneType.VESSEL, 0, ComponentType.SENSOR, 2).toString());
	}
	
	/**
	 * Check if a message is filtered
	 * 
	 * @param msg the message
	 * @return true if the message is a filtered message (includes all topics) else
	 *         false
	 */
	private boolean isFilteredTopic(SdmTopic topic) {
		// this is like so because they may have different reqs in the future
		for (SdmTopic sdmTopic : filteredTopics)
			if (sdmTopic.equals(topic))
				return true;
		return false;
	}


}
