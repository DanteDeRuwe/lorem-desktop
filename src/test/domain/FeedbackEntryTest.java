package test.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import main.domain.FeedbackEntry;
import main.domain.Member;
import main.domain.MemberType;

public class FeedbackEntryTest {

	private static FeedbackEntry fbEntry;
	private static Member exampleAuthor;
	private static String exampleText, exampleTitle;

	@BeforeAll
	public static void beforeAll() {
		exampleAuthor = new Member("johnsmith", "John", "Smith", MemberType.USER);
		exampleText = "Lorem ipsum dolor sit amet";
		exampleTitle = "Lorem ipsum";
	}
	
	@BeforeEach
	public void beforeEach() {
		fbEntry = new FeedbackEntry(exampleAuthor, exampleTitle, exampleText);
	}

	//Title tests
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = {" ", "   ", "\n", "\t"})
	public void createFeedbackEntry_EmptyOrNullTitle_ThrowIllegalArgumentException(String title) {
		assertThrows(IllegalArgumentException.class, () -> new FeedbackEntry(exampleAuthor, title, exampleText));
	}
	
	@Test
	public void getTitle_GetsCorrectTitle() {
		assertEquals(exampleTitle, fbEntry.getTitle());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"title 1", "title 2", "title 3"})
	public void setTitle_CorrectTitle_SetsTitle(String value) {
		fbEntry.setTitle(value);
		assertEquals(value, fbEntry.getTitle());
	}

	//Text tests
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = {" ", "   ", "\n", "\t"})
	public void createFeedbackEntry_EmptyOrNullText_ThrowIllegalArgumentException(String text) {
		assertThrows(IllegalArgumentException.class, () -> new FeedbackEntry(exampleAuthor, exampleTitle, text));
	}
	
	@Test
	public void getText_GetsCorrectText() {
		assertEquals(exampleText, fbEntry.getText());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"text 1", "text 2", "text 3"})
	public void setText_CorrectText_SetsText(String value) {
		fbEntry.setText(value);
		assertEquals(value, fbEntry.getText());
	}
	
	//Author test
	@Test
	public void getAuthor_GetsCorrectAuthor() {
		assertEquals(exampleAuthor, fbEntry.getAuthor());
	}

}
