package main.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FeedbackEntry {

	private final Member author;
	private final LocalDateTime timestamp;
	private String title;
	private String text;

	public FeedbackEntry(Member author, String title, String text) {
		this.author = author;
		setTitle(title);
		setText(text);
		this.timestamp = LocalDateTime.now();
	}

	public Member getAuthor() {
		return this.author;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String value) {
		if (value == null || value.trim().isEmpty())
			throw new IllegalArgumentException("title null or empty");
		this.title = value;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String value) {
		if (value == null || value.trim().isEmpty())
			throw new IllegalArgumentException("text null or empty");
		this.text = value;
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
