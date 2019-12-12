/**
 * 
 */
package enums;


/**
 * LaneType
 * Represents a lane type
 * @author Jan Julius de Lang
 * @author Lars Schipper
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
	
	/**
	 * {@link LaneType} enum converted to an array of {@link LaneType}
	 */
	private static final LaneType[] enumValues = LaneType.values();
	
	/**
	 * Returns the lanetype based on the id in the sequence
	 * @param x the id
	 * @return The {@link LaneType}
	 */
	public static LaneType fromInteger(int x) {
        return enumValues[x];
    }
	
	/**
	 * Returns the lanetype based on the id in the sequence
	 * @param x the id as string
	 * @return The {@link LaneType}
	 */
	public static LaneType fromInteger(String x) {
		int number = Integer.parseInt(x);
        return enumValues[number];
    }
	
	/**
	 * Gets the lanetype based on the {@link topicName} value
	 * @param x the string
	 * @return The {@link LaneType}
	 */
	public static LaneType fromString(String x) {
		return LaneType.valueOf(x.toUpperCase());
	}
	
}
