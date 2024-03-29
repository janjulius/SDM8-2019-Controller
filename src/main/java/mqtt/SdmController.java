package mqtt;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import enums.ComponentType;
import enums.LaneType;
import janjulius.Tuple;
import util.Constants;
import util.SdmGrouper;
import util.SdmHelper;

/**
 * Repersents the SdmController and handles all related things
 * 
 * @author Jan Julius de Lang
 * @author Lars Schipper
 * @date Dec 9, 2019
 */
public class SdmController {

	/**
	 * The client
	 */
	public final MqttClient client;

	/**
	 * The queue of messages, still to be handled
	 */
	private List<SdmMessage> sdmMessageQ = new ArrayList<SdmMessage>();

	/**
	 * All known sensor statusses
	 */
	private List<Tuple<SdmTopic, byte[]>> sensorStatus = new ArrayList<Tuple<SdmTopic, byte[]>>();

	/**
	 * Time until or since the controller was or is busy
	 */
	private double busyTime;

	/**
	 * The default waiting time
	 */
	private final double waitTime = 10_000;

	/**
	 * Thread that currently running (main thread)
	 */
	private SdmHandler currentThread = null;

	/**
	 * Thread that currently runs the boat
	 */
	private SdmHandler boatThread = null;


	/**
	 * Constructs a new {@link SdmController}
	 */
	public SdmController() throws MqttException {
		String clientId = UUID.randomUUID().toString();
		client = new MqttClient(Constants.PROTOCOL + Constants.ADDRESS + ":" + Constants.PORT, clientId);

		MqttConnectOptions options = new MqttConnectOptions();
		options.setAutomaticReconnect(Settings.AUTOMATIC_RECONNECT);
		options.setCleanSession(Settings.CLEAN_ON_START);
		options.setConnectionTimeout(Settings.CONNECTION_TIMEOUT);

		client.connect(options);
	}

	/**
	 * Publish a topic and a message
	 * 
	 * @param topic   the topic
	 * @param message the message
	 * @throws MqttException
	 */
	public void publish(SdmTopic topic, byte[] message) throws MqttException {
		SdmMessager messager = new SdmMessager(topic, message);
		messager.run();
		updateBusyTime();
	}

	/**
	 * Publish a {@link SdmMessage}
	 * 
	 * @param sdmMessage The message
	 * @throws MqttException
	 */
	public void publish(SdmMessage sdmMessage) throws MqttException {
		publish(sdmMessage.getTopic(), sdmMessage.getMessage());
	}

	/**
	 * Subscribes to an {@link SdmTopic}
	 * 
	 * @param topic    the topic
	 * @param listener the listener interface
	 * @throws MqttException
	 */
	public void subscribe(SdmTopic topic, IMqttMessageListener listener) throws MqttException {
		SdmSubscriber subscriber = new SdmSubscriber(topic, listener);
		subscriber.run();
	}

	/**
	 * Queues a message onto {@link SdmMessageQ} if it meets the requirements
	 * 
	 * @param sdmMessage the message
	 */
	public void queue(SdmMessage sdmMessage) {
		if (isTrain(new SdmTopic(sdmMessage.getTopic().toString()).getCorrespondingSensors())) {
			sdmMessageQ.add(0, sdmMessage);
		}
		else if (sdmMessageQ.isEmpty()) {
			sdmMessageQ.add(sdmMessage);
		} else {
			try {
				for (SdmMessage qmsg : sdmMessageQ) {
					if (qmsg.equals(sdmMessage))
						return;
					if (qmsg.getTopic().equals(sdmMessage.getTopic()))
						return;
					sdmMessageQ.add(sdmMessage);
					pollQueue();
					break;
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		updateBusyTime();
	}

	private boolean isTrain(SdmTopic topic) {
		return topic.toString().equals(new SdmTopic(LaneType.TRACK, 0, ComponentType.SENSOR, 0).toString()) ;
	}
	
	public void handleBoat(SdmMessage msg) {
		if (boatThread == null || !boatThread.isWorking()) {
			boatThread = new SdmHandler(this, msg);
			new Thread(boatThread).start();
		}
	}

	/**
	 * Returns wether the {@link SdmController} is currently busy with something
	 * 
	 * @return true if busy, false if not
	 */
	public boolean isBusy() {
		if (currentThread != null)
			if (currentThread.isWorking())
				return true;
		return System.currentTimeMillis() > busyTime + waitTime;
	}

	/**
	 * Updates the last time the controller was busy to the current time.
	 */
	private void updateBusyTime() {
		busyTime = System.currentTimeMillis(); // set message to current time
	}

	/**
	 * Polls the queue (message that was entered first) and handles it Be caucious
	 * to use preferably use {@link handleMesage}
	 * 
	 * @throws MqttException
	 */
	public void pollQueue() throws MqttException {
		if (currentThread == null || !currentThread.isWorking()) {
			if (sdmMessageQ.size() > 0) {
				SdmMessage msg = sdmMessageQ.get(0);
				handleMessage(msg);
			}
		}
	}

	/**
	 * Handles a message and removes it from the queue
	 * 
	 * @param sdmMessage the message
	 * @throws MqttException
	 */
	public void handleMessage(SdmMessage sdmMessage) throws MqttException {
		if (currentThread == null || !currentThread.isWorking()) {
			currentThread = new SdmHandler(this, sdmMessage);
			new Thread(currentThread).start();
			sdmMessageQ.remove(sdmMessage);
			for (SdmMessage msg : SdmGrouper.getRelatedGroups(sdmMessage.getTopic(), this)) {
				new Thread(new SdmHandler(this, msg)).start();
				SdmMessage toRemove = null;
				for(SdmMessage item : sdmMessageQ) {
					if(item.getTopic().equals(msg.getTopic())) {
						toRemove = item;
						break;
					}
				}
				if(toRemove != null) 
				{				
					sdmMessageQ.remove(toRemove);
				}
			}
		}
	}

	/**
	 * Updates a sensor status of {@linkplain topic} or adds it to the
	 * {@link sensorStatus}
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

	public List<SdmMessage> getSdmMessageQ() {
		return sdmMessageQ;
	}

	public List<Tuple<SdmTopic, byte[]>> getSensorStatus() {
		return sensorStatus;
	}

	/**
	 * Gets a sensor from the {@linkplain sensorStatus} list
	 * 
	 * @param topic the topic
	 * @return the sensor or null if it does not exist
	 */
	public Tuple<SdmTopic, byte[]> getSensor(SdmTopic topic) {
		for (Tuple<SdmTopic, byte[]> sensor : sensorStatus) {
			if (sensor.getLeft().equals(topic)) {
				return sensor;
			}
		}
		return null;
	}

	/**
	 * Check if a sensor is active
	 * 
	 * @param topic the topic
	 * @return true if the sensor is active else false
	 */
	public boolean isSensorActive(SdmTopic topic, boolean isDeck) {
		Tuple<SdmTopic, byte[]> sensor = getSensor(topic);
		if (isDeck && sensor == null)
			return true;
		if (sensor == null)
			return false;
		if (SdmHelper.bytesToInt(sensor.getRight()) >= 1)
			return true;
		return false;
	}

}
