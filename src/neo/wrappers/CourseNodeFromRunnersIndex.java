/**
 * 
 */
package neo.wrappers;

import java.util.ArrayList;
import java.util.List;

import newraceday.FromRunnersIndex;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;

import course.Course;

/**
 * @author Miriam Martin
 *
 */
public class CourseNodeFromRunnersIndex implements CourseNodeWrapper {

	private Node node;
	private Course course;

	/**
	 * 
	 */
	public CourseNodeFromRunnersIndex(Node node) {
		this.node = node;
		this.course = new Course((String)node.getProperty("courseName"));
	}

	/* (non-Javadoc)
	 * @see neo.wrappers.CourseNodeWrapper#getCourse()
	 */
	@Override
	public Course getCourse() {	
		return course;
	}

	/* (non-Javadoc)
	 * @see neo.wrappers.CourseNodeWrapper#addRace(org.neo4j.graphdb.GraphDatabaseService, neo.wrappers.RaceNode)
	 */
	@Override
	public void addRace(RaceNode raceWrapper) {
		GraphDatabaseService graphDb  = node.getGraphDatabase();
		Transaction tx = graphDb.beginTx();
		try {
			
			node.createRelationshipTo(raceWrapper.getUnderlyingNode(), courseEnum.HOLDS);
			
			//create relationship to subreference node
			Node courseSubReferenceNode = graphDb.getReferenceNode()
					.getRelationships(Direction.OUTGOING, FromRunnersIndex.FooRels.COURSE).iterator().next().getEndNode();
			
			courseSubReferenceNode.createRelationshipTo(node, courseEnum.HOLDS);
			
			//System.out.println("added: " + raceWrapper + " to " + course);

			tx.success();
		} catch (Exception e){
			//System.out.println("failed: " + raceWrapper + "\nto " + course + "\n");
			e.printStackTrace();
		} finally {
			tx.finish();
		}

	}

	/* (non-Javadoc)
	 * @see neo.wrappers.CourseNodeWrapper#getAllRaces(org.neo4j.graphdb.GraphDatabaseService)
	 */
	@Override
	public List<RaceNode> getAllRaces() {
		
		List<RaceNode> races = new ArrayList<>();
		
		Iterable<Relationship> relationships = node.getRelationships(courseEnum.HOLDS, Direction.OUTGOING);
		
		for (Relationship r : relationships){
			races.add(new RaceNode(r.getEndNode()));
		}
		
		return races;
	}

	/* (non-Javadoc)
	 * @see neo.wrappers.CourseNodeWrapper#getCourseName()
	 */
	@Override
	public String getCourseName() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
