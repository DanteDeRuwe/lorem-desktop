package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.*;
import java.time.*;

public class Session {

	private Member organizer;
	private Collection<Member> participants;
	private Location location;
	private Collection<MediaItem> media;
	private Collection<FeedbackEntry> feedbackEntries;
	private Collection<Announcement> announcements;
	private String title;
	private String speakerName;
	private LocalDateTime start;
	private LocalDateTime end;

	public Session(Member organizer, String title, String speakerName, LocalDateTime start, LocalDateTime end,
			Location location) {
		this.organizer = organizer;
		setTitle(title);
		this.speakerName = speakerName;
		this.start = start;
		this.end = end;
		setLocation(location);

		// initialize collections
		media = new ArrayList<>();
		feedbackEntries = new ArrayList<>();
		announcements = new ArrayList<>();
		participants = new ArrayList<>();
	}

	public String getTitle() {
		return this.title;
	}

	public String getSpeakerName() {
		return this.speakerName;
	}

	public void setSpeakerName(String value) {
		this.speakerName = value;
	}

	public LocalDateTime getStart() {
		return this.start;
	}

	public void setStart(LocalDateTime value) {
		this.start = value;
	}

	public LocalDateTime getEnd() {
		return this.end;
	}

	public void setEnd(LocalDateTime value) {
		this.end = value;
	}

	public void setTitle(String value) {
		if (value == null || value.isEmpty())
			throw new IllegalArgumentException("title invalid");
		this.title = value;
	}

	public Location getLocation() {
		return this.location;
	}

	public void setLocation(Location value) {
		if (value == null)
			throw new IllegalArgumentException("location invalid");
		this.location = value;
	}

}
