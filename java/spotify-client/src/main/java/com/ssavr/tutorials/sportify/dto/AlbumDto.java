package com.ssavr.tutorials.sportify.dto;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AlbumDto {

	public AlbumDto() {
	}

	public AlbumDto(JSONObject albumJson) {
		setArtists(albumJson);
		setGenres(albumJson);
		setName(albumJson);
		setReleaseDate(albumJson);
		setTracks(albumJson);
	}

	public List<String> getArtists() {
		return artists;
	}

	public List<String> getGenres() {
		return genres;
	}

	public String getName() {
		return name;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public List<String> getTracks() {
		return tracks;
	}

	private void setTracks(JSONObject albumJson) {
		tracks.addAll(getList("tracks", "name", albumJson));
	}

	private void setReleaseDate(JSONObject albumJson) {
		this.releaseDate = getString(albumJson, "release_date");
	}

	private void setName(JSONObject albumJson) {
		this.name = getString(albumJson, "name");
	}

	private void setGenres(JSONObject albumJson) {

	}

	private void setArtists(JSONObject albumJson) {
		artists.addAll(getList("artists", "name", albumJson));
	}

	private List<String> getList(String arrParam, String objParam,
			JSONObject albumJson) {
		List<String> tmpList = new ArrayList<String>();
		JSONArray jsonArray;
		try {
			jsonArray = albumJson.getJSONArray(arrParam);
			if (jsonArray != null) {
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					tmpList.add(getString(jsonObject, objParam));
				}
			}
		} catch (JSONException e) {
		}

		return tmpList;
	}

	private String getString(JSONObject albumJson, String key) {
		String value = "";
		try {
			value = albumJson.getString(key);
		} catch (JSONException e) {
		}
		return value;
	}

	private List<String> artists = new ArrayList<String>();
	private List<String> genres = new ArrayList<String>();
	private List<String> tracks = new ArrayList<String>();
	private String name;
	private String releaseDate;
}
