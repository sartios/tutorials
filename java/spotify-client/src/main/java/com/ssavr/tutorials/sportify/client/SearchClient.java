package com.ssavr.tutorials.sportify.client;

import com.ssavr.tutorials.sportify.dto.SearchResultDto;
import com.ssavr.tutorials.sportify.utils.JSONUtil;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class SearchClient {

	public static SearchResultDto searchForArtists(String artistName) {
		URI uri;
		try {
			uri = new URIBuilder().setScheme("https")
					.setHost("api.spotify.com").setPath("/v1/search")
					.addParameter("q", artistName)
					.addParameter("type", "artist").build();
		} catch (URISyntaxException e) {
			_log.error(e.getMessage());
			return null;
		}

		HttpGet httpGet = new HttpGet(uri);

		if (_log.isDebugEnabled()) {
			_log.debug("URI: " + uri.toString());
		}

		ResponseHandler<SearchResultDto> responseHandler = new ResponseHandler<SearchResultDto>() {

			public SearchResultDto handleResponse(HttpResponse response)
					throws ClientProtocolException, IOException {
				SearchResultDto resultDto = null;
				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode == HttpStatus.SC_OK) {
					JSONObject jsonObject = JSONUtil.getJsonObject(EntityUtils
							.toString(response.getEntity()));
					JSONObject artists = JSONUtil.getJsonObject(jsonObject,
							"artists");
					JSONArray jsonArray = JSONUtil.getJsonArray(artists,
							"items");
					JSONObject resultObject = JSONUtil.getJsonObject(jsonArray,
							0);
					resultDto = new SearchResultDto(resultObject);
				}
				return resultDto;
			}
		};

		SearchResultDto resultDto = null;
		HttpClient httpClient = HttpClientBuilder.create().build();
		try {
			resultDto = httpClient.execute(httpGet, responseHandler);
		} catch (ClientProtocolException e) {
			_log.error(e.getMessage());
		} catch (IOException e) {
			_log.error(e.getMessage());
		}

		return resultDto;
	}

	private final static Logger _log = Logger.getLogger(SearchClient.class);
}
