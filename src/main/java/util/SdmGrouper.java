package util;

import java.util.ArrayList;
import java.util.List;

import enums.ComponentType;
import enums.LaneType;
import janjulius.Tuple;
import mqtt.SdmController;
import mqtt.SdmMessage;
import mqtt.SdmTopic;

/**
 * Returns related groups SdmGrouper
 * 
 * @author Jan Julius de Lang
 * @date Dec 7, 2019
 */
public class SdmGrouper {

	private static SdmController publisher;

	private static final SdmMessage[][] trackOccupiedGroups = new SdmMessage[][] {
			new SdmMessage[] { new SdmMessage(new SdmTopic(Constants.CONNECTED_TEAM, LaneType.MOTORISED, 7, ComponentType.TRAFFIC_LIGHT, 0), 2),
					new SdmMessage(new SdmTopic(Constants.CONNECTED_TEAM, LaneType.MOTORISED, 2, ComponentType.TRAFFIC_LIGHT, 0), 2),
					new SdmMessage(new SdmTopic(Constants.CONNECTED_TEAM, LaneType.CYCLE, 1, ComponentType.TRAFFIC_LIGHT, 0), 2), new SdmMessage(new SdmTopic(Constants.CONNECTED_TEAM, LaneType.FOOT, 2, ComponentType.TRAFFIC_LIGHT, 0), 2) },
			new SdmMessage[] { new SdmMessage(new SdmTopic(Constants.CONNECTED_TEAM, LaneType.MOTORISED, 0, ComponentType.TRAFFIC_LIGHT, 0), 2),
					new SdmMessage(new SdmTopic(Constants.CONNECTED_TEAM, LaneType.MOTORISED, 3, ComponentType.TRAFFIC_LIGHT, 0), 2),
					new SdmMessage(new SdmTopic(Constants.CONNECTED_TEAM, LaneType.CYCLE, 4, ComponentType.TRAFFIC_LIGHT, 0), 2), new SdmMessage(new SdmTopic(Constants.CONNECTED_TEAM, LaneType.FOOT, 6, ComponentType.TRAFFIC_LIGHT, 0), 2)

			},
			new SdmMessage[] { new SdmMessage(new SdmTopic(Constants.CONNECTED_TEAM, LaneType.MOTORISED, 0, ComponentType.TRAFFIC_LIGHT, 0), 2),
					new SdmMessage(new SdmTopic(Constants.CONNECTED_TEAM, LaneType.MOTORISED, 2, ComponentType.TRAFFIC_LIGHT, 0), 2),
					new SdmMessage(new SdmTopic(Constants.CONNECTED_TEAM, LaneType.MOTORISED, 3, ComponentType.TRAFFIC_LIGHT, 0), 2), } };

	private static final SdmMessage[][] trackFreeGroups = new SdmMessage[][] { new SdmMessage[] { new SdmMessage(new SdmTopic(Constants.CONNECTED_TEAM, LaneType.MOTORISED, 5, ComponentType.TRAFFIC_LIGHT, 0), 2),
			new SdmMessage(new SdmTopic(Constants.CONNECTED_TEAM, LaneType.MOTORISED, 5, ComponentType.TRAFFIC_LIGHT, 1), 2), new SdmMessage(new SdmTopic(Constants.CONNECTED_TEAM, LaneType.MOTORISED, 6, ComponentType.TRAFFIC_LIGHT, 0), 2),
			new SdmMessage(new SdmTopic(Constants.CONNECTED_TEAM, LaneType.MOTORISED, 8, ComponentType.TRAFFIC_LIGHT, 0), 2), },
			new SdmMessage[] { new SdmMessage(new SdmTopic(Constants.CONNECTED_TEAM, LaneType.MOTORISED, 5, ComponentType.TRAFFIC_LIGHT, 0), 2),
					new SdmMessage(new SdmTopic(Constants.CONNECTED_TEAM, LaneType.MOTORISED, 5, ComponentType.TRAFFIC_LIGHT, 1), 2),
					new SdmMessage(new SdmTopic(Constants.CONNECTED_TEAM, LaneType.MOTORISED, 1, ComponentType.TRAFFIC_LIGHT, 0), 2),
					new SdmMessage(new SdmTopic(Constants.CONNECTED_TEAM, LaneType.MOTORISED, 2, ComponentType.TRAFFIC_LIGHT, 0), 2), },
			new SdmMessage[] { new SdmMessage(new SdmTopic(Constants.CONNECTED_TEAM, LaneType.MOTORISED, 6, ComponentType.TRAFFIC_LIGHT, 0), 2),
					new SdmMessage(new SdmTopic(Constants.CONNECTED_TEAM, LaneType.MOTORISED, 8, ComponentType.TRAFFIC_LIGHT, 0), 2),
					new SdmMessage(new SdmTopic(Constants.CONNECTED_TEAM, LaneType.MOTORISED, 0, ComponentType.TRAFFIC_LIGHT, 0), 2),
					new SdmMessage(new SdmTopic(Constants.CONNECTED_TEAM, LaneType.MOTORISED, 3, ComponentType.TRAFFIC_LIGHT, 0), 2), },
			new SdmMessage[] { new SdmMessage(new SdmTopic(Constants.CONNECTED_TEAM, LaneType.MOTORISED, 0, ComponentType.TRAFFIC_LIGHT, 0), 2),
					new SdmMessage(new SdmTopic(Constants.CONNECTED_TEAM, LaneType.MOTORISED, 3, ComponentType.TRAFFIC_LIGHT, 0), 2),
					new SdmMessage(new SdmTopic(Constants.CONNECTED_TEAM, LaneType.MOTORISED, 1, ComponentType.TRAFFIC_LIGHT, 0), 2),
					new SdmMessage(new SdmTopic(Constants.CONNECTED_TEAM, LaneType.MOTORISED, 1, ComponentType.TRAFFIC_LIGHT, 1), 2),
					new SdmMessage(new SdmTopic(Constants.CONNECTED_TEAM, LaneType.MOTORISED, 2, ComponentType.TRAFFIC_LIGHT, 0), 2), },
			new SdmMessage[] { new SdmMessage(new SdmTopic(Constants.CONNECTED_TEAM, LaneType.MOTORISED, 1, ComponentType.TRAFFIC_LIGHT, 0), 2),
					new SdmMessage(new SdmTopic(Constants.CONNECTED_TEAM, LaneType.MOTORISED, 1, ComponentType.TRAFFIC_LIGHT, 1), 2),
					new SdmMessage(new SdmTopic(Constants.CONNECTED_TEAM, LaneType.MOTORISED, 5, ComponentType.TRAFFIC_LIGHT, 0), 2),
					new SdmMessage(new SdmTopic(Constants.CONNECTED_TEAM, LaneType.MOTORISED, 5, ComponentType.TRAFFIC_LIGHT, 1), 2),
					new SdmMessage(new SdmTopic(Constants.CONNECTED_TEAM, LaneType.MOTORISED, 2, ComponentType.TRAFFIC_LIGHT, 0), 2), },
			new SdmMessage[] { new SdmMessage(new SdmTopic(Constants.CONNECTED_TEAM, LaneType.MOTORISED, 4, ComponentType.TRAFFIC_LIGHT, 0), 2),
					new SdmMessage(new SdmTopic(Constants.CONNECTED_TEAM, LaneType.MOTORISED, 7, ComponentType.TRAFFIC_LIGHT, 0), 2),
					new SdmMessage(new SdmTopic(Constants.CONNECTED_TEAM, LaneType.MOTORISED, 2, ComponentType.TRAFFIC_LIGHT, 0), 2), },
			new SdmMessage[] { new SdmMessage(new SdmTopic(Constants.CONNECTED_TEAM, LaneType.CYCLE, 0, ComponentType.TRAFFIC_LIGHT, 0), 2),
					new SdmMessage(new SdmTopic(Constants.CONNECTED_TEAM, LaneType.CYCLE, 1, ComponentType.TRAFFIC_LIGHT, 0), 2), new SdmMessage(new SdmTopic(Constants.CONNECTED_TEAM, LaneType.CYCLE, 2, ComponentType.TRAFFIC_LIGHT, 0), 2),
					new SdmMessage(new SdmTopic(Constants.CONNECTED_TEAM, LaneType.CYCLE, 3, ComponentType.TRAFFIC_LIGHT, 0), 2), new SdmMessage(new SdmTopic(Constants.CONNECTED_TEAM, LaneType.CYCLE, 4, ComponentType.TRAFFIC_LIGHT, 0), 2),
					new SdmMessage(new SdmTopic(Constants.CONNECTED_TEAM, LaneType.FOOT, 0, ComponentType.TRAFFIC_LIGHT, 0), 2), new SdmMessage(new SdmTopic(Constants.CONNECTED_TEAM, LaneType.FOOT, 1, ComponentType.TRAFFIC_LIGHT, 0), 2),
					new SdmMessage(new SdmTopic(Constants.CONNECTED_TEAM, LaneType.FOOT, 2, ComponentType.TRAFFIC_LIGHT, 0), 2), new SdmMessage(new SdmTopic(Constants.CONNECTED_TEAM, LaneType.FOOT, 6, ComponentType.TRAFFIC_LIGHT, 0), 2),
					new SdmMessage(new SdmTopic(Constants.CONNECTED_TEAM, LaneType.FOOT, 3, ComponentType.TRAFFIC_LIGHT, 0), 2), new SdmMessage(new SdmTopic(Constants.CONNECTED_TEAM, LaneType.FOOT, 4, ComponentType.TRAFFIC_LIGHT, 0), 2),
					new SdmMessage(new SdmTopic(Constants.CONNECTED_TEAM, LaneType.FOOT, 5, ComponentType.TRAFFIC_LIGHT, 0), 2),

			}, };

	/**
	 * Returns an array of prepared message to be sent when given topic is on a positive value
	 * 
	 * @param topic the topic
	 * @return an array of messages to send
	 */
	public static SdmMessage[] getRelatedGroups(SdmTopic topic, SdmController p) {
		publisher = p;
		return getMessages(topic);
	}

	/**
	 * Finds group with most participants waiting, prioritises first in array.
	 * 
	 * @param messageArrays
	 * @return messages to send
	 */
	private static SdmMessage[] findBusiestGroup(SdmMessage[][] messageArrays, SdmTopic filter) {
		int weight = 0;
		SdmMessage[] result = null;

		for (SdmMessage[] messageArray : messageArrays) {
			int curWeight = 0;
			if (filter != null) {
				boolean found = false;
				for (SdmMessage msg : messageArray) {
					if (msg.getTopic().equals(filter))
						found = true;
				}
				if (!found)
					continue;
			}
			for (SdmMessage msg : messageArray) {
				for (Tuple<SdmTopic, byte[]> sdmMessage : publisher.getSensorStatus()) {
					if (sdmMessage.getLeft().equals(msg.getTopic())) {
						if (SdmHelper.bytesToInt(sdmMessage.getRight()) >= 1) {
							curWeight++;
						}
					}
				}
			}
			if (curWeight > weight) {
				result = messageArray;
			}
		}

		return result;
	}

	/**
	 * Returns messages (that should be set) related to the topic
	 * 
	 * @param topic
	 * @return message array of which to send on new threads
	 */
	private static SdmMessage[] getMessages(SdmTopic topic) {
		List<SdmMessage> result = new ArrayList<SdmMessage>();

		String teamId = Constants.CONNECTED_TEAM;

		if (topic.getLaneType().getTopicName() == LaneType.TRACK.getTopicName()) {
			if (topic.getGroupId() == "0" && topic.getComponentId() == "0" && topic.getComponentType() == ComponentType.TRAIN_LIGHT) {
				result.add(new SdmMessage(new SdmTopic(teamId, LaneType.CYCLE, 2, ComponentType.TRAFFIC_LIGHT, 0), 2));
				result.add(new SdmMessage(new SdmTopic(teamId, LaneType.CYCLE, 3, ComponentType.TRAFFIC_LIGHT, 0), 2));
				result.add(new SdmMessage(new SdmTopic(teamId, LaneType.FOOT, 3, ComponentType.TRAFFIC_LIGHT, 0), 2));
				result.add(new SdmMessage(new SdmTopic(teamId, LaneType.FOOT, 4, ComponentType.TRAFFIC_LIGHT, 0), 2));
				result.add(new SdmMessage(new SdmTopic(teamId, LaneType.FOOT, 5, ComponentType.TRAFFIC_LIGHT, 0), 2));
				result.add(new SdmMessage(new SdmTopic(teamId, LaneType.MOTORISED, 5, ComponentType.TRAFFIC_LIGHT, 0), 0));
				result.add(new SdmMessage(new SdmTopic(teamId, LaneType.MOTORISED, 6, ComponentType.TRAFFIC_LIGHT, 0), 0));
				for (SdmMessage msg : findBusiestGroup(trackOccupiedGroups, null)) {
					result.add(msg);
				}
			}
		} else {
			for (SdmMessage sdmMessage : findBusiestGroup(trackFreeGroups, topic)) {
				result.add(sdmMessage);
			}
		}

		return (SdmMessage[]) result.toArray();
	}

}
