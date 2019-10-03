/**
 * 
 */
package util;

import enums.ComponentType;
import enums.Direction;
import enums.LaneType;

/**
 * TopicHelper
 * Helps to construct topic strings
 * team_id/lane_type/cardinal_direction/group_id/subgroup_id/component_type/component_id/
 * @author Jan Julius de Lang
 * @date Oct 3, 2019
 */
public class TopicHelper {

	/**
	 * Constructs the topic string
	 * @param teamId The id of the team to send to
	 * @param laneType The type of lane
	 * @param direction The ordinal direction
	 * @param groupId The id of the traffic group
	 * @param subgroupId The subgroup of the group
	 * @param componentType The type of component
	 * @param componentId The id of the component
	 * @return The topic string
	 */
	public static String constructTopic(String teamId, LaneType laneType, Direction direction, int groupId, int subgroupId, ComponentType componentType, int componentId) {
		return String.format("%s/%s/%s/%s/%s/%s/%s", teamId, laneType.getTopicName(), direction.getTopicName(), groupId, subgroupId, componentType.getTopicName(), componentId);
	}
}
