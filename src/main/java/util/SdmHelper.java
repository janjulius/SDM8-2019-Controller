/**
 * 
 */
package util;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/**
 * SdmHelper
 * @author Jan Julius de Lang
 * @author Lars Schipper
 * @date Oct 4, 2019
 */
public class SdmHelper {

	/**
	 * Converts an integer to a byte array
	 * @param i the integer
	 * @return byte array which represents the integer {@link i}
	 * @throws UnsupportedEncodingException 
	 */
	public static byte[] intToBytes(final int i) {
		return new Integer(i).toString().getBytes();
	}
	
	/**
	 * Converts a byte array to integer
	 * @param b the array
	 * @return integer converted from byte {@link b}
	 */
	public static int bytesToInt(final byte[] b) {
		return (Integer.parseInt(new String(b)));
	}
}
