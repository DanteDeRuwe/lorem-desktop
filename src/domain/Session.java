package domain;

import java.util.*;

public class Session {

	private Member organizer;
	private Collection<Member> participants;
	private Location location;
	private Collection<MediaItem> media;
	private Collection<FeedbackEntry> feedbackEntries;
	private Collection<Announcement> announcements;
	private String title;
	private String speakerName;
	private Date start;
	private Date end;

	
	
	public Session(Member organizer, String title, String speakerName, Date start, Date end, Location location) {
		this.organizer = organizer;
		this.title = title;
		this.speakerName = speakerName;
		this.start = start;
		this.end = end;
		this.location = location;
		
		//initialize collections
		media = new ArrayList<>();
		feedbackEntries = new ArrayList<>();
		announcements =  new ArrayList<>();
		participants =  new ArrayList<>();
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

	public Date getStart() {
		return this.start;
	}

	public void setStart(Date value) {
		this.start = value;
	}

	public Date getEnd() {
		return this.end;
	}

	public void setEnd(Date value) {
		this.end = value;
	}

}
