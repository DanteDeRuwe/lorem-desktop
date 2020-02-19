package domain;

import java.util.Date;
import java.util.*;

public class FeedbackEntry {

	private Member author;
	private String title;
	private String text;
	private Date timestamp;
	
	
	
	
	public FeedbackEntry(Member author, String title, String text) {
		this.author = author;
		this.title = title;
		this.text = text;
		this.timestamp = new Date();
	}
	
	
	public String getTitle() {
		return this.title;
	}
	public void setTitle(String value) {
		this.title = value;
	}
	public String getText() {
		return this.text;
	}
	public void setText(String value) {
		this.text = value;
	}
	public Date getTimestamp() {
		return this.timestamp;
	}
	public void setTimestamp(Date value) {
		this.timestamp = value;
	}
}
