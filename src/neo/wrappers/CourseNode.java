/**
 * 
 */
package neo.wrappers;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.util.NodeWrapperImpl;

import course.Course;

/**
 * @author Miriam Martin
 *
 */
public class CourseNode extends NodeWrapperImpl implements CourseNodeWrapper {
	
	private Course course;

	/**
	 * @param node
	 */
	public CourseNode(Node node) {
		super(node);
		this.course = new Course((String)node.getProperty("courseName"));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

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
		GraphDatabaseService graphDb  = getUnderlyingNode().getGraphDatabase();
		Transaction tx = graphDb.beginTx();
		try {
			
			getUnderlyingNode().createRelationshipTo(raceWrapper.getUnderlyingNode(), courseEnum.HOLDS);
			
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
		
		List<RaceNode> races = new ArrayList<>(150);
		
		Iterable<Relationship> relationships = getUnderlyingNode().getRelationships(courseEnum.HOLDS, Direction.OUTGOING);
		
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
		
		return course.getName();
	}

	
}
