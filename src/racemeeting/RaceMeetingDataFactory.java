package racemeeting;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.RefreshHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;

import fullday.FullDayPage;
import fullday.FullDayPageFactory;

/**
 * Take a full day page and return the MeetingData page
 * @author Miriam Martin
 */
 
public class RaceMeetingDataFactory {
	
/**
	private FullDayPage page;
	private String name;
	private String going;
	private List<HtmlPage> racePages;

	public RaceMeetingDataFactory(FullDayPage page) {
		this.page = page;
		name = page.querySelector("h1.cardHeadline").asText();
		racePages = new ArrayList<>();
		findGoing();
	}
	
	private void findGoing() {
		DomNode goingNode = page.querySelector("div.leftBlock");
		going = goingNode.asText();
		//System.out.println(going);
	}

	public List<HtmlPage> findRaces(){
		
		if (racePages.size() > 0){
			return racePages;
		}
		
		DomNodeList<DomNode>  raceTimes = page.querySelectorAll("td.raceTime");
		
		// convert raceTimes to anchors and add to map
		Map<String, HtmlAnchor> racesTimesAnchorMap = new HashMap<>();
		for (DomNode rt : raceTimes){
			HtmlElement rtHtmlElement = (HtmlElement)rt;
			HtmlAnchor rtAnchor = (HtmlAnchor) rtHtmlElement.getElementsByTagName("a").get(0);
			racesTimesAnchorMap.put(rtAnchor.getHrefAttribute(), rtAnchor);
			//System.out.println("10. --- " + rtAnchor.getHrefAttribute());
		}
		
		DomNodeList<DomNode> raceTitles = page.querySelectorAll("td.raceTitle");
		
		for (int i = 0; i < raceTimes.size(); i++){
			//System.out.println("1. --- " + raceTimes.get(i).asText());
			HtmlElement he = (HtmlElement) raceTitles.get(i);
			Iterable<HtmlElement> children = he.getElementsByTagName("a");
			for (HtmlElement titleLinks : children){
				HtmlAnchor titleAnchor = (HtmlAnchor)titleLinks;
				HtmlAnchor timeAnchor = racesTimesAnchorMap.get(titleAnchor.getHrefAttribute());
				
				
			}
			
			
					
					//raceTitles.get(i).getHtmlElementDescendants();
			for (HtmlElement child : children){
				//System.out.println("2. --- " + child.asXml() + "3. --" 	+ child.getTagName());
				
			}
		}
		return racePages;
		
		
		/**
		// get all td of class 'raceTitle'
		DomNodeList<DomNode> raceTables = page.querySelectorAll("table.raceHead");
		
		for (DomNode dm : raceTables){
			int i = 0;
			try {
				HtmlTable table = (HtmlTable)dm;
				System.out.println("1. time --" + table.getCellAt(0, 0).asXml());
				//System.out.println("2. name --" + table.getCellAt(0, 1).asText());
				
					HtmlTableCell cell = table.getCellAt(0, 0);
					Iterable<DomNode> cellChildren = cell.getChildren();
					
					
					for (DomNode dom : cellChildren){
						System.out.println("child--"+dom.asXml());
						if (i < 0) {
							try {
								i++;
								HtmlAnchor link = (HtmlAnchor) dom;
								HtmlPage newPage = link.click();
								 
									System.out.println(newPage.asText());
									
								
							} catch (Exception e) {
								// TODO Auto-generated catch block
								//System.out.println(dom.asXml());
								e.printStackTrace();
							}
						}
						
						
					}
					System.out.println("3, xxxx --" + cell.asText());
					//HtmlPage html = (HtmlPage)table.getCellAt(0, 1).click();
					
				
				
			} catch (Exception e){
				e.printStackTrace();
			}
		
	}

	public static void main(String[] args) throws Exception {

		// Create and initialize WebClient object
		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_3_6);
		webClient.setThrowExceptionOnScriptError(false);
		webClient.setRefreshHandler(new RefreshHandler() {
			public void handleRefresh(Page page, URL url, int arg) throws IOException {
				System.out.println("handleRefresh");
			}

		});

		// visit Yahoo Mail login page and get the Form object
		HtmlPage page = (HtmlPage) webClient.getPage("https://reg.racingpost.com/modal_dialog/login.sd?protoSecure=0");
		HtmlForm form = page.getFormByName("loginFrm");

		// Enter login and passwd
		form.getInputByName("in_un").setValueAttribute("ricardop");
		form.getInputByName("in_pw").setValueAttribute("randomly");

		// Click "Sign In" button/link
		//page = (HtmlPage) form.getInputByName("lb_login").click();
		HtmlButton submitButton = (HtmlButton)page.createElement("button");
		submitButton.setAttribute("type", "submit");
		form.appendChild(submitButton);
		page = submitButton.click();

		page = (HtmlPage) webClient.getPage("http://www.racingpost.com/horses2/cards/meeting_of_cards.sd?crs_id=1079&r_date=2012-02-29");  

		FullDayPageFactory.getFullDayPage(page);
		
		//RaceMeetingData md = new RaceMeetingData(page);
		
		//md.findRaces();
	}*/
}
