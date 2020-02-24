package controllers;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import domain.Member;
import domain.Session;
import domain.SessionCalendar;

public class SessionCalendarController {

	/**
	 * Gives an overview of all Sessions in current SessionCalendar
	 * 
	 * @return <code>List&lt;String[]&gt;</code> An overview with:
	 *         <ol>
	 *         <li>The organizer name</li>
	 *         <li>The session title</li>
	 *         <li>The start datetimestring</li>
	 *         <li>The end datetimestring</li>
	 *         <li>capacity</li>
	 *         </ol>
	 *         returns <code>null</code> if no sessions
	 */
	// TODO not show capacity, see Use case
	public List<String[]> getSessionOverview() {
		List<Session> sessions = currentSessionCalendar.getSessions();

		if (sessions.size() > 0) {
			return sessions.stream()
					.map(s -> new String[] { s.getFullOrganizerName(), s.getTitle(),
							s.getStart().format(DateTimeFormatter.ofPattern("hh:mm EEEE d MMMM yyyy")),
							s.getEnd().format(DateTimeFormatter.ofPattern("hh:mm EEEE d MMMM yyyy")),
							Integer.toString(s.getLocationCapacity()) })
					.collect(Collectors.toList());

		} else {
			return null;
		}
	}

	public ArrayList<String> showSummary() {
		throw new UnsupportedOperationException();
	}

	private SessionCalendar currentSessionCalendar;
	private Member loggedInMember;

}
