/**
 * 
 */
package mqtt;

/**
 * Settings 
 * Settings for the SDM Controller
 * 
 * @author Jan Julius de Lang
 * @date Oct 3, 2019
 */
public class Settings {

	/**
	 * If the controller should automatically reconnect
	 */
	public static final boolean AUTOMATIC_RECONNECT = true;

	/**
	 * If old messages should be cleared on a new connection
	 */
	public static final boolean CLEAN_ON_START = true;

	/**
	 * Timeout time
	 */
	public static final int CONNECTION_TIMEOUT = 10;

}
