/**
 * 
 */
package fullday;

import java.net.URL;
import java.util.List;

import org.joda.time.LocalDate;

import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.WebWindow;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @author Miriam Martin
 *
 */
public class FullDayResultsPage extends HtmlPage implements FullDayPage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8516969755112266893L;

	/**
	 * @param originatingUrl
	 * @param webResponse
	 * @param webWindow
	 * @param date 
	 */
	public FullDayResultsPage(URL originatingUrl, WebResponse webResponse,
			WebWindow webWindow, LocalDate date) {
		super(originatingUrl, webResponse, webWindow);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<HtmlPage> findMeetings() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isResult() {
		// TODO Auto-generated method stub
		return true;
	}

}
