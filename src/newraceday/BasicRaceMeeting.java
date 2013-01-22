/**
 * 
 */
package newraceday;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.LocalDate;

import course.Course;
import race.Race;
import race.RaceId;
import racemeeting.RaceMeeting;

/**
 * @author Miriam Martin
 *
 */
public class BasicRaceMeeting implements RaceMeeting {

	private Course course;
	private LocalDate date;
	private Map<RaceId, Race> raceMap;

	public BasicRaceMeeting(Course course, LocalDate date) {
		this.course = course;
		this.date = date;
		this.raceMap = new HashMap<>();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public boolean equals(Object other){
		if (!(other instanceof RaceMeeting)) return false;
		
		RaceMeeting otherRaceMeeting = (RaceMeeting)other;
		return date.equals(otherRaceMeeting.getDate()) && course.equals(otherRaceMeeting.getCourse());
	}
	
	@Override
	public int hashCode(){
		int hash = 7;
		
		hash = hash*31 + course.hashCode();
		
		hash = hash*31 + date.hashCode();
		
		return hash;
	}

	@Override
	public LocalDate getDate() {
		// TODO Auto-generated method stub
		return date;
	}

	@Override
	public Course getCourse() {
		// TODO Auto-generated method stub
		return course;
	}

	@Override
	public Race getRace(RaceId raceId) {
		return raceMap.get(raceId);
	}

	@Override
	public void addRace(Race race) {
		raceMap.put(race.getRaceId(), race);
		
	}

	@Override
	public Collection<Race> getRaces() {
		
		return raceMap.values();
	}

}
