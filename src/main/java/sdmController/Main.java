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
import mqtt.SdmMessager;
import mqtt.SdmTopic;
import mqtt.Settings;
import sdmMessageListener.SdmSensorListener;
import util.Constants;
import util.SdmHelper;

/**
 * Main
 * 
 * @author Jan Julius de Lang
 * @author Lars Schipper
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
	private static String[] settings = new String[] { Constants.CONNECTED_TEAM, Constants.PROTOCOL, Constants.ADDRESS,
			Constants.PORT };

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
			
		try {
			SdmController publisher = new SdmController();
			
			SdmTopic allSensors = new SdmTopic(settings[0], LaneType.ALL, "+", ComponentType.SENSOR, "+");

			//SdmTopic topic = new SdmTopic(settings[0], LaneType.MOTORISED, 5, ComponentType.SENSOR, 0);
			//publisher.publish(topic, SdmHelper.intToBytes(1));			
			publisher.subscribe(allSensors, new SdmSensorListener(publisher));
		} catch (MqttException e) {
			e.printStackTrace();
//			try {
//				SdmLogger.Save();
//			} catch (IOException e1) {
//				System.out.println("Was not able to save error log.");
//				e1.printStackTrace();
//			}
		}
	}

}
