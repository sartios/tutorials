package com.ssavr.tutorials.sportify.client;

import com.ssavr.tutorials.sportify.dto.AlbumDto;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class AlbumClient {

	public static AlbumDto getAlbumInfo(String albumId) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		URI uri;
		try {
			uri = new URIBuilder().setScheme("https")
					.setHost("api.spotify.com")
					.setPath("/v1/albums/" + albumId).build();
		} catch (URISyntaxException e1) {
			_log.error(e1.getMessage());
			return null;
		}
		HttpGet httpGet = new HttpGet(uri);

		ResponseHandler<AlbumDto> responseHandler = new ResponseHandler<AlbumDto>() {

			public AlbumDto handleResponse(HttpResponse response)
					throws ClientProtocolException, IOException {
				int status = response.getStatusLine().getStatusCode();
				AlbumDto album = null;
				if (status >= 200 && status < 300) {
					HttpEntity entity = response.getEntity();
					if (entity != null) {
						try {
							album = new AlbumDto(new JSONObject(
									EntityUtils.toString(entity)));
						} catch (ParseException e) {
							_log.error(e.getMessage());
						} catch (JSONException e) {
							_log.error(e.getMessage());
						}
					}
				}
				return album;
			}
		};

		AlbumDto album = null;
		try {
			album = httpClient.execute(httpGet, responseHandler);
		} catch (ClientProtocolException e) {
			_log.error(e.getMessage());
		} catch (IOException e) {
			_log.error(e.getMessage());
		}
		return album;
	}

	final static Logger _log = Logger.getLogger(AlbumClient.class);
}
