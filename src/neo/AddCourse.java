/**
 * 
 */
package neo;

import java.util.HashMap;
import java.util.Map;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;

import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.neo4j.util.GraphDatabaseLifecycle;

/**
 * @author Miriam Martin
 *
 */
public class AddCourse {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Course index: 0 ");
		GraphDatabaseService graphDb =   new EmbeddedGraphDatabase( "test117" ) ;
		//graph.addLuceneIndexService();

		//GraphDatabaseLifecycle graph = new GraphDatabaseLifecycle(new EmbeddedGraphDatabase("test117"));
		System.out.println("Course index: 1 ");
		//GraphDatabaseService graphDb = graph.graphDb();
		System.out.println("Course index: 2 ");
		//graph.addIndexService(new LuceneIndexService(graphDb));
		//IndexService index = graph.indexService();
		System.out.println("Course index: 3 ");
		Index<Node> courseIndex = graphDb.index().forNodes( "course" );
		System.out.println("Course index: 4 ");
		Map<String, Integer> map = createMap();
	
		// START ADD COURSES TO MAP
		
		/**
		 * For each value in map add a new course with courseName and courseId
		 */
		
		for (String s : map.keySet()) {
			Transaction tx = graphDb.beginTx();
			try {
				int id = (int)map.get(s);
				Node courseNode = graphDb.createNode();
				courseNode.setProperty("courseName", s);
				courseNode.setProperty("courseId", id);

				courseIndex.add(courseNode, "courseId", id);

				//letterRelationship = graphDb.getReferenceNode().createRelationshipTo(letterNode, RelTypes.IS_LETTER );

				// END SNIPPET: addUsers
				//System.out.println( "Letter created: " + upper + " -- " + letterNode.getProperty("letter") );

				System.out.println("Course index: "
						+ id
						+ " --- "
						+ courseIndex.get("courseId", id).getSingle()
								.getProperty("courseName"));

				tx.success();
			} finally {
				tx.finish();
			}
		}
		
		// END ADD COURSES TO MAP

	}
	
	private static Map<String, Integer> createMap(){
		Map<String,Integer> map = new HashMap<>();
		String courseId = "32:Aintree, 2:Ascot, 3:Ayr, 4:Bangor-On-Dee,5:Bath,6:Beverley,7:Brighton,8:Carlisle,9:Cartmel,10:Catterick,11:Cheltenham,12:Chepstow,13:Chester,15:Doncaster,17:Epsom,14:Exeter,18:Fakenham,1212:Ffos Las,19:Folkestone,20:Fontwell,21:Goodwood,1083:Great Leighs (A.W),22:Hamilton,23:Haydock,24:Hereford,25:Hexham,26:Huntingdon,27:Kelso,28:Kempton (A.W),1079:Kempton (A.W),30:Leicester,31:Lingfield (A.W),393:Lingfield (A.W),34:Ludlow,35:Market Rasen,16:Musselburgh,36:Newbury,37:Newcastle,38:Newmarket,174:Newmarket (July),39:Newton Abbot,40:Nottingham,41:Perth,44:Plumpton,46:Pontefract,47:Redcar,49:Ripon,52:Salisbury,54:Sandown,57:Sedgefield,61:Lingfield (A.W),394:Southwell (A.W),67:Stratford,73:Taunton,80:Thirsk,83:Towcester,84:Uttoxeter,85:Warwick,87:Wetherby,90:Wincanton,93:Windsor,95:Wolverhampton (A.W),513:Wolverhampton (A.W),101:Worcester,104:Yarmouth,107:York";
		String[] array = courseId.split(",");
		
		for (int i = 0; i < array.length; i++){
			System.out.println("trying " + i + " g " + array[i]);
			String[] split = array[i].trim().split(":");
			System.out.println("split 0 " + split[0] + " split 1 " + split[1]);
			
			map.put(split[1], Integer.parseInt(split[0]));
			
		}
		
		return map;
	}

}
