/**
 * 
 */
package mqtt;

import enums.ComponentType;
import enums.LaneType;

/**
 * SdmTopic Represents an SdmTopic
 * 
 * @author Jan Julius de Lang
 * @author Thomas Tijsma
 * @author Marco Geertsma
 * @author Lars Schipper
 * @date Oct 4, 2019
 */
public class SdmTopic {

	/**
	 * The team id of the topic
	 */
	private String teamId;

	/**
	 * The lane type
	 */
	private LaneType laneType;

	/**
	 * The id of the traffic group
	 */
	private String groupId;

	/**
	 * The componentType
	 */
	private ComponentType componentType;

	/**
	 * The componentId
	 */
	private String componentId;

	/**
	 * Constructs a new {@link SdmTopic}
	 */
	public SdmTopic(String teamId, LaneType laneType, String groupId, ComponentType componentType, String componentId) {
		this.teamId = teamId;
		this.laneType = laneType;
		this.groupId = groupId;
		this.componentType = componentType;
		this.componentId = componentId;
	}

	public SdmTopic(String teamId, LaneType laneType, Integer groupId, ComponentType componentType, Integer componentId) {
		this(teamId, laneType, groupId.toString(), componentType, componentId.toString());
	}

	public SdmTopic(String topic) {
		this(getTopics(topic)[0], LaneType.fromString(getTopics(topic)[1]), getTopics(topic)[2], ComponentType.fromString(getTopics(topic)[3]), getTopics(topic)[4]);
	}

	public SdmTopic getCorrespondingTrafficLight() {
		if (this.laneType == LaneType.VESSEL)
			this.componentType = ComponentType.BOAT_LIGHT;
		else if (this.laneType == LaneType.TRACK)
			this.componentType = ComponentType.TRAIN_LIGHT;
		else
			this.componentType = ComponentType.TRAFFIC_LIGHT;

		this.componentId = "0";
		return this;
	}

	public SdmTopic getCorrespondingSensors() {
		this.componentType = ComponentType.SENSOR;
		this.componentId = "0";
		return this;
	}

	private static String[] getTopics(String topic) {
		return topic.split("/");
	}

	public ComponentType getComponentType() {
		return componentType;
	}

	public String getComponentId() {
		return componentId;
	}

	public LaneType getLaneType() {
		return laneType;
	}

	public String getGroupId() {
		return groupId;
	}

	public boolean fundamentallyTheSameAs(SdmTopic b) {
		return groupId.equals(b.groupId);
	}

	@Override
	public String toString() {
		return String.format("%s/%s/%s/%s/%s", teamId, laneType.getTopicName(), groupId, componentType.getTopicName(), componentId);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SdmTopic topic = (SdmTopic) o;
//		System.out.println(topic.toString() + " " + o.toString() + " " + (topic.toString().equals(o.toString())) + (teamId.equals(topic.teamId) && laneType.equals(topic.laneType) && groupId.equals(topic.groupId) && componentType.equals(topic.componentType) && componentId.equals(topic.componentId)));
//		System.out.println(teamId.equals(topic.teamId));
//		System.out.println(laneType.equals(topic.laneType));
//		System.out.println(groupId.equals(topic.groupId));
//		System.out.println(componentType.equals(topic.componentType));
//		System.out.println(componentId.equals(topic.componentId));
		return teamId.equals(topic.teamId) && laneType.equals(topic.laneType) && groupId.equals(topic.groupId) && componentType.equals(topic.componentType) && componentId.equals(topic.componentId);

	}

}
