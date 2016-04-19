package com.ssavr.tutorials.sportify.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtil {

	public static JSONObject getJsonObject(String str) {
		JSONObject object = new JSONObject();
		try {
			object = new JSONObject(str);
		} catch (JSONException e) {
			System.err.println(e.getMessage());
		}
		return object;
	}

	public static List<String> getList(String arrParam, String objParam,
			JSONObject jsonObject) {
		List<String> tmpList = new ArrayList<String>();
		JSONArray jsonArray;
		try {
			jsonArray = jsonObject.getJSONArray(arrParam);
			if (jsonArray != null) {
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonTmpObject = jsonArray.getJSONObject(i);
					tmpList.add(getString(jsonTmpObject, objParam));
				}
			}
		} catch (JSONException e) {
		}

		return tmpList;
	}

	public static String getString(JSONObject jsonObject, String key) {
		String value = "";
		try {
			value = jsonObject.getString(key);
		} catch (JSONException e) {
		}
		return value;
	}
}
