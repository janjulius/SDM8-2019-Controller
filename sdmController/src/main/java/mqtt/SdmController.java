/**
 * 
 */
package mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import logger.SdmLogger;

/**
 * SdmController The SdmController / client
 * 
 * @author Jan Julius de Lang
 * @date Oct 3, 2019
 */
public class SdmController extends MqttClient {

	/**
	 * The group the controller is connected to
	 */
	public final String connectedGroup;

	/**
	 * Constructs a new {@link SdmController}
	 */
	public SdmController(String protocol, String address, String port, String clientId, String connectedGroup) throws MqttException {
		super(protocol + address + ":" + port, clientId);
		this.connectedGroup = connectedGroup;
	}

	/**
	 * Sends a message to the Mqtt broker
	 * 
	 * @param topic The topic to send to
	 * @param msg   The message to send as MqttMessage
	 * @return
	 * @throws MqttException
	 */
	public boolean sendMessage(String topic, MqttMessage msg) throws MqttException {
		System.out.println("Sending message (" + msg + ") on topic: " + topic);
		try {
			publish(topic, msg);
		} catch (MqttException e) {
			SdmLogger.Log(this.toString(), e.getStackTrace().toString());
			return false;
		}
		return true;
	}

}
