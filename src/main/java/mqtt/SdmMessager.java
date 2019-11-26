/**
 * 
 */
package mqtt;

import java.util.Arrays;
import java.util.UUID;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import mqtt.SdmController;
import util.Constants;

/**
 * SdmMessager
 * Represents a SdmMessager
 * @author Lars Schipper
 * @date Oct 3, 2019
 */
public class SdmMessager extends SdmController implements Runnable{
	private final byte[] message;
	private final SdmTopic topic;
	
	/*
	 * Constructs a new {@link SdmMessager}
	 */
	public SdmMessager(SdmTopic topic, byte[] message) throws MqttException {
		this.topic = topic;
		this.message = message;
	}

	/*
	 * Publishes message
	 */
	public void run() {
		try {
			System.out.println("Sending message (" +  new String(message) + ") on topic: " + topic.toString());
			client.publish(topic.toString(), message, Settings.QOS, Settings.MESSAGE_RETAINED);
		} catch (Exception e) {
			e.printStackTrace();
			//SdmLogger.Log(this.toString(), e.getStackTrace().toString());
		}
		
		System.out.println("Message sent succesfully");		
	}
	
}
