package mqtt;

import java.util.UUID;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import util.Constants;

/**
 * 
 * @author Lars Schipper 
 * @author Jan Julius de Lang
 *
 */
public class SdmController {
	public final MqttClient client;
	
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
	}
	
	public void subscribe(SdmTopic topic, IMqttMessageListener listener) throws MqttException {
		SdmSubscriber subscriber = new SdmSubscriber(topic, listener);
		subscriber.run();
	}
	
}
