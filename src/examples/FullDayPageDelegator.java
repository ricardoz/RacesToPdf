package examples;

import java.io.Serializable;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public interface FullDayPageDelegator extends Page, Serializable, Cloneable, Document, Node {

	List<HtmlPage> findMeetings();

}
