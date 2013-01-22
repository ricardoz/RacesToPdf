/**
 * 
 */
package neo;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import neo.enums.SubRef;
import neo.wrappers.CourseNode;
import neo.wrappers.RaceNode;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexHits;
import org.neo4j.kernel.EmbeddedGraphDatabase;

import course.Course;

/**
 * @author Miriam Martin
 *
 */
public class TestRaces {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GraphDatabaseService graphDb =   new EmbeddedGraphDatabase( "test117" ) ;

		registerShutdownHook( graphDb );

		Index<Node> courseIndex = graphDb.index().forNodes("course");

		Set<String> map = Course.getCourseNames();

		int handicap = 0;
		int listed = 0;
		int group = 0;

		Node refNode = graphDb.getReferenceNode();
		Iterable<Relationship> rels2 = refNode.getRelationships(SubRef.RACE);
		Iterator<Relationship> iters = rels2.iterator();
		
		Node raceRefNode = iters.next().getOtherNode(refNode);
		
		for (String s : map){
			Course c = new Course(s);
			IndexHits<Node> nodeHits = courseIndex.get("courseId", c.getId().toString());
			Node n = nodeHits.getSingle();

			if (n == null){
				System.out.println (" mistake with course " + c + 
						"\n---------------------------------------");
			} else {
				CourseNode w = new CourseNode(n);

				List<RaceNode> raceList = w.getAllRaces();

				if (raceList.size() >0) {
					System.out
					.println(c
							+ " has "
							+ raceList.size()
							+ " races\n---------------------------------------------\n");
					//System.out.println ();
					//System.out.println (" node " + n);
					//System.out.println (" nodeWrapper " + w + " \n");
					/*
					for (RaceNode rn : raceList){
						if (rn.isGroup()) {
							group++;
							System.out.println(rn);
						}
					}
					*/
					Transaction tx = graphDb.beginTx();
					try {
						for (RaceNode rn : raceList){
							
								if (rn.getReferenceNode() == null){
									raceRefNode.createRelationshipTo(rn.getUnderlyingNode(), SubRef.RACE);
									System.out.println("adding ref node: " + rn);
								}
							
						}
						
						tx.success();
					} finally {
						// TODO Auto-generated catch block
						tx.finish();
					}

					//System.out.println("\n");
				}
			}
		}

		System.out.println("Found hcap race " + handicap);


	}

	private static void registerShutdownHook(final GraphDatabaseService graphDb) {
		// Registers a shutdown hook for the Neo4j instance so that it
		// shuts down nicely when the VM exits (even if you "Ctrl-C" the
		// running example before it's completed)
		Runtime.getRuntime().addShutdownHook( new Thread()
		{
			@Override
			public void run()
			{
				graphDb.shutdown();
			}
		} );
	}

}
