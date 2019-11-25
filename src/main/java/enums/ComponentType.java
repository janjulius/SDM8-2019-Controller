/**
 * 
 */
package enums;


/**
 * ComponentType
 * Represents a component type
 * @author Jan Julius de Lang
 * @date Oct 3, 2019
 */
public enum ComponentType {

	TRAFFIC_LIGHT(0, "traffic_light"),
	WARNING_LIGHT(1, "warning_light"),
	SENSOR(2, "sensor"),
	BARRIER(3, "barrier"),
	BOAT_LIGHT(4, "boat_light"),
	DECK(5, "deck"),
	DECK_SENSOR(6, "deck_sensor"),
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
	
}
