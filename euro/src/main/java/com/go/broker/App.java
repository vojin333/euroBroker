package com.go.broker;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

/**
 * 
 * @author Vojin Nikolic
 * Main class
 *
 */
public class App {
	public static void main(String[] args) {

		System.out.println("Hello World!");
		// RestBroker restBroker = new RestBroker();
		// restBroker.returnJson("Berlin");

		System.out.println("Args = " + args.toString());

		JsonHandler jsonHandler = new JsonHandler();
		CSVWriter csvWriter = new CSVWriter();

		try {
			Reader json = new FileReader("D:/My Stuff/JsonToCsv/euro/json/json.json");

			JsonParser parser = new JsonParser();
			JsonArray jsonArray = parser.parse(json).getAsJsonArray();
			if (jsonArray.size() != 0) {
				List<Map<String, String>> flatJson = jsonHandler.handleAsJsonArray(jsonArray);
				csvWriter.writeAsCSV(flatJson, "sample.csv");
			} else {
				System.out.println("Response is Empty");
			}

			// csvWriter.writeToFile(jsonHandler.parseJson().toString(),
			// "json.csv");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//        try {
//
//			URL url = new URL("http://api.goeuro.com/api/v2/position/suggest/en/Berlin");
//			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//			conn.setRequestMethod("GET");
//			conn.setRequestProperty("Accept", "application/json");
//
//			if (conn.getResponseCode() != 200) {
//				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
//			}
//
//			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
//
//			String output;
//			System.out.println("Output from Server .... \n");
//			while ((output = br.readLine()) != null) {
//				System.out.println(output);
//			}
//
//			conn.disconnect();
//
//		} catch (MalformedURLException e) {
//
//    		e.printStackTrace();
//
//    	  } catch (IOException e) {
//
//    		e.printStackTrace();
//
//    	  }
    }
    

}
