package main.domain;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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
	@JoinColumn(name = "member_id", nullable = false)
	private Member organizer;
	@ManyToOne
	@JoinColumn(name = "calendar_id", nullable = false)
	private SessionCalendar calendar;
	private String location, title, speakerName;

	@Lob
	@Column(length = 8000)
	private String description;
	private LocalDateTime startTime, endTime;
	private int capacity;

	@ManyToMany
	@JoinTable(name = "SESSION_REGISTREES", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "member_id"))
	private Set<Member> registrees;

	@ManyToMany
	@JoinTable(name = "SESSION_ATTENDEES", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "member_id"))
	private Set<Member> attendees;

	private Set<MediaItem> media = new HashSet<>();
	private Set<FeedbackEntry> feedbackEntries = new HashSet<>();

	@OneToMany(mappedBy = "session")
	private Set<Announcement> announcements = new HashSet<>();

	private SessionStatus sessionStatus;

	private String externalLink;

	public Session() {
	};

	public Session(Member organizer, String title, String description, String speakerName, LocalDateTime start,
			LocalDateTime end, String location, int capacity, String externalLink) {

		if (start == null) {
			throw new IllegalArgumentException("Start can't be null");
		} else if (end == null) {
			throw new IllegalArgumentException("End can't be null");
		}

		if (!meetsMinimumPeriodRequirement(start, end)) {
			throw new IllegalArgumentException("start and end do not meet minimum period requirement");
		} else {
			setStart(start);
			setEnd(end);
		}

		this.organizer = organizer;
		setDescription(description);
		setTitle(title);
		setSpeakerName(speakerName);
		setLocation(location);
		setCapacity(capacity);
		setRegistrees(new HashSet<Member>());
		setAttendees(new HashSet<Member>());
		setExternalLink(externalLink);
	}

	// session aanmaken zonder link mee te geven
	public Session(Member organizer, String title, String description, String speakerName, LocalDateTime start,
			LocalDateTime end, String location, int capacity) {
		this(organizer, title, description, speakerName, start, end, location, capacity, "");
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
		announcement.setSession(this);
		announcements.add(announcement);
	}
	
	public void removeAnnouncement(Announcement announcement) {
		announcements.remove(announcement);
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCalendar(SessionCalendar cal) {
		this.calendar = cal;
	}

	public Set<Announcement> getAnnouncements() {
		return announcements;
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

	public ObjectProperty<LocalDateTime> startProperty() {
		return new SimpleObjectProperty<LocalDateTime>(startTime);
	}

	public ObjectProperty<Duration> durationProperty() {
		Duration duration = Duration.between(startTime, endTime);
		return new SimpleObjectProperty<Duration>(duration);
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

	public StringProperty capacityProperty() {
		return new SimpleStringProperty(registrees.size() + "/" + capacity);
	}

	public SessionStatus getSessionStatus() {
		return sessionStatus;
	}

	public void setSessionStatus(SessionStatus sessionStatus) {
		this.sessionStatus = sessionStatus;
	}

	public Set<Member> getRegistrees() {
		return registrees;
	}

	public void setRegistrees(Set<Member> registrees) {
		this.registrees = registrees;
	}

	public Set<Member> getAttendees() {
		return attendees;
	}

	public void setAttendees(Set<Member> attendees) {
		this.attendees = attendees;
	}

	public String getExternalLink() {
		return externalLink;
	}

	public void setExternalLink(String externalLink) {
		if (externalLink == null || externalLink.isBlank()) {
			this.externalLink = "";
			return;
		}

		if (externalLink.toLowerCase().startsWith("http")) {
			this.externalLink = externalLink;
		} else {
			this.externalLink = "http://" + externalLink;
		}

	}

}