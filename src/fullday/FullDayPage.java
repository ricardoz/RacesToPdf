package fullday;

import java.util.List;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public interface FullDayPage {

	List<HtmlPage> findMeetings();
	public boolean isResult();

}
