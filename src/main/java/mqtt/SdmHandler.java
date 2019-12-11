/**
 * 
 */
package mqtt;

import java.util.List;

import org.eclipse.paho.client.mqttv3.MqttException;

import enums.ComponentType;
import util.Constants;
import util.SdmGrouper;
import util.SdmHelper;

/**
 * SdmHandler
 * 
 * @author Jan Julius de Lang
 * @date Dec 6, 2019 03:00 AM
 */
public class SdmHandler extends Thread {

	private final SdmController publisher;

	private final SdmMessage message;

	private List<SdmMessage> otherGroups;

	public SdmHandler(SdmController pub, SdmMessage msg) {
		publisher = pub;
		message = msg;
	}

	public void run() {
		otherGroups = SdmGrouper.getRelatedGroups(message.getTopic(), publisher);

		otherGroups.add(message);

		for (SdmMessage sdmMessage : otherGroups) {

			try {

				if (sdmMessage.getTopic().getComponentType() == ComponentType.TRAFFIC_LIGHT)
					sdmMessage.setMessage(SdmHelper.intToBytes(2));
				else
					sdmMessage.setMessage(SdmHelper.intToBytes(1));

				publisher.publish(sdmMessage);
				Thread.sleep(Constants.DEFAULT_GREEN_TIME);
			} catch (Exception e) {
				System.out.println(e);
			}

			System.out.println(sdmMessage);
			if (sdmMessage.getTopic().getComponentType() == ComponentType.TRAFFIC_LIGHT) { //if traffic light set to orange
				try {
					Thread.sleep(1_000);
					sdmMessage.setMessage(SdmHelper.intToBytes(1));
					publisher.publish(sdmMessage);
				} catch (Exception e) {
				}
			}

			try { //set back to 0
				Thread.sleep(1_000);
				sdmMessage.setMessage(SdmHelper.intToBytes(0));
				publisher.publish(sdmMessage);
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
				System.out.println("removing: " + sdmMessage.getTopic());
				for (SdmMessage asdf : publisher.getSdmMessageQ()) {
					System.out.println("Msg:" + asdf);
				}
				publisher.pollQueue();
			} catch (MqttException e) {
				e.printStackTrace();
			}
		}
	}

}
