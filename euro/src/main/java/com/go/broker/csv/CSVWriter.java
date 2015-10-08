package com.go.broker.csv;



import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.BufferOverflowException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author Vojin Nikolic
 * This class is responsible for writing file in desired format
 *
 */
public class CSVWriter {

	/**
	 * Initial method that is exposed to write file
	 * @param flatJson
	 * @param fileName
	 * @throws FileNotFoundException
	 * @see
	 */
	public void writeAsCSV(List<Map<String, String>> flatJson, String fileName) throws FileNotFoundException {
		Set<String> headers = collectHeaders(flatJson);
		String output = StringUtils.join(headers.toArray(), ",") + "\n";
		for (Map<String, String> map : flatJson) {
			output = output + getCommaSeperatedRow(headers, map) + "\n";
		}
		writeToFile(output, fileName);
	}

	/**
	 * Open {@link BufferOverflowException}
	 * @param output
	 * @param fileName
	 * @throws FileNotFoundException
	 * @see
	 */
	private void writeToFile(String output, String fileName) throws FileNotFoundException {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(fileName));
			writer.write(output);
		} catch (IOException e) {
			throw new FileNotFoundException(e.getMessage());
		} finally {
			close(writer);
		}
	}

	/**
	 * close {@link BufferedWriter}
	 * @param writer
	 * @see
	 */
	private void close(BufferedWriter writer) {
		try {
			if (writer != null) {
				writer.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param headers
	 * @param map
	 * @return
	 * @see
	 */
	private String getCommaSeperatedRow(Set<String> headers, Map<String, String> map) {
		List<String> items = new ArrayList<String>();
		for (String header : headers) {
			String value = map.get(header) == null ? "" : map.get(header).replace(",", "");
			items.add(value);
		}
		return StringUtils.join(items.toArray(), ",");
	}

	/**
	 * Collecting Headers and Columns
	 * @param flatJson
	 * @return
	 * @see
	 */
	private Set<String> collectHeaders(List<Map<String, String>> flatJson) {
		Set<String> headers = new TreeSet<String>();
		for (Map<String, String> map : flatJson) {
			headers.addAll(map.keySet());
		}
		return headers;
	}
}
