package main.domain;

public class Location {

	private String id;
	private int capacity;

	public Location(String id, int capacity) {
		this.id = id;
		this.capacity = capacity;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String value) {
		this.id = value;
	}

	public int getCapacity() {
		return this.capacity;
	}

	public void setCapacity(int value) {
		this.capacity = value;
	}
}
