package com.ssavr.tutorials.sportify.client;

import com.ssavr.tutorials.sportify.dto.TokenDto;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class FollowArtistsUsersClient {

	public static boolean followArtists(List<String> ids, TokenDto tokenDto) {
		URI uri;
		try {
			uri = new URIBuilder().setScheme("https")
					.setHost("api.spotify.com").setPath("/v1/me/following")
					.addParameter("type", "artist").build();
			if (_log.isDebugEnabled()) {
				_log.debug("URI: " + uri.toString());
			}
		} catch (URISyntaxException e) {
			_log.error(e.getMessage());
			return false;
		}

		HttpPut httpPut = new HttpPut(uri);

		String authHeader = tokenDto.getTokenType() + " "
				+ tokenDto.getAccessToken();
		httpPut.addHeader(HttpHeaders.AUTHORIZATION, authHeader);
		httpPut.addHeader(HttpHeaders.CONTENT_TYPE,
				ContentType.APPLICATION_JSON.toString());

		StringEntity entity = new StringEntity(getJsonIds(ids),
				ContentType.APPLICATION_JSON);
		httpPut.setEntity(entity);

		ResponseHandler<Boolean> responseHandler = new ResponseHandler<Boolean>() {

			public Boolean handleResponse(HttpResponse response)
					throws ClientProtocolException, IOException {
				int status = response.getStatusLine().getStatusCode();
				if (status == 204) {
					if (_log.isDebugEnabled()) {
						_log.debug("SUCCESS");
					}
					return true;
				} else {
					_log.error("Error Code: " + status);
					_log.error("Error Message: "
							+ EntityUtils.toString(response.getEntity()));
					return false;
				}
			}
		};

		HttpClient httpClient = HttpClientBuilder.create().build();
		boolean success = false;
		try {
			success = httpClient.execute(httpPut, responseHandler);
		} catch (ClientProtocolException e) {
			_log.error(e.getMessage());
		} catch (IOException e) {
			_log.error(e.getMessage());
		}

		return success;
	}

	private static String getJsonIds(List<String> ids) {
		StringBuilder builder = new StringBuilder("{");
		builder.append("\"ids\":");
		builder.append("[");
		for (String id : ids) {
			builder.append("\"");
			builder.append(id);
			builder.append("\"");

		}
		builder.append("]");
		builder.append("}");

		if (_log.isDebugEnabled()) {
			_log.debug(builder.toString());
		}
		return builder.toString();
	}

	private final static Logger _log = Logger
			.getLogger(FollowArtistsUsersClient.class);
}
