/**
 * 
 */
package fullday;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.joda.time.LocalDate;

import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.WebWindow;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @author Miriam Martin
 *
 */
public class FullDayMeetingPage extends HtmlPage implements FullDayPage{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5899929811807441082L;
	private List<HtmlPage> meetingPages;

	/**
	 * @param originatingUrl
	 * @param webResponse
	 * @param webWindow
	 * @param date 
	 */
	FullDayMeetingPage(URL originatingUrl, WebResponse webResponse,
			WebWindow webWindow, LocalDate date) {
		super(originatingUrl, webResponse, webWindow);
		// TODO Auto-generated constructor stub
	}

	public List<HtmlPage> findMeetings(){

		if (meetingPages.size() > 0)
			return meetingPages;

		/** Get all meetings */
		//System.out.println(" as xml:--" + page.asText()+"--");
		DomNodeList<DomNode> allMeetings = querySelectorAll("p");
		//System.out.println(allMeetings);

		for (DomNode dm : allMeetings){

			//find if correct class
			org.w3c.dom.Node node = dm.getAttributes().getNamedItem("class");

			if ( node != null && node.getNodeValue().equalsIgnoreCase("bull show")){
				Iterable<DomNode> anchor = dm.getDescendants();
				for(DomNode domNode : anchor){
					if (domNode instanceof HtmlAnchor){
						HtmlAnchor link = (HtmlAnchor) domNode;
						String address = link.getHrefAttribute();
						if (!address.endsWith("ww")){
							System.out.println(link.getHrefAttribute());
							try {
								HtmlPage newPage = link.click();
								meetingPages.add(newPage);
								System.out.println(asText());
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					}

				}
			}
		}
		return meetingPages;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

	}

	@Override
	public boolean isResult() {
		return false;
	}

}
