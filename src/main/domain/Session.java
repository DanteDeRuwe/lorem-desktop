package main.domain;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import main.services.Util;

public class Session {

	private final Member organizer;
	private Collection<Member> participants = new ArrayList<>();
	private Collection<MediaItem> media = new ArrayList<>();
	private Collection<FeedbackEntry> feedbackEntries = new ArrayList<>();
	private Collection<Announcement> announcements = new ArrayList<>();
	private Location location;
	private String title;
	private String speakerName;
	private LocalDateTime start;
	private LocalDateTime end;

	public Session(Member organizer, String title, String speakerName, LocalDateTime start, LocalDateTime end,
			Location location) {

		if (!meetsMinimumPeriodRequirement(start, end))
			throw new IllegalArgumentException("start and end do not meet minimum period requirement");

		this.organizer = organizer;
		setTitle(title);
		setSpeakerName(speakerName);
		setLocation(location);

		setStart(start);
		setEnd(end);

	}

	private boolean meetsMinimumPeriodRequirement(LocalDateTime start, LocalDateTime end) {
		Duration durationBetween = Duration.between(start, end);
		Duration minimumPeriod = Duration.ofMinutes(30);
		return durationBetween.compareTo(minimumPeriod) >= 0;
	}

	public void addParticipant(Member participant) {
		/*
		 * TODO: check maximum capacity of location
		 */
		participants.add(participant);
	}

	public void addMediaItem(MediaItem mediaItem) {
		media.add(mediaItem);
	}

	public void addAnnouncement(Announcement announcement) {
		announcements.add(announcement);
	}

	public void addFeedbackEntry(FeedbackEntry feedbackEntry) {
		feedbackEntries.add(feedbackEntry);
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

	public String getFullOrganizerName() {
		return organizer.getFirstName() + " " + organizer.getLastName();
	}

	public int getLocationCapacity() {
		return location.getCapacity();
	}

	@Override
	public String toString() {
		return "Sessie: " + title + "\n" + "Start: " + start + "\n" + "Einde: " + end + "\n" + "Organisator: "
				+ organizer.getFullName() + "\n" + "Locatie: " + location.getId() + "\n" + "Spreker: " + speakerName
				+ "\n";
	}

	/*
	 * properties for javafx
	 * 
	 */

	public StringProperty titleProperty() {
		return new SimpleStringProperty(title);
	}

	public StringProperty dateProperty() {

		if (Util.isSameDay(start, end))
			return new SimpleStringProperty(start.format(Util.DATEFORMATTER));
		else
			return new SimpleStringProperty(start.format(Util.DATEFORMATTER) + " - " + end.format(Util.DATEFORMATTER));
	}

	public StringProperty startProperty() {
		return new SimpleStringProperty(start.format(Util.TIMEFORMATTER));
	}

	public StringProperty endProperty() {
		return new SimpleStringProperty(end.format(Util.TIMEFORMATTER));
	}

	public StringProperty organizerProperty() {
		return new SimpleStringProperty(organizer.getFullName());
	}

	public StringProperty speakerProperty() {
		return new SimpleStringProperty(speakerName);
	}

	public StringProperty locationProperty() {
		return new SimpleStringProperty(location.getId());
	}
}