/**
 * 
 */
package enums;


/**
 * ComponentType
 * Represents a component type
 * @author Jan Julius de Lang
 * @author Lars Schipper
 * @date Oct 3, 2019
 */
public enum ComponentType {

	TRAFFIC_LIGHT(0, "traffic_light"),
	WARNING_LIGHT(1, "warning_light"),
	SENSOR(2, "sensor"),
	BARRIER(3, "barrier"),
	BOAT_LIGHT(4, "boat_light"),
	DECK(5, "deck"),
	TRAIN_LIGHT(6, "train_light")
	;
	
	/**
	 * Constructs a new {@link ComponentType}
	 */
	ComponentType(int uid, String topicName){
		this.uid = uid;
		this.topicName = topicName;
	}
	
	/**
	 * The uid
	 */
	public int uid;
	
	/**
	 * The topic name
	 */
	public String topicName;
	
	public int getUid() {
		return uid;
	}
	
	public String getTopicName() {
		return topicName;
	}
	
	/**
	 * {@link ComponentType} enum converted to an array of {@link ComponentType}
	 */
	private static final ComponentType[] enumValues = ComponentType.values();
	
	/**
	 * Returns the ComponentType based on the id in the sequence
	 * @param x the id
	 * @return The {@link ComponentType}
	 */
	public static ComponentType fromInteger(int x) {
        return enumValues[x];
    }
	
	/**
	 * Returns the ComponentType based on the id in the sequence
	 * @param x the id
	 * @return The {@link LaneType}
	 */
	public static ComponentType fromInteger(String x) {
		int number = Integer.parseInt(x);
        return enumValues[number];
    }
	
	/**
	 * Returns the ComponentType based on the id in the sequence
	 * @param x the id
	 * @return The {@link ComponentType}
	 */
	public static ComponentType fromString(String x) {
		return ComponentType.valueOf(x.toUpperCase());
	}
}
