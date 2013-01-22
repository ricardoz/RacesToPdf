/**
 * 
 */
package query;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

import examples.Chris;

/**
 * @author Miriam Martin
 *
 */
public class BasicResultsFilterTest {

	private HtmlPage page;
	private BasicResultsFilter brf;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		DistanceFilter edf = new EqualDistanceFilter(Integer.valueOf(8));
		brf = new BasicResultsFilter.Builder().distance(edf).build();
		Chris c = new Chris();
		page = c.getPageFromWebClient("http://www.racingpost.com/horses/result_home.sd?race_id=547667&r_date=2012-02-29&popup=yes");
	}

	@Test
	public void test() {
		assertTrue(brf.isSuitableResult(page));
	}

}
