package com.ssavr.tutorials.sportify;

import com.ssavr.tutorials.sportify.client.AlbumClient;
import com.ssavr.tutorials.sportify.client.FollowArtistsClient;
import com.ssavr.tutorials.sportify.client.NewReleasesClient;
import com.ssavr.tutorials.sportify.client.SearchClient;
import com.ssavr.tutorials.sportify.client.TokenClient;
import com.ssavr.tutorials.sportify.client.UnfollowArtistsClient;
import com.ssavr.tutorials.sportify.dto.AlbumDto;
import com.ssavr.tutorials.sportify.dto.NewReleasesDto;
import com.ssavr.tutorials.sportify.dto.SearchResultDto;
import com.ssavr.tutorials.sportify.dto.TokenDto;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * 
 * Spotify Client
 *
 */
public class SpotifyClient {

	public static void main(String[] args) throws UnsupportedEncodingException {

		//followArtist("Wiz Khalifa");
		unfollowArtist("Wiz Khalifa");

	}

	private static void unfollowArtist(String artistName) {
		TokenDto tokenDto = TokenClient.getScopedAccessToken();
		SearchResultDto searchResultDto = SearchClient
				.searchForArtists(artistName);
		List<String> ids = new ArrayList<String>();
		if (_log.isDebugEnabled()) {
			_log.debug("ID: " + searchResultDto.getId());
		}
		ids.add(searchResultDto.getId());
		UnfollowArtistsClient.unfollowArtist(ids, tokenDto);
	}

	private static void followArtist(String artistName) {
		TokenDto tokenDto = TokenClient.getScopedAccessToken();
		SearchResultDto searchResultDto = SearchClient
				.searchForArtists(artistName);
		List<String> ids = new ArrayList<String>();
		ids.add(searchResultDto.getId());
		FollowArtistsClient.followArtists(ids, tokenDto);
	}

	private static void printAlbumInfo(AlbumDto album) {
		if (album != null) {
			_log.debug("-----------------------------");
			_log.debug("Ablum Name: " + album.getName());
			_log.debug("Tracks count: " + album.getTotalTracks());
			_log.debug("Artists count: " + album.getArtists().size());
			_log.debug("Genres count: " + album.getGenres().size());
			_log.debug("Release Date: " + album.getReleaseDate());
		}
	}

	private static void printNewReleases() {
		TokenDto tokenDto = TokenClient.getAccessToken();

		NewReleasesDto newReleasesDto = NewReleasesClient.getNewReleases(
				tokenDto, "GR", 10, 0);
		List<AlbumDto> albums = newReleasesDto.getAlbums();
		for (AlbumDto albumDto : albums) {
			AlbumDto album = AlbumClient.getAlbumInfo(albumDto.getId());
			printAlbumInfo(album);
		}

	}

	private final static Logger _log = Logger.getLogger(SpotifyClient.class);
}
