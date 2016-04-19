package com.ssavr.tutorials.sportify.client;

import com.ssavr.tutorials.sportify.dto.TokenDto;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class UnfollowArtistsClient {

	public static boolean unfollowArtist(List<String> ids, TokenDto tokenDto) {

		URI uri;
		try {
			uri = new URIBuilder().setScheme("https")
					.setHost("api.spotify.com").setPath("/v1/me/following")
					.addParameter("type", "artist")
					.addParameter("ids", getJsonIds(ids)).build();
		} catch (URISyntaxException e) {
			_log.error(e.getMessage());
			return false;
		}

		HttpDelete httpDelete = new HttpDelete(uri);
		String authHeader = tokenDto.getTokenType() + " "
				+ tokenDto.getAccessToken();
		httpDelete.addHeader(HttpHeaders.AUTHORIZATION, authHeader);
		httpDelete.addHeader(HttpHeaders.CONTENT_TYPE,
				ContentType.APPLICATION_JSON.toString());

		ResponseHandler<Boolean> responseHandler = new ResponseHandler<Boolean>() {
			public Boolean handleResponse(HttpResponse response)
					throws ClientProtocolException, IOException {
				int status = response.getStatusLine().getStatusCode();
				if (status == HttpStatus.SC_NO_CONTENT) {
					return true;
				} else {
					_log.error("Error Code: " + status);
					_log.error("Error Message: "
							+ EntityUtils.toString(response.getEntity()));
					return false;
				}
			}
		};

		boolean success = false;
		HttpClient httpClient = HttpClientBuilder.create().build();
		try {
			success = httpClient.execute(httpDelete, responseHandler);
		} catch (ClientProtocolException e) {
			_log.error(e.getMessage());
		} catch (IOException e) {
			_log.error(e.getMessage());
		}
		return success;
	}

	private static String getJsonIds(List<String> ids) {
		StringBuilder builder = ids.size() > 0 ? new StringBuilder(ids.get(0))
				: new StringBuilder("");
		for (int i = 1; i < ids.size(); i++) {
			if (_log.isDebugEnabled()) {
				_log.debug("ID: " + ids.get(i));
			}
			builder.append(ids.get(i));
			builder.append(",");
		}
		return builder.toString();
	}

	private final static Logger _log = Logger
			.getLogger(UnfollowArtistsClient.class);
}
