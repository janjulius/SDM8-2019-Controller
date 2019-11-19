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
	
	private static final ComponentType[] enumValues = ComponentType.values();
	
	public static ComponentType fromInteger(int x) {
        return enumValues[x];
    }
	
	public static ComponentType fromInteger(String x) {
		int number = Integer.parseInt(x);
        return enumValues[number];
    }
}
