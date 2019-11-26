/**
 * 
 */
package mqtt;

import java.util.UUID;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import util.Constants;

/**
 * SdmSubscriber
 * Represents a SdmSubscriber
 * @author Lars Schipper
 * @date Oct 3, 2019
 */
public class SdmSubscriber extends SdmController implements Runnable {
	private final IMqttMessageListener listener;
	private final SdmTopic topic;
	
	/*
	 * Constructs a new {@link SdmSubscriber}
	 */
	public SdmSubscriber(SdmTopic topic, IMqttMessageListener listener) throws MqttException {
		this.topic = topic;
		this.listener = listener;
	}

	/*
	 * Subscribes to topic
	 */
	public void run() {
        try {
            client.subscribe(topic.toString(), listener);
            System.out.println("Subscribing to: " + topic.toString());
        } catch(MqttException e) {
            e.printStackTrace();
            //SdmLogger.Log(this.toString(), e.getStackTrace().toString());
        }	
	}	
}
