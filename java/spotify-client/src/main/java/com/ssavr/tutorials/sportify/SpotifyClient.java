package com.ssavr.tutorials.sportify;

import com.ssavr.tutorials.sportify.client.AlbumClient;
import com.ssavr.tutorials.sportify.client.NewReleasesClient;
import com.ssavr.tutorials.sportify.client.TokenClient;
import com.ssavr.tutorials.sportify.dto.AlbumDto;
import com.ssavr.tutorials.sportify.dto.NewReleasesDto;
import com.ssavr.tutorials.sportify.dto.TokenDto;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * 
 * Hello world!
 *
 */
public class SpotifyClient {

	public static void main(String[] args) throws UnsupportedEncodingException {

		TokenDto tokenDto = TokenClient.getAccessToken();

		NewReleasesDto newReleasesDto = NewReleasesClient.getNewReleases(
				tokenDto, "GR", 10, 0);
		List<AlbumDto> albums = newReleasesDto.getAlbums();
		for (AlbumDto albumDto : albums) {
			AlbumDto album = AlbumClient.getAlbumInfo(albumDto.getId());
			printAlbumInfo(album);
		}

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

	final static Logger _log = Logger.getLogger(SpotifyClient.class);
}
