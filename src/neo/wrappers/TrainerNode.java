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
public class TrainerNode extends NodeWrapperImpl {

	public TrainerNode(Node trainerNode) {
		super(trainerNode);
	}
	
	enum TrainerRelationships implements RelationshipType{
		TRAINS
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public void addIndividualResult(IndividualResultNode irWrapper) {
		getUnderlyingNode().createRelationshipTo(irWrapper.getUnderlyingNode(), TrainerRelationships.TRAINS);
		
	}

}
