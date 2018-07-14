package tool_bt1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainApplication {

	private static String Url = "http://news.admicro.vn:10002/api/realtime?domain=kenh14.vn";
	private static final Logger LOGGER = LoggerFactory.getLogger(MainApplication.class);

	public static void main(String[] args) throws IOException, JSONException {
		LOGGER.trace("s");
		System.out.println(MainApplication.class);

		final long MAX_TIME = 30; // max time seconds

		long startTime = System.currentTimeMillis();
		long currTime = System.currentTimeMillis();
		long lastWriteLogTime = startTime - 10;

		int numCurrUsers = 0;
		int numLastWriteLogUsers = 0;

		Random R = new Random();

		LinkedList<UserLog> userDebugLogs = new LinkedList<UserLog>();

		while (((currTime = System.currentTimeMillis()) - startTime) < MAX_TIME * 1000) {
			if ((currTime - lastWriteLogTime) > 1000 * 2) {
				// System.out.println("currTime - lastTime = " + (currTime - lastWriteLogTime));
				lastWriteLogTime = currTime;

				numCurrUsers = getNumCurrUsers();
//				System.out.println("Curr user : " + numCurrUsers + ", num last write user : " + numLastWriteLogUsers);
				
				if (numCurrUsers > 1.01 * numLastWriteLogUsers) {
					numLastWriteLogUsers = numCurrUsers;
					LOGGER.info("Num user : {}", numCurrUsers);
				} else {
					userDebugLogs.addLast(new UserLog(currTime, numCurrUsers));
				}

				while (!userDebugLogs.isEmpty()) {
					UserLog userLog = userDebugLogs.peek();
					if ((currTime - userLog.getLogTime()) < 12 * 1000) {
						break;
					}
					// pop user log and write debug log
					LOGGER.debug("Num user : {}", userLog.getNumUsers());
					numLastWriteLogUsers = userLog.getNumUsers();
					userDebugLogs.pop();
				}

			}
		}
		System.out.println("Done");
	}

	private static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			is.close();
		}
	}

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	private static int getNumCurrUsers() throws IOException, JSONException {
		JSONObject json = readJsonFromUrl(Url);

		return json.getInt("user");
	}

}
