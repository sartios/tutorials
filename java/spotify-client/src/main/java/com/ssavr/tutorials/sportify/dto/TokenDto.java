package com.ssavr.tutorials.sportify.dto;

import com.ssavr.tutorials.sportify.utils.JSONUtil;

import org.json.JSONObject;

public class TokenDto {

	public TokenDto() {
	}

	public TokenDto(JSONObject tokenJson) {
		setAccessToken(tokenJson);
		setTokenType(tokenJson);
	}

	private void setTokenType(JSONObject tokenJson) {
		accessToken = JSONUtil.getString(tokenJson, "access_token");
	}

	private void setAccessToken(JSONObject tokenJson) {
		tokenType = JSONUtil.getString(tokenJson, "token_type");
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	private String accessToken;
	private String tokenType;
}
