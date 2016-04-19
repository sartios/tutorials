package com.ssavr.tutorials.sportify.dto;

import com.ssavr.tutorials.sportify.utils.JSONUtil;

import java.util.ArrayList;
import java.util.List;

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
		tracks.addAll(JSONUtil.getList("tracks", "name", albumJson));
	}

	private void setReleaseDate(JSONObject albumJson) {
		this.releaseDate = JSONUtil.getString(albumJson, "release_date");
	}

	private void setName(JSONObject albumJson) {
		this.name = JSONUtil.getString(albumJson, "name");
	}

	private void setGenres(JSONObject albumJson) {

	}

	private void setArtists(JSONObject albumJson) {
		artists.addAll(JSONUtil.getList("artists", "name", albumJson));
	}

	private List<String> artists = new ArrayList<String>();
	private List<String> genres = new ArrayList<String>();
	private List<String> tracks = new ArrayList<String>();
	private String name;
	private String releaseDate;
}
