package pws2.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Announcement {

	private final Member author;
	private final LocalDateTime timestamp;
	private String text;
	private String title;

	public Announcement(Member author, String text, String title) {
		this.author = author;
		setText(text);
		setTitle(title);
		this.timestamp = LocalDateTime.now();
	}

	public Member getAuthor() {
		return this.author;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String value) {
		if (value == null || value.trim().isEmpty())
			throw new IllegalArgumentException("text null or empty");
		this.text = value;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String value) {
		this.title = value;
	}

	public LocalDateTime getTimestamp() {
		return this.timestamp;
	}

	@Override
	public String toString() {
		return String.format("%s%n%s%n%s%n%s", getTitle(), getAuthor().getLastName() + getAuthor().getFirstName(),
				getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), text);
	}

}
