package neo.wrappers;

import java.util.List;

import org.neo4j.graphdb.RelationshipType;

import course.Course;

public interface CourseNodeWrapper {
	
	enum courseEnum implements RelationshipType {HOLDS}

	public abstract Course getCourse();

	public abstract void addRace(
			RaceNode raceWrapper);

	public abstract List<RaceNode> getAllRaces();

	public abstract String getCourseName();

}

