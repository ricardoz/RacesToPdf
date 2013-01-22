package examples;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
import com.gargoylesoftware.htmlunit.javascript.NamedNodeMap;
import com.gargoylesoftware.htmlunit.javascript.host.Node;

import fullday.FullDayPage;
import fullday.FullDayPageFactory;

public class MeetingData {
	private FullDayPage page;
	private List<HtmlPage> meetingPages;

	public MeetingData(FullDayPage page){
			this.page = page;
			meetingPages = page.findMeetings();
			
			
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
		final HtmlPage loginPage = (HtmlPage) webClient.getPage("https://reg.racingpost.com/modal_dialog/login.sd?protoSecure=0");
		HtmlForm form = loginPage.getFormByName("loginFrm");

		// Enter login and passwd
		form.getInputByName("in_un").setValueAttribute("ricardop");
		form.getInputByName("in_pw").setValueAttribute("randomly");

		// Click "Sign In" button/link
		//page = (HtmlPage) form.getInputByName("lb_login").click();
		HtmlButton submitButton = (HtmlButton)loginPage.createElement("button");
		submitButton.setAttribute("type", "submit");
		form.appendChild(submitButton);
		submitButton.click();

		final HtmlPage raceDayPage = (HtmlPage) webClient.getPage("http://www.racingpost.com/horses2/cards/home.sd?r_date=2012-02-23");  
		
		FullDayPage fdp = FullDayPageFactory.getFullDayPage(raceDayPage);

		MeetingData md = new MeetingData(fdp);
		
		//md.findMeetings();
	}
}
