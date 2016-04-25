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
		setId(albumJson);
		setTotalTracks(albumJson);
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
	
	public String getId(){
		return id;
	}
	
	public String getTotalTracks(){
		return totalTracks;
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
	
	private void setId(JSONObject albumJson){
		this.id = JSONUtil.getString(albumJson, "id");
	}

	private void setArtists(JSONObject albumJson) {
		artists.addAll(JSONUtil.getList("artists", "name", albumJson));
	}
	
	private void setTotalTracks(JSONObject albumJson){
		String tracksJson = JSONUtil.getString(albumJson, "tracks");
		JSONObject tracksObject = JSONUtil.getJsonObject(tracksJson);
		this.totalTracks = JSONUtil.getString(tracksObject, "total");
	}

	private List<String> artists = new ArrayList<String>();
	private List<String> genres = new ArrayList<String>();
	private List<String> tracks = new ArrayList<String>();
	private String totalTracks;
	private String name;
	private String releaseDate;
	private String id;
}
