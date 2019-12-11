package mqtt;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.UUID;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import enums.ComponentType;
import enums.LaneType;
import janjulius.Tuple;
import util.Constants;

/**
 * Repersents the SdmController and handles all related things
 * 
 * @author Jan Julius de Lang
 * @author Lars Schipper
 * @date Dec 9, 2019
 */
public class SdmController {

	public final MqttClient client;

	private Queue<SdmMessage> sdmMessageQ = new LinkedList<SdmMessage>();

	private List<Tuple<SdmTopic, byte[]>> sensorStatus = new ArrayList<Tuple<SdmTopic, byte[]>>();

	private double busyTime;

	private final double waitTime = 10_000;

	/**
	 * Filtered topics for regular traffic lights queue
	 */
	private final String[] filteredTopics = { "8/vessel/3/sensor/0" };
	
	private final SdmTopic[] filteredSensors = { new SdmTopic(Constants.CONNECTED_TEAM, LaneType.VESSEL, 0, ComponentType.SENSOR, 3) };

	public SdmController() throws MqttException {
		String clientId = UUID.randomUUID().toString();
		client = new MqttClient(Constants.PROTOCOL + Constants.ADDRESS + ":" + Constants.PORT, clientId);

		MqttConnectOptions options = new MqttConnectOptions();
		options.setAutomaticReconnect(Settings.AUTOMATIC_RECONNECT);
		options.setCleanSession(Settings.CLEAN_ON_START);
		options.setConnectionTimeout(Settings.CONNECTION_TIMEOUT);

		client.connect(options);
	}

	public void publish(SdmTopic topic, byte[] message) throws MqttException {
		SdmMessager messager = new SdmMessager(topic, message);
		messager.run();
		updateBusyTime();
	}

	public void publish(SdmMessage sdmMessage) throws MqttException {
		publish(sdmMessage.getTopic(), sdmMessage.getMessage());
	}

	public void subscribe(SdmTopic topic, IMqttMessageListener listener) throws MqttException {
		SdmSubscriber subscriber = new SdmSubscriber(topic, listener);
		subscriber.run();
	}

	public void queue(SdmMessage sdmMessage) {
		if (isFilteredTopic(sdmMessage))
			return;
		if (sdmMessageQ.isEmpty()) {
			sdmMessageQ.add(sdmMessage);
		} else {
			try {
				for (SdmMessage qmsg : sdmMessageQ) {
					if (qmsg.equals(sdmMessage))
						return;
					if (qmsg.getTopic().equals(sdmMessage.getTopic()))
						return;
					System.out.println("ADDED TO QUEUE");
					sdmMessageQ.add(sdmMessage);
					break;
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		updateBusyTime();
	}

	public Queue<SdmMessage> getSdmMessageQ() {
		return sdmMessageQ;
	}

	public List<Tuple<SdmTopic, byte[]>> getSensorStatus() {
		return sensorStatus;
	}

	public void setSdmMessageQ(Queue<SdmMessage> sdmMessageQ) {
		this.sdmMessageQ = sdmMessageQ;
	}

	public boolean isBusy() {
		return System.currentTimeMillis() > busyTime + waitTime;
	}

	private void updateBusyTime() {
		busyTime = System.currentTimeMillis(); //set message to current time
	}

	public void pollQueue() throws MqttException {
		SdmMessage msg = sdmMessageQ.poll();
		handleMessage(msg);
	}

	public void handleMessage(SdmMessage sdmMessage) throws MqttException {
		SdmHandler handlerhomie = new SdmHandler(this, sdmMessage);
		handlerhomie.start();
	}

	private boolean isFilteredTopic(SdmMessage msg) {
		for (String string : filteredTopics)
			if (msg.getTopic().toString().equals(string))
				return true;
		for (SdmTopic sdmTopic : filteredSensors) {
			if(sdmTopic.equals(msg.getTopic()))
				return true;
		}
		return false;
	}

	/**
	 * Updates a sensor status of {@linkplain topic} or adds it to the {@link sensorStatus}
	 * 
	 * @param topic
	 * @param message
	 */
	public void updateSensorStatus(SdmTopic topic, byte[] message) {
		if (topic.getComponentType() != ComponentType.SENSOR)
			return;

		for (Tuple<SdmTopic, byte[]> b : sensorStatus) {
			if (topic.equals(b.getLeft())) {
				b.setRight(message);
				return;
			}
		}

		sensorStatus.add(new Tuple<SdmTopic, byte[]>(topic, message));
	}

}
