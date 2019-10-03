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
	 * Jan Julius: The ordinal direction
	 * Thomas: The cardinal direction
	 */
	private Direction direction;
	
	/**
	 * The id of the traffic group
	 */
	private int groupId;
	
	/**
	 * The subgroup of the {@link groupId}
	 */
	private int subgroupId;
	
	/**
	 * The componentType
	 */
	private ComponentType componentType;
	
	/**
	 * The componentId
	 */
	private int componentId;
	
	/**
	 * Constructs a new {@link SdmTopic}
	 */
	public SdmTopic(String teamId, LaneType laneType, Direction direction, int groupId, int subgroupId, ComponentType componentType, int componentId) {
		this.teamId = teamId;
		this.laneType = laneType;
		this.direction = direction;
		this.groupId = groupId;
		this.subgroupId = subgroupId;
		this.componentType = componentType;
		this.componentId = componentId;
	}
	
	@Override
	public String toString() {
		return String.format("%s/%s/%s/%s/%s/%s/%s", teamId, laneType.getTopicName(), direction.getTopicName(), groupId, subgroupId, componentType.getTopicName(), componentId);
	}
	
}
