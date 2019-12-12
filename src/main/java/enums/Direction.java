package enums;

/**
 * Direction
 * Represents a direction
 * @author Jan Julius de Lang
 * @date Oct 3, 2019
 */
public enum Direction {

	NORTH(0, "north"),
	EAST(1, "east"),
	SOUTH(2, "south"),
	WEST(3, "west"),
	;
	
	/**
	 * Constructs a new {@link Direction}
	 */
	Direction(int uid, String topicName){
		this.uid = uid;
		this.topicName = topicName;
	}
	
	/**
	 * The uid
	 */
	private int uid;
	
	/**
	 * The name used to communicate with topic
	 */
	private String topicName;
	
	public int getUid() {
		return uid;
	}
	
	public String getTopicName() {
		return topicName;
	}
	
}
