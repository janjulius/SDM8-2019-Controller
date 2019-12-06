/**
 * 
 */
package util;

/**
 * Constants
 * 
 * @author Jan Julius de Lang
 * @date Oct 3, 2019
 */
public class Constants {

	/**
	 * The default protocol
	 */
	public static final String PROTOCOL = "tcp://";

	/**
	 * The default address to connect to
	 */
	public static final String ADDRESS = "62.210.180.72";

	/**
	 * The default port
	 */
	public static final String PORT = "1883";

	/**
	 * Team id to connect to
	 */
	public static final String CONNECTED_TEAM = "8";
	
	/**
	 * The default barrier transition in ms
	 */
	public static final int BARRIER_TRANSITION = 4_000;
	
	/**
	 * The default deck transistion in ms
	 */
	public static final int DECK_TRANSITION = 10_000;
	
	/**
	 * The default clearing time in ms
	 */
	public static final int DEFAULT_CLEAR_TIME = 3_000;
	
	/**
	 * The time a traffic light will be green for in ms
	 */
	public static final int DEFAULT_GREEN_TIME = 10_000;
}
