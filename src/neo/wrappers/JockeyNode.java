/**
 * 
 */
package neo.wrappers;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.util.NodeWrapperImpl;

/**
 * @author Miriam Martin
 *
 */
public class JockeyNode extends NodeWrapperImpl {

	public JockeyNode(Node node) {
		super(node);
		// TODO Auto-generated constructor stub
	}
	
	enum JockeyRelationships implements RelationshipType{
		RODE
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public void addIndividualResult(IndividualResultNode irWrapper) {
		// TODO Auto-generated method stub
		getUnderlyingNode().createRelationshipTo(irWrapper.getUnderlyingNode(), JockeyRelationships.RODE);
	}

}
