package com.go.broker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;


/**
 * 
 * @author Vojin Nikolic
 * 
 * This class is responsible for parsing Json
 *
 */
public class JsonHandler {

	//Set of columns to display
	private ArrayList<String> setOfColumns = new ArrayList<String>();
	
	public JsonHandler() {
		setOfColumns.add("_id");
		setOfColumns.add("name");
		setOfColumns.add("type");
		setOfColumns.add("latitude");
		setOfColumns.add("longitude");
	}
	
	/**
	 * Handling initial jsonArray
	 * @param jsonArray
	 * @return
	 * @throws Exception
	 * @see
	 */
	public List<Map<String, String>> handleAsJsonArray(JsonArray jsonArray) throws JsonParseException {
	    List<Map<String, String>> listOfJsonObj = null;
	    try {
	        listOfJsonObj = parseArray(jsonArray);
	    } catch (Exception e) {
	    	e.printStackTrace();
	        throw new JsonParseException("Json has bad structure");
	    }
	    return listOfJsonObj;
	}
	
	/**
	 * Iterates through jsonArray 
	 * @param jsonArray
	 * @return
	 * @see
	 */
	public List<Map<String, String>> parseArray(JsonArray jsonArray) {
	    List<Map<String, String>> listOfJsonObj = new ArrayList<Map<String, String>>();
	    int length = jsonArray.size();
	    for (int i = 0; i < length; i++) {
	        JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
	        Map<String, String> stringMap = parseObject(jsonObject);
	        listOfJsonObj.add(stringMap);
	    }
	    return listOfJsonObj;
	}
	
	/**
	 *  Parse each object from jsonArray
	 * @param jsonObject
	 * @return
	 * @see
	 */
	public Map<String, String> parseObject(JsonObject jsonObject) {
	    Map<String, String> mapJson = new HashMap<String, String>();
	    flatten(jsonObject, mapJson, "");
	    return mapJson;
	}


	/**
	 * Recursively parses Json Object depending is it an {@link JsonArray} or {@link JsonObject} 
	 * @param jsonObjectIn
	 * @param flatJson
	 * @param prefix
	 * @see
	 */
	private void flatten(JsonObject jsonObjectIn, Map<String, String> flatJson, String prefix) {
		Set<Entry<String, JsonElement>> entrySet = jsonObjectIn.entrySet();
		for (Map.Entry<String, JsonElement> entry : entrySet) {
			String key = entry.getKey().toString();
			if (jsonObjectIn.get(key).isJsonObject()) {
				JsonObject jsonObject = jsonObjectIn.get(key).getAsJsonObject();
				flatten(jsonObject, flatJson, prefix);
			} else if (jsonObjectIn.get(key).isJsonArray()) {
				JsonArray jsonArray = jsonObjectIn.get(key).getAsJsonArray();
				if (jsonArray.size() < 1) {
					continue;
				}
				flatten(jsonArray, flatJson, key);
			} else {
				if (!jsonObjectIn.get(key).isJsonNull() && setOfColumns.contains(key)) {
					String value = jsonObjectIn.get(key).getAsString();
					flatJson.put(prefix + key, value);
				}

			}
		}
	}
	
	/**
	 * Recursively parses Json Array depending is it an {@link JsonArray} or {@link JsonObject} 
	 * @param jsonArrayIn
	 * @param flatJson
	 * @param prefix
	 * @see
	 */
	private void flatten(JsonArray jsonArrayIn, Map<String, String> flatJson, String prefix) {
		int length = jsonArrayIn.size();
		for (int i = 0; i < length; i++) {
			if (jsonArrayIn.get(i).isJsonArray()) {
				JsonArray jsonArray = jsonArrayIn.get(i).getAsJsonArray();
				if (jsonArray.size() < 1) {
					continue;
				}
				flatten(jsonArray, flatJson, prefix + i);
			} else if (jsonArrayIn.get(i).isJsonObject()) {
				JsonObject jsonObject = jsonArrayIn.get(i).getAsJsonObject();
				flatten(jsonObject, flatJson, prefix + (i + 1));
			} else {
				String value = jsonArrayIn.get(i).getAsString();
				if (value != null)
					flatJson.put(prefix + (i + 1), value);
			}
		}
	}
}
