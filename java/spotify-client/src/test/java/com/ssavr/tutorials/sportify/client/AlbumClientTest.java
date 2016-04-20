package com.ssavr.tutorials.sportify.client;

import com.ssavr.tutorials.sportify.dto.AlbumDto;

import org.junit.Assert;
import org.junit.Test;

public class AlbumClientTest {

	@Test
	public void getAlbumInfo_Test() {
		String albumId = "40QPuvt8Ft79Bakb0Jc78f";
		AlbumDto albumDto = AlbumClient.getAlbumInfo(albumId);
		Assert.assertNotNull(albumDto);
		Assert.assertEquals(
				"Expected Khalifa but is was " + albumDto.getName(), "Khalifa",
				albumDto.getName());
		Assert.assertEquals(
				"Expected 13 but is was " + albumDto.getTotalTracks(), "13",
				albumDto.getTotalTracks());
		Assert.assertEquals(
				"Expected 13 but is was " + albumDto.getTotalTracks(), "13",
				albumDto.getTotalTracks());
		Assert.assertEquals(
				"Expected " + albumId + " but is was " + albumDto.getId(),
				albumId, albumDto.getId());
		Assert.assertEquals(
				"Expected 2016-02-05 but is was " + albumDto.getReleaseDate(),
				"2016-02-05", albumDto.getReleaseDate());
	}

	@Test
	public void getAlbumInfo_AlbumDoesNotExist_Test() {
		String albumId = "album-does-not-exist";
		AlbumDto albumDto = AlbumClient.getAlbumInfo(albumId);
		Assert.assertNull(albumDto);
	}
	
	@Test
	public void getAlbumInfo_AlbumIsNull_Test() {
		String albumId = null;
		AlbumDto albumDto = AlbumClient.getAlbumInfo(albumId);
		Assert.assertNull(albumDto);
	}
}
