package com.ssavr.tutorials.sportify.dto;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NewReleasesDto {

	public NewReleasesDto() {
	}

	public NewReleasesDto(JSONObject newReleasesJson) {
		setAlbums(newReleasesJson);
	}

	private void setAlbums(JSONObject newReleasesJson) {
		try {
			JSONObject albumsJson = newReleasesJson.getJSONObject("albums");
			JSONArray itemsJson = albumsJson.getJSONArray("items");
			for (int i = 0; i < itemsJson.length(); i++) {
				JSONObject albumJson = itemsJson.getJSONObject(i);
				AlbumDto albumDto = new AlbumDto(albumJson);
				albums.add(albumDto);
			}
		} catch (JSONException e) {
		}
	}

	public List<AlbumDto> getAlbums() {
		return albums;
	}

	private List<AlbumDto> albums = new ArrayList<AlbumDto>();
}
