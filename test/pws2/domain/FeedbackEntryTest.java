package pws2.domain;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class FeedbackEntryTest {

	static Member exampleAuthor;
	static String exampleText, exampleTitle;

	@BeforeAll
	static void beforeAll() {
		exampleAuthor = new Member("johnsmith", "John", "Smith", MemberType.USER);
		exampleText = "Lorem ipsum dolor sit amet";
		exampleTitle = "Lorem ipsum";
	}

	@ParameterizedTest
	@NullAndEmptySource
	void createFeedbackEntry_EmptyOrNullTitle_ThrowIllegalArgumentException(String title) {
		assertThrows(IllegalArgumentException.class, () -> new FeedbackEntry(exampleAuthor, title, exampleText));
	}

	@ParameterizedTest
	@NullAndEmptySource
	void createFeedbackEntry_EmptyOrNullText_ThrowIllegalArgumentException(String text) {
		assertThrows(IllegalArgumentException.class, () -> new FeedbackEntry(exampleAuthor, exampleTitle, text));
	}

}
