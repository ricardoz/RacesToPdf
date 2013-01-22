package racemeeting;

import java.util.Collection;

import org.joda.time.LocalDate;

import race.Race;
import race.RaceId;

import course.Course;

public interface RaceMeeting {

	public LocalDate getDate();

	public Course getCourse();

	public Race getRace(RaceId raceId);

	public void addRace(Race race);

	public Collection<Race> getRaces();

}
