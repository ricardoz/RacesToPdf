package neo.enums;

import org.neo4j.graphdb.Node;
import org.neo4j.util.NodeWrapperImpl;

public class RaceClassNode extends NodeWrapperImpl implements Comparable<RaceClassNode>{

	public RaceClassNode(Node n) {
		super(n);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public int compareTo(RaceClassNode rcn) {
		
		return  rcn.getClassScore() - getClassScore();
	}
	
	private int getClassScore(){
		int score = 0;
		
		String s = getUnderlyingNode().getProperty("handicapClass").toString().toLowerCase();
		
		if (s.startsWith("seller")) return 1;
		
		if (s.startsWith("claimer")) return 2;
		
		if (s.startsWith("maiden")) return 3;
		
		if (s.startsWith("class")) return 100 * Integer.parseInt(s.substring(s.length()-1));
		
		if (s.startsWith("group")) return 1000 * Integer.parseInt(s.substring(s.length()-1));
		
		return score;
		
	}

}
