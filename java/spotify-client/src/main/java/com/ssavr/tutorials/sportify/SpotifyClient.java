package com.ssavr.tutorials.sportify;

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
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Hello world!
 *
 */
public class SpotifyClient {
	public static void main(String[] args) {

		AlbumDto album = getAlbumInfo("0sNOF9WDwhWunNAHPD3Baj");
		if (album != null) {
			System.out
					.println("-----------------------------------------------");
			System.out.println("Ablum Name: " + album.getName());
			System.out.println("Tracks count: " + album.getTracks().size());
			System.out.println("Artists count: " + album.getArtists().size());
			System.out.println("Genres count: " + album.getGenres().size());
			System.out.println("Release Date: " + album.getReleaseDate());
		}

	}

	private static AlbumDto getAlbumInfo(String albumId) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		URI uri;
		try {
			uri = new URIBuilder().setScheme("https")
					.setHost("api.spotify.com")
					.setPath("/v1/albums/" + albumId).build();
		} catch (URISyntaxException e1) {
			System.err.println(e1.getMessage());
			return null;
		}
		HttpGet httpGet = new HttpGet(uri);

		System.out.println("Executing request: " + httpGet.getRequestLine());

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
							System.err.println(e.getMessage());
						} catch (JSONException e) {
							System.err.println(e.getMessage());
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
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		return album;
	}
}
