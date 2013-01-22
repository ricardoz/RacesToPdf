/**
 * 
 */
package query;

/**
 * @author Miriam Martin
 *
 */
public class DistanceFilterFactory {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	

	public static DistanceFilter createDistanceFilter(DistanceFilterEnum dfEnum,
			String lower, String higher) {
		switch (dfEnum){
		case ALL: return null;
		case LESS_THAN_OR_EQUAL_TO: return new LessThanOrEqualToDistanceFilter(Integer.parseInt(lower));
		case MORE_THAN_OR_EQUAL_TO: return new MoreThanOrEqualToDistanceFilter(Integer.parseInt(lower));
		case EQUAL_TO: return new EqualDistanceFilter(Integer.parseInt(lower));
		case BETWEEN: return new IntervalDistanceFilter(
				new LessThanOrEqualToDistanceFilter(Integer.parseInt(lower)), 
				new MoreThanOrEqualToDistanceFilter(Integer.parseInt(higher)));
	}
		return null;
	}

}
