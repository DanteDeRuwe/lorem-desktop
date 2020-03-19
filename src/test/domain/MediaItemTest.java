package test.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import main.domain.MediaItem;

public class MediaItemTest {

	private static String exUrl;
	private static MediaItem exItem;

	@BeforeAll
	public static void beforeAll() {
		exUrl = "url van het item";
	}
	
	@BeforeEach
	public void beforeEach() {
		exItem = new MediaItem(exUrl);
	}
	
	@Test
	public void getUrl_GetsCorrectUrl() {
		assertEquals(exUrl, exItem.getUrl());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"url 1", "url 2", "url 3"})
	public void setUrl_SetsCorrectUrl(String value) {
		exItem.setUrl(value);
		assertEquals(value, exItem.getUrl());
	}
	
}
