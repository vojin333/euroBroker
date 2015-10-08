package com.go.broker;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;
import javax.xml.ws.WebServiceException;

import com.google.gson.JsonArray;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

/**
 * 
 * @author Vojin Nikolic
 * Main class
 *
 */
public class App {
	public static void main(String[] args) {

		String city = args[0];
		System.out.println("Hello World!" + city);
		
		if (city != null) {
			try {
				RestBroker restBroker = new RestBroker();
				JsonHandler jsonHandler = new JsonHandler();
				CSVWriter csvWriter = new CSVWriter();
				
				String response = restBroker.returnResponse(city);
				if (response != null) {
					JsonParser parser = new JsonParser();
					JsonArray jsonArray = parser.parse(response).getAsJsonArray();
					if (jsonArray.size() != 0) {
						List<Map<String, String>> flatJson = jsonHandler.handleAsJsonArray(jsonArray);
						csvWriter.writeAsCSV(flatJson, "sample.csv");
					} else {
						System.out.println("Response array is Empty,  No Inforamtions for city " + city);
					}
				} else {
					System.out.println("Response is null");
				}

			} catch (FileNotFoundException e) {
				System.out.println("Error while writing csv");
				e.printStackTrace();
			} catch (WebServiceException e) {
				// TODO Auto-generated catch block
				System.out.println("Error while calling rest service");
				e.printStackTrace();
			} catch (JsonParseException e) {
				System.out.println("Error while parsing Json");
				e.printStackTrace();
			}
		} else {
			System.out.println("Run jar with city as arrgument");
			System.exit(0);
		}
	}
}
