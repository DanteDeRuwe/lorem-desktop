package pws2.domain;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class AnnouncementTest {

	static Member exampleAuthor;

	@BeforeAll
	static void beforeAll() {
		exampleAuthor = new Member("john.smith", "John", "Smith", MemberType.HEADADMIN);
	}

	@ParameterizedTest
	@NullAndEmptySource
	void createAnnouncement_EmptyOrNullTExt_ThrowsIllegalArgumentException(String text) {
		assertThrows(IllegalArgumentException.class, () -> new Announcement(exampleAuthor, text, "title"));
	}

}
