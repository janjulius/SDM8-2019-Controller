/**
 * 
 */
package sdmController;

import java.io.IOException;
import java.util.UUID;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import enums.ComponentType;
import enums.Direction;
import enums.LaneType;
import logger.SdmLogger;
import mqtt.SdmController;
import mqtt.SdmMessage;
import mqtt.SdmTopic;
import mqtt.Settings;
import sdmMessageListener.SdmSensorListener;
import util.Constants;
import util.SdmHelper;

/**
 * Main
 * 
 * @author Jan Julius de Lang
 * @date Oct 3, 2019
 */
public class Main {

	/**
	 * Unique id for the publisher (this)
	 */
	private static final String clientId = UUID.randomUUID().toString();

	/**
	 * Settings of this application (can be overriden by arguments)
	 */
	private static String[] settings = new String[] { Constants.CONNECTED_TEAM, Constants.PROTOCOL, Constants.ADDRESS, Constants.PORT };

	/**
	 * The main entry point of the program
	 * 
	 * @param args Arguments to start the program
	 * @param args 0 connected group
	 * @param args 1 the protocol
	 * @param args 2 ip default: 91.121.165.36
	 * @param args 3 port default: 1883
	 */
	public static void main(String[] args) {

		for (int i = 0; i < args.length; i++)
			settings[i] = settings[i] != args[i] ? args[i] : settings[i];

		MqttConnectOptions options = new MqttConnectOptions();
		options.setAutomaticReconnect(Settings.AUTOMATIC_RECONNECT);
		options.setCleanSession(Settings.CLEAN_ON_START);
		options.setConnectionTimeout(Settings.CONNECTION_TIMEOUT);

		try {
			SdmController publisher = new SdmController(settings[1], settings[2], settings[3], clientId, settings[0]);
			publisher.connect(options);

			SdmTopic topic = new SdmTopic(settings[0], LaneType.MOTORISED, 5, 0, ComponentType.TRAFFIC_LIGHT, 0);
			SdmTopic allSensors = new SdmTopic(settings[0], LaneType.ALL, +, +, ComponentType.SENSOR, +);
			
			SdmMessage msg = SdmMessage.createMessage(topic, SdmHelper.intToBytes(2));

			publisher.sendMessage(topic, msg);

			System.out.println("Message sent succesfully");
			
			publisher.subscribeToTopic(topic, new SdmSensorListener());
			
			publisher.close();
		} catch (MqttException e) {
			e.printStackTrace();
			try {
				SdmLogger.Save();
			} catch (IOException e1) {
				System.out.println("Was not able to save error log.");
				e1.printStackTrace();
			}
		}
	}

}
