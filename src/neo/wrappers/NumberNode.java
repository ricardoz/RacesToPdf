package neo.wrappers;

import org.neo4j.graphdb.Node;
import org.neo4j.util.NodeWrapperImpl;

public class NumberNode extends NodeWrapperImpl {

	private int value;

	public NumberNode(Node node) {
		super(node);
		this.value = (int)getUnderlyingNode().getProperty("numberValue");
		// TODO Auto-generated constructor stub
	}
	
	public int getValue(){
		return value;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
