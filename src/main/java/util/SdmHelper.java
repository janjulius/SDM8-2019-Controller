/**
 * 
 */
package util;

import java.nio.ByteBuffer;

/**
 * SdmHelper
 * @author Jan Julius de Lang
 * @date Oct 4, 2019
 */
public class SdmHelper {

	/**
	 * Converts an integer to a byte array
	 * @param i the integer
	 * @return byte array which represents the integer {@link i}
	 */
	public static byte[] intToBytes(final int i) {
		ByteBuffer b = ByteBuffer.allocate(4);
		b.putInt(i);
		return b.array();
	}
}
