package sdmMessageListener;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import mqtt.SdmController;

public abstract class SdmMessageListener implements IMqttMessageListener {
	public SdmController controller;

	public SdmMessageListener(SdmController controller) throws MqttException {
		super();
		
		this.controller = controller;
	}

	public abstract void messageArrived(String topic, MqttMessage message) throws Exception;

}
