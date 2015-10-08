package com.go.broker;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import javax.xml.ws.WebServiceException;

import com.go.broker.csv.CSVWriter;
import com.go.broker.jsonHandler.JsonHandler;
import com.go.broker.restClient.RestBroker;
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

		String city = null;
		if (args.length != 0) {
			city = args[0];
		}
		
		if (city != null) {
			System.out.println("Starting .... for city = " + city);
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
						csvWriter.writeAsCSV(flatJson, city + ".csv");
						System.out.println("File has been written under /target/" + city + ".csv");
					} else {
						System.out.println("Response array is Empty,  No Inforamtions for city " + city);
					}
				} else {
					System.out.println("Response is null");
				}

			} catch (FileNotFoundException e) {
				System.out.println("Error while writing csv" + "  Reason = " + e.getMessage());
			} catch (WebServiceException e) {
				System.out.println("Error while calling rest service" + "  Reason = " + e.getMessage());
			} catch (JsonParseException e) {
				System.out.println("Error while parsing Json" + "  Reason = " + e.getMessage());
			}
		} else {
			System.out.println("Run jar with city as arrgument");
			System.exit(0);
		}
	}
}
