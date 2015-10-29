package com.konyk;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class JsonChecking {

	public static void main(String[] args) {
		String url_ = "http://localhost:8080/ServletWebGL/?q=java&limit=13&length=10&includeMetaData=true";

		URL url;
		StringBuilder sb;

		try {
			url = new URL(url_);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			br.close();
			
			JSONObject json = new JSONObject(sb.toString());
			String[] text = json.getNames(json);
			System.out.println(sb.toString());
			
			System.out.println("First element is JSONArray: " + json.getJSONArray("text").get(0));
			System.out.println("First element is JSONArray: " + json.getJSONArray("text").get(1));
			
			System.out.println("metaData: filename - " + json.getJSONObject("metaData").get("fileName"));
			System.out.println("metaData: fileCreationDate - " + json.getJSONObject("metaData").get("fileCreationDate"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
