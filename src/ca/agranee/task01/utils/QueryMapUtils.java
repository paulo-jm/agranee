package com.agranee.utils;

import java.util.HashMap;
import java.util.Map;

public class QueryMapUtils {

	private QueryMapUtils() {
	}

	public static Map<String, String> getAsMap(String str) {
		
		Map<String, String> map = new HashMap<String, String>();
		
		String[] parameters = str.split("&");
		
		for(String parameter : parameters) {
			
			String[] pSplit = parameter.split("=");
			
			map.put(pSplit[0], pSplit[1]);
			
		}
		
		return map;
		
	}

}
