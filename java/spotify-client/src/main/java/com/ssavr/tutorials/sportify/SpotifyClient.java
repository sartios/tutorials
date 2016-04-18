package com.ssavr.tutorials.sportify;

import com.ssavr.tutorials.sportify.client.AlbumClient;
import com.ssavr.tutorials.sportify.dto.AlbumDto;

/**
 * Hello world!
 *
 */
public class SpotifyClient {
	public static void main(String[] args) {

		AlbumDto album = AlbumClient.getAlbumInfo("0sNOF9WDwhWunNAHPD3Baj");
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
}
