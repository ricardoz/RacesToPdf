package examples;

import java.io.IOException;
import java.net.URL;
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
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

public class Login {

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
	    
	    page = (HtmlPage) webClient.getPage("http://www.racingpost.com/horses/horse_home_popup.sd?horse_id=763453");  

	    //System.out.println(page.asXml());	 
	    
	    /** Get the horse's name */
	    System.out.println(page.getTitleText());	
	    
	    //or
	    System.out.println(page.querySelector("h1").asText());
	    
	    /** Get the horse's */
	    System.out.println(page.querySelector("tr.fl_F_S_W").asXml());
	    //DomNode domnode = page.querySelector("tr.fl_F_S_W");
	    
	    /** Get all races */
	    DomNodeList<DomNode> allRaces = page.querySelectorAll("tr.fl_F_S_W,tr.fl_F_W");
	    
	    for (DomNode dm : allRaces){
	    	Iterable<HtmlElement> domNodeElementChildren = dm.getHtmlElementDescendants();
		    
		    int i = 1;
		    
		    for (HtmlElement he : domNodeElementChildren){
		    	if (i== 12){
		    		System.out.println("that's a bingo:--" + he.getAttribute("title"));
		    	}
		    	//System.out.println(i + " as xml:--" + he.asXml()+"--");
		    	//System.out.println(i + " as txt:--" + he.asText() +"--\n\n\n");
		    	i++;
		    }
	    }
	    
	    
	    
	    
	}
	
	
}
