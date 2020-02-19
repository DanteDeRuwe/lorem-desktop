package domain;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

public class Session {

	private final Member organizer;
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
		setSpeakerName(speakerName);
		setLocation(location);

		setStart(start);
		setEnd(end);
		if (!meetsMinimumPeriodRequirement())
			throw new IllegalArgumentException("start and end do not meet minimum period requirement");

		// initialize collections
		media = new ArrayList<>();
		feedbackEntries = new ArrayList<>();
		announcements = new ArrayList<>();
		participants = new ArrayList<>();
	}

	private boolean meetsMinimumPeriodRequirement() {
		Duration durationBetween = Duration.between(getStart(), getEnd());
		Duration minimumPeriod = Duration.ofMinutes(30);
		return durationBetween.compareTo(minimumPeriod) >= 0;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String value) {
		if (value == null || value.trim().isEmpty())
			throw new IllegalArgumentException("title null or empty");
		this.title = value;
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
		if (value == null)
			throw new IllegalArgumentException("start null");
		if (value.compareTo(LocalDateTime.now()) < 0)
			throw new IllegalArgumentException("start in past");
		if (value.compareTo(LocalDateTime.now().plusHours(24)) < 0)
			throw new IllegalArgumentException("start less than 1 day in future");
		this.start = value;
	}

	public LocalDateTime getEnd() {
		return this.end;
	}

	public void setEnd(LocalDateTime value) {
		if (value == null)
			throw new IllegalArgumentException("end null");
		if (value.compareTo(LocalDateTime.now()) < 0)
			throw new IllegalArgumentException("end in past");
		this.end = value;
	}

	public Location getLocation() {
		return this.location;
	}

	public void setLocation(Location value) {
		if (value == null)
			throw new IllegalArgumentException("location null");
		this.location = value;
	}

}
