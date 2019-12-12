/**
 * 
 */
package mqtt;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Handler;

import org.eclipse.paho.client.mqttv3.MqttException;

import enums.ComponentType;
import enums.LaneType;
import util.Constants;
import util.SdmHelper;

/**
 * SdmHandler Handles requests from the controller represents its own thread
 * 
 * @author Jan Julius de Lang
 * @date Dec 6, 2019 03:00 AM
 */
public class SdmHandler extends Thread {

	/**
	 * The publisher
	 */
	private final SdmController publisher;

	/**
	 * The message of this thread
	 */
	private final SdmMessage message;

	/**
	 * If this is busy
	 */
	private boolean working = true;

	/**
	 * Constructs a new {@link SdmHandler}
	 */
	public SdmHandler(SdmController pub, SdmMessage msg) {
		publisher = pub;
		message = msg;
	}

	/**
	 * The boat topics
	 */
	public List<SdmTopic> boatTopics = Arrays.asList(new SdmTopic(Constants.CONNECTED_TEAM, LaneType.VESSEL, 0, ComponentType.SENSOR, 0), new SdmTopic(Constants.CONNECTED_TEAM, LaneType.VESSEL, 0, ComponentType.SENSOR, 2));

	@Override
	public void run() {
		working = true;

		try {
			if (message.getTopic().getComponentType().equals(ComponentType.BOAT_LIGHT))
				boatHandle();
			else if (message.getTopic().getComponentType().equals(ComponentType.TRAIN_LIGHT))
				trainHandle();
			else
				defaultHandle();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		working = false;

		try {
			publisher.pollQueue();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void boatHandle() throws MqttException, InterruptedException {
		//warning_lights -> deck leeg -> barriers dicht -> deck open -> boat_light op groen

		//warning lights aan
		SdmTopic deckWarningLightsTopic = new SdmTopic(LaneType.VESSEL, 0, ComponentType.WARNING_LIGHT, 0);
		publisher.publish(new SdmMessage(deckWarningLightsTopic, 1));

		//check if deck is empty
		SdmTopic deckStatusTopic = new SdmTopic(LaneType.VESSEL, 0, ComponentType.SENSOR, 3);

		while (!publisher.isSensorActive(deckStatusTopic, true)) {
			Thread.sleep(1000);
		}
		Thread.sleep(500);

		//close barriers
		SdmTopic deckBarriersTopic = new SdmTopic(LaneType.VESSEL, 0, ComponentType.BARRIER, 0);

		publisher.publish(new SdmMessage(deckBarriersTopic, 1));

		Thread.sleep(4000);

		SdmTopic deckTopic = new SdmTopic(LaneType.VESSEL, 0, ComponentType.DECK, 0);
		publisher.publish(new SdmMessage(deckTopic, 1));

		Thread.sleep(10_000);

		SdmTopic[] boatLightTopics = new SdmTopic[] { new SdmTopic(LaneType.VESSEL, 0, ComponentType.BOAT_LIGHT, 0), new SdmTopic(LaneType.VESSEL, 0, ComponentType.BOAT_LIGHT, 1) };
		for (SdmTopic sdmTopic : boatLightTopics) {
			publisher.publish(new SdmMessage(sdmTopic, 1));
			Thread.sleep(200);
		}
		Thread.sleep(2000);
		SdmTopic boatySensor = new SdmTopic(LaneType.VESSEL, 0, ComponentType.SENSOR, 1);
		while (publisher.isSensorActive(boatySensor, false)) {
//			if(publisher.getSensor(boatySensor) != null)
//				System.out.println(SdmHelper.bytesToInt(publisher.getSensor(boatySensor).getRight()));
			System.out.println("Waiting for boats to pass." + publisher.isSensorActive(boatySensor, false));
			Thread.sleep(2000);
		}
		Thread.sleep(500);

		for (SdmTopic sdmTopic : boatLightTopics) {
			publisher.publish(new SdmMessage(sdmTopic, 0));
		}
		publisher.publish(new SdmMessage(deckTopic, 0));
		Thread.sleep(10_000);

		publisher.publish(new SdmMessage(deckBarriersTopic, 0));
		Thread.sleep(4000);

		publisher.publish(new SdmMessage(deckWarningLightsTopic, 0));
	}

	private void trainHandle() throws MqttException, InterruptedException {
		SdmTopic trainWarningLights = new SdmTopic(LaneType.TRACK, 0, ComponentType.WARNING_LIGHT, 0);
		publisher.publish(new SdmMessage(trainWarningLights, 1));
		Thread.sleep(5000);
		
		SdmTopic trainBarriers = new SdmTopic(LaneType.TRACK, 0, ComponentType.BARRIER, 0);
		publisher.publish(new SdmMessage(trainBarriers, 1));
		Thread.sleep(4000);
		
		SdmTopic trainLight = new SdmTopic(LaneType.TRACK, 0, ComponentType.TRAIN_LIGHT, 0);
		publisher.publish(new SdmMessage(trainLight, 1));
		Thread.sleep(2000);

		SdmTopic trainySensor = new SdmTopic(LaneType.TRACK, 0, ComponentType.SENSOR, 0);
		while (publisher.isSensorActive(trainySensor, false)) {
			System.out.println("Waiting for train to leave.");
			Thread.sleep(1000);
		}
		Thread.sleep(2000);
		publisher.publish(new SdmMessage(trainLight, 0));
		Thread.sleep(3000);
		publisher.publish(new SdmMessage(trainBarriers, 0));
		Thread.sleep(4000);
		publisher.publish(new SdmMessage(trainWarningLights, 0));
	}

	private void defaultHandle() throws MqttException, InterruptedException {

		if (message.getTopic().getComponentType() == ComponentType.TRAFFIC_LIGHT)
			message.setMessage(SdmHelper.intToBytes(2));
		else
			message.setMessage(SdmHelper.intToBytes(1));

		publisher.publish(message);
		Thread.sleep(Constants.DEFAULT_GREEN_TIME);

		if (message.getTopic().getComponentType() == ComponentType.TRAFFIC_LIGHT) { //if traffic light set to orange
			Thread.sleep(1_000);
			message.setMessage(SdmHelper.intToBytes(1));
			publisher.publish(message);
		}

		Thread.sleep(1_000);
		message.setMessage(SdmHelper.intToBytes(0));
		publisher.publish(message);

		Thread.sleep(Constants.DEFAULT_CLEAR_TIME);

		System.out.println("removing: " + message.getTopic());
		for (SdmMessage asdf : publisher.getSdmMessageQ()) {
			System.out.println("Msg:" + asdf);
		}
	}

	public boolean isWorking() {
		return working;
	}

}
