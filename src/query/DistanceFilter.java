/**
 * 
 */
package query;


/**
 * @author Miriam Martin
 *
 */
public abstract class DistanceFilter{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	final Integer distance;
	
	public DistanceFilter(Integer integer) {
		this.distance = integer;
	}

	public abstract boolean evaluate(Integer i);

	

}
