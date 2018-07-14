package tool_bt2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class MainApplication {

	public static void main(String[] args) throws IOException {

		String urlStr = "http://dantri.com.vn";
		int timeoutMillis = 5000;
		URL url = new URL(urlStr);

		Document doc = Jsoup.parse(url, timeoutMillis);
		System.out.println("Parse url : " + urlStr + " done");
		writeFile(doc);
	}

	private static void writeFile(Document doc) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
		String bySeconds = sdf.format(new Date(System.currentTimeMillis()));
		String folderName = "./Output_ToolBt2/";
		String fileName = folderName + bySeconds + ".txt";
		
		// Create directory
		File dir = new File(folderName);
		dir.mkdirs();
		
		File file = new File(fileName);
		
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file), "utf-8"));
			String data = doc.toString();
			bw.write(data);
			System.out.println("Write fo file : " + file.getAbsolutePath() + " done");

		} catch (Exception ex) {
			ex.printStackTrace();
		}finally {
			bw.close();
		}

	}

}
