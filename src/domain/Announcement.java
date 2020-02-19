package domain;

import java.util.Date;
import java.util.*;

public class Announcement {

	private Member author;
	private String text;
	private String title;
	private Date timestamp;
	
	
	
	
	public Announcement(Member author, String text, String title) {
		this.author = author;
		this.text = text;
		this.title = title;
		this.timestamp = new Date();
	}
	
	
	
	public String getText() {
		return this.text;
	}
	public void setText(String value) {
		this.text = value;
	}
	public String getTitle() {
		return this.title;
	}
	public void setTitle(String value) {
		this.title = value;
	}
	public Date getTimestamp() {
		return this.timestamp;
	}
	public void setTimestamp(Date value) {
		this.timestamp = value;
	}
}
