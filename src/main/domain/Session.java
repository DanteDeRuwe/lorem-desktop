package main.domain;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import main.services.Util;

@Entity
@NamedQueries({
		@NamedQuery(name = "Session.findByTitle", query = "select s from Session s where s.title = :sessionTitle") })
public class Session {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
    @JoinColumn(name="member_id", nullable=false)
	private Member organizer;
	private String location, title, speakerName;
	private LocalDateTime startTime, endTime;
	private int capacity;

	private List<Member> participants = new ArrayList<>();
	private List<MediaItem> media = new ArrayList<>();
	private List<FeedbackEntry> feedbackEntries = new ArrayList<>();
	private List<Announcement> announcements = new ArrayList<>();

	public Session() {
	};

	public Session(Member organizer, String title, String speakerName, LocalDateTime start, LocalDateTime end,
			String location, int capacity) {

		this.organizer = null;
		if (!meetsMinimumPeriodRequirement(start, end))
			throw new IllegalArgumentException("start and end do not meet minimum period requirement");

		this.organizer = organizer;
		setTitle(title);
		setSpeakerName(speakerName);
		setLocation(location);

		setStart(start);
		setEnd(end);
		setCapacity(capacity);
	}

	private boolean meetsMinimumPeriodRequirement(LocalDateTime start, LocalDateTime end) {
		Duration durationBetween = Duration.between(start, end);
		Duration minimumPeriod = Duration.ofMinutes(30);
		return durationBetween.compareTo(minimumPeriod) >= 0;
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
		return this.startTime;
	}

	public void setStart(LocalDateTime value) {
		if (value == null)
			throw new IllegalArgumentException("start null");
		if (value.compareTo(LocalDateTime.now()) < 0)
			throw new IllegalArgumentException("start in past");
		if (value.compareTo(LocalDateTime.now().plusHours(24)) < 0)
			throw new IllegalArgumentException("start less than 1 day in future");
		this.startTime = value;
	}

	public LocalDateTime getEnd() {
		return this.endTime;
	}

	public void setEnd(LocalDateTime value) {
		if (value == null)
			throw new IllegalArgumentException("end null");
		if (value.compareTo(LocalDateTime.now()) < 0)
			throw new IllegalArgumentException("end in past");
		this.endTime = value;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String value) {
		if (value == null)
			throw new IllegalArgumentException("location null");
		this.location = value;
	}

	public String getFullOrganizerName() {
		return organizer.getFirstName() + " " + organizer.getLastName();
	}

	public int getCapacity() {
		return this.capacity;
	}

	public void setCapacity(int value) {
		this.capacity = value;
	}

	@Override
	public String toString() {
		return "Sessie: " + title + "\n" + "Start: " + startTime + "\n" + "Einde: " + endTime + "\n" + "Organisator: "
				+ organizer.getFullName() + "\n" + "Locatie: " + location + "\n" + "Spreker: " + speakerName + "\n";
	}

	/*
	 * properties for javafx
	 * 
	 */

	public StringProperty titleProperty() {
		return new SimpleStringProperty(title);
	}

	public StringProperty dateProperty() {

		if (Util.isSameDay(startTime, endTime))
			return new SimpleStringProperty(startTime.format(Util.DATEFORMATTER));
		else
			return new SimpleStringProperty(
					startTime.format(Util.DATEFORMATTER) + " - " + endTime.format(Util.DATEFORMATTER));
	}

	public StringProperty startProperty() {
		return new SimpleStringProperty(startTime.format(Util.TIMEFORMATTER));
	}

	public StringProperty endProperty() {
		return new SimpleStringProperty(endTime.format(Util.TIMEFORMATTER));
	}

	public StringProperty organizerProperty() {
		return new SimpleStringProperty(organizer.getFullName());
	}

	public StringProperty speakerProperty() {
		return new SimpleStringProperty(speakerName);
	}

	public StringProperty locationProperty() {
		return new SimpleStringProperty(location);
	}
}