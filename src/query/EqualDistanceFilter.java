/**
 * 
 */
package query;




/**
 * @author Miriam Martin
 *
 */
public class EqualDistanceFilter extends DistanceFilter {

	

	public EqualDistanceFilter(Integer integer) {
		super(integer);
	}

	/* (non-Javadoc)
	 * @see query.DistanceFilter#evaluate()
	 */
	@Override
	public boolean evaluate(Integer i) {
		
		
		return distance.equals(i);
	}

	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
