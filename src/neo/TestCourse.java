/**
 * 
 */
package neo;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.neo4j.util.GraphDatabaseLifecycle;

/**
 * @author Miriam Martin
 *
 */
public class TestCourse {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Course index: 0 ");
		GraphDatabaseService graphDb =   new EmbeddedGraphDatabase( "test117" ) ;
		System.out.println("Course index: 1 ");
		//GraphDatabaseLifecycle graph = new GraphDatabaseLifecycle(graphDb);
		System.out.println("Course index: 2 ");
		//GraphDatabaseService graphDb = graph.graphDb();
		
		Iterable<Node> nodes = graphDb.getAllNodes();
		
		for (Node n : nodes){
			try {
				//System.out.println("nodes " + nodes.iterator().next());
				if (n.hasProperty("courseName")) {
					System.out.println("Course: " + n.getProperty("courseName")
							+ " Id: " + n.getProperty("courseId"));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//graph.addIndexService(new LuceneIndexService(graphDb));
		//IndexService index = graph.indexService();
		System.out.println("Course index: 3 ");

	}

}
