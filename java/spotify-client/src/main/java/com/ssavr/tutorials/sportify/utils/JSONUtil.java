package com.ssavr.tutorials.sportify.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtil {

	public static JSONObject getJsonObject(String str) {
		JSONObject object = new JSONObject();
		try {
			object = new JSONObject(str);
		} catch (JSONException e) {
			_log.error(e.getMessage());
		}
		return object;
	}

	public static JSONArray getJsonArray(JSONObject jsonObject, String key) {
		JSONArray jsonArray = new JSONArray();
		try {
			jsonArray = jsonObject.getJSONArray(key);
		} catch (JSONException e) {
			_log.error(e.getMessage());
		}
		return jsonArray;
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
			_log.error(e.getMessage());
		}
		return value;
	}

	public static JSONObject getJsonObject(JSONArray jsonArray, int index) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = jsonArray.getJSONObject(index);
		} catch (JSONException e) {
			_log.error(e.getMessage());
		}
		return jsonObject;
	}


	public static JSONObject getJsonObject(JSONObject jsonObject, String key) {
		JSONObject appJsonObject = null;
		try {
			appJsonObject = jsonObject.getJSONObject(key);
		} catch (JSONException e) {
		}
		return appJsonObject;
	}
	

	private final static Logger _log = Logger.getLogger(JSONUtil.class);
}
