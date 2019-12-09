/**
 * 
 */
package mqtt;

import org.eclipse.paho.client.mqttv3.MqttException;

import enums.ComponentType;
import util.Constants;
import util.SdmHelper;

/**
 * SdmHandler
 * @author Jan Julius de Lang
 * @date Dec 6, 2019 03:00 AM
 */
public class SdmHandler extends Thread {

	private final SdmController publisher;
	private final SdmMessage message;
	
	public SdmHandler(SdmController pub, SdmMessage msg) {
		publisher = pub;
		message = msg;
	}
	
	public void run() {

		try {
			
			if(message.getTopic().getComponentType() == ComponentType.TRAFFIC_LIGHT)
				message.setMessage(SdmHelper.intToBytes(2));
			else
				message.setMessage(SdmHelper.intToBytes(1));
				
			publisher.publish(message);
			Thread.sleep(Constants.DEFAULT_GREEN_TIME);
		} catch(Exception e) {
			System.out.println(e);
		}
		
		if(message.getTopic().getComponentType() == ComponentType.TRAFFIC_LIGHT) { //if traffic light set to orange
			try {
				Thread.sleep(1_000);
				message.setMessage(SdmHelper.intToBytes(1));
				publisher.publish(message);
			}catch (Exception e) {
			}
		}
		
		try { //set back to 0
			Thread.sleep(1_000);
			message.setMessage(SdmHelper.intToBytes(0));
			publisher.publish(message);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MqttException e) {
			e.printStackTrace();
		}
		
		try {
			Thread.sleep(Constants.DEFAULT_CLEAR_TIME);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			System.out.println("removing: " + message.getTopic());
			for (SdmMessage asdf : publisher.getSdmMessageQ()) {
				System.out.println(asdf);
			}
			publisher.pollQueue();
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

}
