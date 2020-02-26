package main.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import main.domain.Location;

public class DummyLocationProvider {

	private List<Location> locations = new ArrayList<>();

	public DummyLocationProvider() {
		seedLocations();
	}

	private void seedLocations() {

		String loc = "GESCHB0.022,GESCHB4.031,GESCHB2.011,GESCHB0.039,GESCHB2.035,GESCHB0.038,GESCHB4.018,GESCHB4.037,GESCHB3.012,GESCHB3.030,GESCHB0.025,GESCHB4.011,GESCHB2.031,GESCHB3.011,GESCHB3.036,GESCHB4.026,GESCHB3.027,GESCHB4.026,GESCHB0.033,GESCHB3.033";
		String[] locArr = loc.split(",");

		Stream.of(locArr).forEach(l -> {
			int randCapacity = ThreadLocalRandom.current().nextInt(0, 50) * 10;
			locations.add(new Location(l, randCapacity));
		});
	}

	public List<Location> getLocations() {
		return locations;
	}

	public Location getRandomLocation() {
		return Util.randomChoice(locations);
	}

}
