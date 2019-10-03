/**
 * 
 */
package logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Logger
 * 
 * @author Jan Julius de Lang
 * @date Oct 3, 2019
 */
public class SdmLogger {

	/**
	 * The text of the logger
	 */
	private static List<String> text;

	/**
	 * Constructs a new {@link SdmLogger}
	 */
	private SdmLogger() {

	}

	/**
	 * Logs a message
	 * 
	 * @param objName Name of the object
	 * @param message The message
	 */
	public static void Log(String objName, Object message) {
		text.add("[" + objName + "] " + message);
		System.out.println(text.get(text.size() - 1));
	}

	/**
	 * Logs a message
	 * 
	 * @param objName Name of the object
	 * @param message The message
	 */
	public static void Log(String objName, String message) {
		Log(objName, message);
	}

	/**
	 * Saves the state of the current session
	 * 
	 * @throws IOException
	 */
	public static void Save() throws IOException {
		Path file = Paths.get("Sdm Log " + System.currentTimeMillis() + ".txt");
		Files.write(file, text, StandardCharsets.UTF_8);
	}
}
