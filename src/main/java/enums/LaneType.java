/**
 * 
 */
package enums;


/**
 * LaneType
 * Represents a lane type
 * @author Jan Julius de Lang
 * @date Oct 3, 2019
 */
public enum LaneType {

	FOOT(0, "foot"),
	CYCLE(1, "cycle"),
	MOTORISED(2, "motorised"),
	VESSEL(3, "vessel"),
	TRACK(4, "track"),
	TRAIN(5, "track"),
	BOAT(6, "vessel"),
	ALL(7, "+")
	;
	
	/**
	 * Constructs a new {@link LaneType}
	 */
	LaneType(int uid, String topicName) {
		this.uid = uid;
		this.topicName = topicName;
	}
	
	/**
	 * The uid
	 */
	private int uid;
	
	/**
	 * The topic name
	 */
	private String topicName;
	
	
	public String getTopicName() {
		return topicName;
	}
	
	public int getUid() {
		return uid;
	}
	
}
