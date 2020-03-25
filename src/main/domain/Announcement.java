package main.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class Announcement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long announcement_id;

	private Member author;
	private LocalDateTime timestamp;

	@Lob
	@Column(length = 8000)
	private String text;
	private String title;

	@ManyToOne
	@JoinColumn(name = "session_id", nullable = false)
	private Session session;

	public Announcement() {
	}

	public Announcement(Member author, String text, String title) {
		this.author = author;
		setText(text);
		setTitle(title);
		this.timestamp = LocalDateTime.now();
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
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
