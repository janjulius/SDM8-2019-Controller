/**
 * 
 */
package mqtt;


import enums.ComponentType;
import enums.Direction;
import enums.LaneType;

/**
 * SdmTopic
 * Represents an SdmTopic
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
		this (teamId, laneType, groupId.toString(), componentType, componentId.toString());
	}
	
	public SdmTopic(String topic) {
		this(GetTopics(topic)[0],
				LaneType.fromString(GetTopics(topic)[1]), 
				GetTopics(topic)[2],
				ComponentType.fromString(GetTopics(topic)[3]),
				GetTopics(topic)[4]
		);
	}
	
	public SdmTopic GetCorrespondingTrafficLight() {
		this.componentType = ComponentType.TRAFFIC_LIGHT;
		return this;
	}
	
	private static String[] GetTopics(String topic) {
		return topic.split("/");
	}
	
	@Override
	public String toString() {
		return String.format("%s/%s/%s/%s/%s", teamId, laneType.getTopicName(), groupId, componentType.getTopicName(), componentId);
	}
	
}
