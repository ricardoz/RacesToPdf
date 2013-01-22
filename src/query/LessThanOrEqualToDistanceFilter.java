/**
 * 
 */
package query;

/**
 * @author Miriam Martin
 *
 */
public class LessThanOrEqualToDistanceFilter extends DistanceFilter {

	/**
	 * @param integer
	 */
	public LessThanOrEqualToDistanceFilter(Integer integer) {
		super(integer);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see query.DistanceFilter#evaluate(java.lang.Integer)
	 */
	@Override
	public boolean evaluate(Integer i) {
		System.out.println("ltdf.e-- i " + i + " d " + distance + " i<d " + (i <= distance) );
		return i <= distance;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
