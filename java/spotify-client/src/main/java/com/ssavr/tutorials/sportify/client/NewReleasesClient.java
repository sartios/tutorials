package com.ssavr.tutorials.sportify.client;

import com.ssavr.tutorials.sportify.dto.NewReleasesDto;
import com.ssavr.tutorials.sportify.dto.TokenDto;
import com.ssavr.tutorials.sportify.utils.JSONUtil;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class NewReleasesClient {

	public static NewReleasesDto getNewReleases(TokenDto tokenDto,
			String country, long limit, long offset) {

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("country", country));
		nameValuePairs.add(new BasicNameValuePair("limit", String
				.valueOf(limit)));
		nameValuePairs.add(new BasicNameValuePair("offset", String
				.valueOf(offset)));

		URI uri;
		try {
			uri = new URIBuilder().setScheme("https")
					.setHost("api.spotify.com")
					.setPath("/v1/browse/new-releases")
					.addParameters(nameValuePairs).build();
		} catch (URISyntaxException e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e.getMessage());
			}
			return null;
		}

		HttpGet httpGet = new HttpGet(uri);
		String authHeader = tokenDto.getTokenType() + " "
				+ tokenDto.getAccessToken();
		httpGet.setHeader(HttpHeaders.AUTHORIZATION, authHeader);

		ResponseHandler<NewReleasesDto> responseHandler = new ResponseHandler<NewReleasesDto>() {

			public NewReleasesDto handleResponse(HttpResponse response)
					throws ClientProtocolException, IOException {
				NewReleasesDto newReleasesDto = null;
				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode == HttpStatus.SC_OK) {
					newReleasesDto = new NewReleasesDto(
							JSONUtil.getJsonObject(EntityUtils
									.toString(response.getEntity())));
					if (_log.isDebugEnabled()) {
						_log.debug("Albums size: "
								+ newReleasesDto.getAlbums().size());
					}
				} else {
					_log.error("Request failed, status code: " + statusCode);
				}
				return newReleasesDto;
			}
		};

		HttpClient client = HttpClientBuilder.create().build();
		NewReleasesDto newReleasesDto = null;
		try {
			newReleasesDto = client.execute(httpGet, responseHandler);
		} catch (ClientProtocolException e) {
			_log.error(e.getMessage());
		} catch (IOException e) {
			_log.error(e.getMessage());
		}
		return newReleasesDto;
	}

	final static Logger _log = Logger.getLogger(NewReleasesClient.class);
}
