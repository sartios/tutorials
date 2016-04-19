package com.ssavr.tutorials.sportify.dto;

import com.ssavr.tutorials.sportify.utils.JSONUtil;

import org.json.JSONObject;

public class SearchResultDto {

	public SearchResultDto() {
	}

	public SearchResultDto(JSONObject jsonObject) {
		setId(jsonObject);
	}

	private void setId(JSONObject jsonObject) {
		this.id = JSONUtil.getString(jsonObject, "id");
	}

	public String getId() {
		return id;
	}

	private String id;
}
