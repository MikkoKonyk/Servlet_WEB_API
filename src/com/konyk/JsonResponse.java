package com.konyk;

import java.util.ArrayList;

public class JsonResponse {
	private ArrayList<String> text;
	private MetaData metaData;
	
	public JsonResponse() {
		text = new ArrayList<String>();
	}
	
	public void setTextData(String text){
		this.text.add(text);
	}

	public ArrayList<String> getText() {
		return text;
	}

	public void setText(ArrayList<String> text) {
		this.text = text;
	}

	public MetaData getMetaData() {
		return metaData;
	}

	public void setMetaData(MetaData metaData) {
		this.metaData = metaData;
	}

	@Override
	public String toString() {
		return "{"+"\"text\":" + text + ", \"metaData\":" + "{" + metaData+ "}}";
	}
	
	
}
