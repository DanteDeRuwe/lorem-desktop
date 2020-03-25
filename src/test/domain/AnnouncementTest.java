package test.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import main.domain.Announcement;
import main.domain.Member;
import main.domain.MemberType;

public class AnnouncementTest {

	private static Announcement exAnn;
	private static Member exAuthor;
	private static String exText, exTitle;
	

	@BeforeAll
	public static void beforeAll() {
		exAuthor = new Member("john.smith", "John", "Smith", MemberType.HEADADMIN);
		exText = "This is an announcement.";
		exTitle = "title";
	}
	
	@BeforeEach
	public void beforeEach() {
		exAnn = new Announcement(exAuthor, exText, exTitle);
	}
	
	//Author test
	@Test
	public void getAuthor_GetsCorrectAuthor() {
		assertEquals(exAuthor, exAnn.getAuthor());
	}
	
	//Text tests
	@Test
	public void getText_GetsCorrectText() {
		assertEquals(exText, exAnn.getText());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"title 1", "title 2", "title 3"})
	public void setText_SetsCorrectText(String value) {
		exAnn.setText(value);
		assertEquals(value, exAnn.getText());
	}
	
	//Title tests
	@Test
	public void getTitle_GetsCorrectTitle() {
		assertEquals(exTitle, exAnn.getTitle());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"title 1", "title 2", "title 3"})
	public void setTitle_SetsCorrectTitle(String value) {
		exAnn.setTitle(value);
		assertEquals(value, exAnn.getTitle());
	}

}
