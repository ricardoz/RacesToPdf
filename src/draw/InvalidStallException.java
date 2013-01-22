/**
 * 
 */
package draw;

/**
 * @author Miriam Martin
 *
 */
public class InvalidStallException extends Exception {

	public InvalidStallException(Integer number, Integer drawAsInteger) {
		System.out.println("Was expecting: " + number + " but got: " + drawAsInteger);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
