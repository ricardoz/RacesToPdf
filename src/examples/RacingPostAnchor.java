package examples;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import net.sourceforge.htmlunit.corejs.javascript.Function;
import net.sourceforge.htmlunit.corejs.javascript.ScriptableObject;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.TypeInfo;
import org.w3c.dom.UserDataHandler;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.SgmlPage;
import com.gargoylesoftware.htmlunit.html.DomAttr;
import com.gargoylesoftware.htmlunit.html.DomChangeListener;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlAttributeChangeListener;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.javascript.host.Event;

public class RacingPostAnchor{

	private HtmlAnchor link;

	/**
	 * @param link
	 */
	public RacingPostAnchor(HtmlAnchor link) {
		super();
		this.link = link;
	}

	/**
	 * @param listener
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#addDomChangeListener(com.gargoylesoftware.htmlunit.html.DomChangeListener)
	 */
	public void addDomChangeListener(DomChangeListener listener) {
		link.addDomChangeListener(listener);
	}

	/**
	 * @param listener
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#addHtmlAttributeChangeListener(com.gargoylesoftware.htmlunit.html.HtmlAttributeChangeListener)
	 */
	public void addHtmlAttributeChangeListener(
			HtmlAttributeChangeListener listener) {
		link.addHtmlAttributeChangeListener(listener);
	}

	/**
	 * @param arg0
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#appendChild(org.w3c.dom.Node)
	 */
	public DomNode appendChild(Node arg0) {
		return link.appendChild(arg0);
	}

	/**
	 * @param arg0
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#appendChildIfNoneExists(java.lang.String)
	 */
	public final HtmlElement appendChildIfNoneExists(String arg0) {
		return link.appendChildIfNoneExists(arg0);
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#asText()
	 */
	public String asText() {
		return link.asText();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#asXml()
	 */
	public String asXml() {
		return link.asXml();
	}

	/**
	 * 
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#blur()
	 */
	public void blur() {
		link.blur();
	}

	/**
	 * @return
	 * @throws IOException
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#click()
	 */
	public <P extends Page> P click() throws IOException {
		return link.click();
	}

	/**
	 * @param shiftKey
	 * @param ctrlKey
	 * @param altKey
	 * @return
	 * @throws IOException
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#click(boolean, boolean, boolean)
	 */
	public <P extends Page> P click(boolean shiftKey, boolean ctrlKey,
			boolean altKey) throws IOException {
		return link.click(shiftKey, ctrlKey, altKey);
	}

	/**
	 * @param event
	 * @return
	 * @throws IOException
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#click(com.gargoylesoftware.htmlunit.javascript.host.Event)
	 */
	public <P extends Page> P click(Event event) throws IOException {
		return link.click(event);
	}

	/**
	 * @param deep
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomElement#cloneNode(boolean)
	 */
	public DomNode cloneNode(boolean deep) {
		return link.cloneNode(deep);
	}

	/**
	 * @param other
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#compareDocumentPosition(org.w3c.dom.Node)
	 */
	public short compareDocumentPosition(Node other) {
		return link.compareDocumentPosition(other);
	}

	/**
	 * @return
	 * @throws IOException
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#dblClick()
	 */
	public <P extends Page> P dblClick() throws IOException {
		return link.dblClick();
	}

	/**
	 * @param shiftKey
	 * @param ctrlKey
	 * @param altKey
	 * @return
	 * @throws IOException
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#dblClick(boolean, boolean, boolean)
	 */
	public <P extends Page> P dblClick(boolean shiftKey, boolean ctrlKey,
			boolean altKey) throws IOException {
		return link.dblClick(shiftKey, ctrlKey, altKey);
	}

	/**
	 * @param arg0
	 * @return
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object arg0) {
		return link.equals(arg0);
	}

	/**
	 * @param event
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#fireEvent(com.gargoylesoftware.htmlunit.javascript.host.Event)
	 */
	public ScriptResult fireEvent(Event event) {
		return link.fireEvent(event);
	}

	/**
	 * @param eventType
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#fireEvent(java.lang.String)
	 */
	public ScriptResult fireEvent(String eventType) {
		return link.fireEvent(eventType);
	}

	/**
	 * 
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#focus()
	 */
	public void focus() {
		link.focus();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlAnchor#getAccessKeyAttribute()
	 */
	public final String getAccessKeyAttribute() {
		return link.getAccessKeyAttribute();
	}

	/**
	 * @param attributeName
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomElement#getAttribute(java.lang.String)
	 */
	public String getAttribute(String attributeName) {
		return link.getAttribute(attributeName);
	}

	/**
	 * @param namespaceURI
	 * @param localName
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomElement#getAttributeNS(java.lang.String, java.lang.String)
	 */
	public final String getAttributeNS(String namespaceURI, String localName) {
		return link.getAttributeNS(namespaceURI, localName);
	}

	/**
	 * @param name
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomElement#getAttributeNode(java.lang.String)
	 */
	public DomAttr getAttributeNode(String name) {
		return link.getAttributeNode(name);
	}

	/**
	 * @param namespaceURI
	 * @param localName
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomElement#getAttributeNodeNS(java.lang.String, java.lang.String)
	 */
	public DomAttr getAttributeNodeNS(String namespaceURI, String localName) {
		return link.getAttributeNodeNS(namespaceURI, localName);
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomElement#getAttributes()
	 */
	public NamedNodeMap getAttributes() {
		return link.getAttributes();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomElement#getAttributesMap()
	 */
	public final Map<String, DomAttr> getAttributesMap() {
		return link.getAttributesMap();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#getBaseURI()
	 */
	public String getBaseURI() {
		return link.getBaseURI();
	}

	/**
	 * @param xpathExpr
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#getByXPath(java.lang.String)
	 */
	public List<?> getByXPath(String xpathExpr) {
		return link.getByXPath(xpathExpr);
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#getCanonicalXPath()
	 */
	public String getCanonicalXPath() {
		return link.getCanonicalXPath();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlAnchor#getCharsetAttribute()
	 */
	public final String getCharsetAttribute() {
		return link.getCharsetAttribute();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#getChildElements()
	 */
	public final Iterable<HtmlElement> getChildElements() {
		return link.getChildElements();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#getChildNodes()
	 */
	public DomNodeList<DomNode> getChildNodes() {
		return link.getChildNodes();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#getChildren()
	 */
	public final Iterable<DomNode> getChildren() {
		return link.getChildren();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlAnchor#getCoordsAttribute()
	 */
	public final String getCoordsAttribute() {
		return link.getCoordsAttribute();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#getDescendants()
	 */
	public final Iterable<DomNode> getDescendants() {
		return link.getDescendants();
	}

	/**
	 * @param id
	 * @return
	 * @throws ElementNotFoundException
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#getElementById(java.lang.String)
	 */
	public <E extends HtmlElement> E getElementById(String id)
			throws ElementNotFoundException {
		return link.getElementById(id);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#getElementsByAttribute(java.lang.String, java.lang.String, java.lang.String)
	 */
	public final <E extends HtmlElement> List<E> getElementsByAttribute(
			String arg0, String arg1, String arg2) {
		return link.getElementsByAttribute(arg0, arg1, arg2);
	}

	/**
	 * @param tagName
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomElement#getElementsByTagName(java.lang.String)
	 */
	public DomNodeList<HtmlElement> getElementsByTagName(String tagName) {
		return link.getElementsByTagName(tagName);
	}

	/**
	 * @param namespace
	 * @param localName
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomElement#getElementsByTagNameNS(java.lang.String, java.lang.String)
	 */
	public DomNodeList<HtmlElement> getElementsByTagNameNS(String namespace,
			String localName) {
		return link.getElementsByTagNameNS(namespace, localName);
	}

	/**
	 * @param arg0
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#getEnclosingElement(java.lang.String)
	 */
	public HtmlElement getEnclosingElement(String arg0) {
		return link.getEnclosingElement(arg0);
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#getEnclosingForm()
	 */
	public HtmlForm getEnclosingForm() {
		return link.getEnclosingForm();
	}

	/**
	 * @return
	 * @throws IllegalStateException
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#getEnclosingFormOrDie()
	 */
	public HtmlForm getEnclosingFormOrDie() throws IllegalStateException {
		return link.getEnclosingFormOrDie();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#getEndColumnNumber()
	 */
	public int getEndColumnNumber() {
		return link.getEndColumnNumber();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#getEndLineNumber()
	 */
	public int getEndLineNumber() {
		return link.getEndLineNumber();
	}

	/**
	 * @param feature
	 * @param version
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#getFeature(java.lang.String, java.lang.String)
	 */
	public Object getFeature(String feature, String version) {
		return link.getFeature(feature, version);
	}

	/**
	 * @param xpathExpr
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#getFirstByXPath(java.lang.String)
	 */
	public <X> X getFirstByXPath(String xpathExpr) {
		return link.getFirstByXPath(xpathExpr);
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#getFirstChild()
	 */
	public DomNode getFirstChild() {
		return link.getFirstChild();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlAnchor#getHrefAttribute()
	 */
	public final String getHrefAttribute() {
		return link.getHrefAttribute();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlAnchor#getHrefLangAttribute()
	 */
	public final String getHrefLangAttribute() {
		return link.getHrefLangAttribute();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#getHtmlElementDescendants()
	 */
	public final Iterable<HtmlElement> getHtmlElementDescendants() {
		return link.getHtmlElementDescendants();
	}

	/**
	 * @param arg0
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#getHtmlElementsByTagName(java.lang.String)
	 */
	public final <E extends HtmlElement> List<E> getHtmlElementsByTagName(
			String arg0) {
		return link.getHtmlElementsByTagName(arg0);
	}

	/**
	 * @param arg0
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#getHtmlElementsByTagNames(java.util.List)
	 */
	public final List<HtmlElement> getHtmlElementsByTagNames(List<String> arg0) {
		return link.getHtmlElementsByTagNames(arg0);
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#getId()
	 */
	public final String getId() {
		return link.getId();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#getIndex()
	 */
	public int getIndex() {
		return link.getIndex();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#getLangAttribute()
	 */
	public final String getLangAttribute() {
		return link.getLangAttribute();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#getLastChild()
	 */
	public DomNode getLastChild() {
		return link.getLastChild();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomNamespaceNode#getLocalName()
	 */
	public String getLocalName() {
		return link.getLocalName();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlAnchor#getNameAttribute()
	 */
	public final String getNameAttribute() {
		return link.getNameAttribute();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomNamespaceNode#getNamespaceURI()
	 */
	public String getNamespaceURI() {
		return link.getNamespaceURI();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#getNextSibling()
	 */
	public DomNode getNextSibling() {
		return link.getNextSibling();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#getNodeName()
	 */
	public String getNodeName() {
		return link.getNodeName();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomElement#getNodeType()
	 */
	public final short getNodeType() {
		return link.getNodeType();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#getNodeValue()
	 */
	public String getNodeValue() {
		return link.getNodeValue();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlAnchor#getOnBlurAttribute()
	 */
	public final String getOnBlurAttribute() {
		return link.getOnBlurAttribute();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#getOnClickAttribute()
	 */
	public final String getOnClickAttribute() {
		return link.getOnClickAttribute();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#getOnDblClickAttribute()
	 */
	public final String getOnDblClickAttribute() {
		return link.getOnDblClickAttribute();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlAnchor#getOnFocusAttribute()
	 */
	public final String getOnFocusAttribute() {
		return link.getOnFocusAttribute();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#getOnKeyDownAttribute()
	 */
	public final String getOnKeyDownAttribute() {
		return link.getOnKeyDownAttribute();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#getOnKeyPressAttribute()
	 */
	public final String getOnKeyPressAttribute() {
		return link.getOnKeyPressAttribute();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#getOnKeyUpAttribute()
	 */
	public final String getOnKeyUpAttribute() {
		return link.getOnKeyUpAttribute();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#getOnMouseDownAttribute()
	 */
	public final String getOnMouseDownAttribute() {
		return link.getOnMouseDownAttribute();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#getOnMouseMoveAttribute()
	 */
	public final String getOnMouseMoveAttribute() {
		return link.getOnMouseMoveAttribute();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#getOnMouseOutAttribute()
	 */
	public final String getOnMouseOutAttribute() {
		return link.getOnMouseOutAttribute();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#getOnMouseOverAttribute()
	 */
	public final String getOnMouseOverAttribute() {
		return link.getOnMouseOverAttribute();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#getOnMouseUpAttribute()
	 */
	public final String getOnMouseUpAttribute() {
		return link.getOnMouseUpAttribute();
	}

	/**
	 * @param elementName
	 * @param attributeName
	 * @param attributeValue
	 * @return
	 * @throws ElementNotFoundException
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#getOneHtmlElementByAttribute(java.lang.String, java.lang.String, java.lang.String)
	 */
	public final <E extends HtmlElement> E getOneHtmlElementByAttribute(
			String elementName, String attributeName, String attributeValue)
			throws ElementNotFoundException {
		return link.getOneHtmlElementByAttribute(elementName, attributeName,
				attributeValue);
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#getOwnerDocument()
	 */
	public Document getOwnerDocument() {
		return link.getOwnerDocument();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#getPage()
	 */
	public SgmlPage getPage() {
		return link.getPage();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#getParentNode()
	 */
	public DomNode getParentNode() {
		return link.getParentNode();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomNamespaceNode#getPrefix()
	 */
	public String getPrefix() {
		return link.getPrefix();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#getPreviousSibling()
	 */
	public DomNode getPreviousSibling() {
		return link.getPreviousSibling();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomNamespaceNode#getQualifiedName()
	 */
	public String getQualifiedName() {
		return link.getQualifiedName();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#getReadyState()
	 */
	public String getReadyState() {
		return link.getReadyState();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlAnchor#getRelAttribute()
	 */
	public final String getRelAttribute() {
		return link.getRelAttribute();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlAnchor#getRevAttribute()
	 */
	public final String getRevAttribute() {
		return link.getRevAttribute();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomElement#getSchemaTypeInfo()
	 */
	public TypeInfo getSchemaTypeInfo() {
		return link.getSchemaTypeInfo();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#getScriptObject()
	 */
	public ScriptableObject getScriptObject() {
		return link.getScriptObject();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlAnchor#getShapeAttribute()
	 */
	public final String getShapeAttribute() {
		return link.getShapeAttribute();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#getStartColumnNumber()
	 */
	public int getStartColumnNumber() {
		return link.getStartColumnNumber();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#getStartLineNumber()
	 */
	public int getStartLineNumber() {
		return link.getStartLineNumber();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#getTabIndex()
	 */
	public Short getTabIndex() {
		return link.getTabIndex();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlAnchor#getTabIndexAttribute()
	 */
	public final String getTabIndexAttribute() {
		return link.getTabIndexAttribute();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomElement#getTagName()
	 */
	public final String getTagName() {
		return link.getTagName();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlAnchor#getTargetAttribute()
	 */
	public final String getTargetAttribute() {
		return link.getTargetAttribute();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#getTextContent()
	 */
	public String getTextContent() {
		return link.getTextContent();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#getTextDirectionAttribute()
	 */
	public final String getTextDirectionAttribute() {
		return link.getTextDirectionAttribute();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlAnchor#getTypeAttribute()
	 */
	public final String getTypeAttribute() {
		return link.getTypeAttribute();
	}

	/**
	 * @param key
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#getUserData(java.lang.String)
	 */
	public Object getUserData(String key) {
		return link.getUserData(key);
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#getXmlLangAttribute()
	 */
	public final String getXmlLangAttribute() {
		return link.getXmlLangAttribute();
	}

	/**
	 * @param attributeName
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomElement#hasAttribute(java.lang.String)
	 */
	public boolean hasAttribute(String attributeName) {
		return link.hasAttribute(attributeName);
	}

	/**
	 * @param namespaceURI
	 * @param localName
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomElement#hasAttributeNS(java.lang.String, java.lang.String)
	 */
	public final boolean hasAttributeNS(String namespaceURI, String localName) {
		return link.hasAttributeNS(namespaceURI, localName);
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomElement#hasAttributes()
	 */
	public final boolean hasAttributes() {
		return link.hasAttributes();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#hasChildNodes()
	 */
	public boolean hasChildNodes() {
		return link.hasChildNodes();
	}

	/**
	 * @param eventName
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#hasEventHandlers(java.lang.String)
	 */
	public final boolean hasEventHandlers(String eventName) {
		return link.hasEventHandlers(eventName);
	}

	/**
	 * @param arg0
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#hasHtmlElementWithId(java.lang.String)
	 */
	public boolean hasHtmlElementWithId(String arg0) {
		return link.hasHtmlElementWithId(arg0);
	}

	/**
	 * @return
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return link.hashCode();
	}

	/**
	 * @param newNode
	 * @throws IllegalStateException
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#insertBefore(com.gargoylesoftware.htmlunit.html.DomNode)
	 */
	public void insertBefore(DomNode newNode) throws IllegalStateException {
		link.insertBefore(newNode);
	}

	/**
	 * @param newChild
	 * @param refChild
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#insertBefore(org.w3c.dom.Node, org.w3c.dom.Node)
	 */
	public Node insertBefore(Node newChild, Node refChild) {
		return link.insertBefore(newChild, refChild);
	}

	/**
	 * @param node
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#isAncestorOf(com.gargoylesoftware.htmlunit.html.DomNode)
	 */
	public boolean isAncestorOf(DomNode node) {
		return link.isAncestorOf(node);
	}

	/**
	 * @param arg0
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#isAncestorOfAny(com.gargoylesoftware.htmlunit.html.DomNode[])
	 */
	public boolean isAncestorOfAny(DomNode... arg0) {
		return link.isAncestorOfAny(arg0);
	}

	/**
	 * @param namespaceURI
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#isDefaultNamespace(java.lang.String)
	 */
	public boolean isDefaultNamespace(String namespaceURI) {
		return link.isDefaultNamespace(namespaceURI);
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#isDisplayed()
	 */
	public boolean isDisplayed() {
		return link.isDisplayed();
	}

	/**
	 * @param arg
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#isEqualNode(org.w3c.dom.Node)
	 */
	public boolean isEqualNode(Node arg) {
		return link.isEqualNode(arg);
	}

	/**
	 * @param other
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#isSameNode(org.w3c.dom.Node)
	 */
	public boolean isSameNode(Node other) {
		return link.isSameNode(other);
	}

	/**
	 * @param namespace
	 * @param featureName
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#isSupported(java.lang.String, java.lang.String)
	 */
	public boolean isSupported(String namespace, String featureName) {
		return link.isSupported(namespace, featureName);
	}

	/**
	 * @param prefix
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#lookupNamespaceURI(java.lang.String)
	 */
	public String lookupNamespaceURI(String prefix) {
		return link.lookupNamespaceURI(prefix);
	}

	/**
	 * @param namespaceURI
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#lookupPrefix(java.lang.String)
	 */
	public String lookupPrefix(String namespaceURI) {
		return link.lookupPrefix(namespaceURI);
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#mayBeDisplayed()
	 */
	public boolean mayBeDisplayed() {
		return link.mayBeDisplayed();
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#mouseDown()
	 */
	public Page mouseDown() {
		return link.mouseDown();
	}

	/**
	 * @param shiftKey
	 * @param ctrlKey
	 * @param altKey
	 * @param button
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#mouseDown(boolean, boolean, boolean, int)
	 */
	public Page mouseDown(boolean shiftKey, boolean ctrlKey, boolean altKey,
			int button) {
		return link.mouseDown(shiftKey, ctrlKey, altKey, button);
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#mouseMove()
	 */
	public Page mouseMove() {
		return link.mouseMove();
	}

	/**
	 * @param shiftKey
	 * @param ctrlKey
	 * @param altKey
	 * @param button
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#mouseMove(boolean, boolean, boolean, int)
	 */
	public Page mouseMove(boolean shiftKey, boolean ctrlKey, boolean altKey,
			int button) {
		return link.mouseMove(shiftKey, ctrlKey, altKey, button);
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#mouseOut()
	 */
	public Page mouseOut() {
		return link.mouseOut();
	}

	/**
	 * @param shiftKey
	 * @param ctrlKey
	 * @param altKey
	 * @param button
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#mouseOut(boolean, boolean, boolean, int)
	 */
	public Page mouseOut(boolean shiftKey, boolean ctrlKey, boolean altKey,
			int button) {
		return link.mouseOut(shiftKey, ctrlKey, altKey, button);
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#mouseOver()
	 */
	public Page mouseOver() {
		return link.mouseOver();
	}

	/**
	 * @param shiftKey
	 * @param ctrlKey
	 * @param altKey
	 * @param button
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#mouseOver(boolean, boolean, boolean, int)
	 */
	public Page mouseOver(boolean shiftKey, boolean ctrlKey, boolean altKey,
			int button) {
		return link.mouseOver(shiftKey, ctrlKey, altKey, button);
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#mouseUp()
	 */
	public Page mouseUp() {
		return link.mouseUp();
	}

	/**
	 * @param shiftKey
	 * @param ctrlKey
	 * @param altKey
	 * @param button
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#mouseUp(boolean, boolean, boolean, int)
	 */
	public Page mouseUp(boolean shiftKey, boolean ctrlKey, boolean altKey,
			int button) {
		return link.mouseUp(shiftKey, ctrlKey, altKey, button);
	}

	/**
	 * 
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#normalize()
	 */
	public void normalize() {
		link.normalize();
	}

	/**
	 * @return
	 * @throws MalformedURLException
	 * @see com.gargoylesoftware.htmlunit.html.HtmlAnchor#openLinkInNewWindow()
	 */
	public final Page openLinkInNewWindow() throws MalformedURLException {
		return link.openLinkInNewWindow();
	}

	/**
	 * @param selectors
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#querySelector(java.lang.String)
	 */
	public DomNode querySelector(String selectors) {
		return link.querySelector(selectors);
	}

	/**
	 * @param selectors
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#querySelectorAll(java.lang.String)
	 */
	public DomNodeList<DomNode> querySelectorAll(String selectors) {
		return link.querySelectorAll(selectors);
	}

	/**
	 * 
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#remove()
	 */
	public void remove() {
		link.remove();
	}

	/**
	 * 
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#removeAllChildren()
	 */
	public void removeAllChildren() {
		link.removeAllChildren();
	}

	/**
	 * @param arg0
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#removeAttribute(java.lang.String)
	 */
	public final void removeAttribute(String arg0) {
		link.removeAttribute(arg0);
	}

	/**
	 * @param namespaceURI
	 * @param localName
	 * @see com.gargoylesoftware.htmlunit.html.DomElement#removeAttributeNS(java.lang.String, java.lang.String)
	 */
	public final void removeAttributeNS(String namespaceURI, String localName) {
		link.removeAttributeNS(namespaceURI, localName);
	}

	/**
	 * @param attribute
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomElement#removeAttributeNode(org.w3c.dom.Attr)
	 */
	public final Attr removeAttributeNode(Attr attribute) {
		return link.removeAttributeNode(attribute);
	}

	/**
	 * @param child
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#removeChild(org.w3c.dom.Node)
	 */
	public Node removeChild(Node child) {
		return link.removeChild(child);
	}

	/**
	 * @param tagName
	 * @param i
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#removeChild(java.lang.String, int)
	 */
	public final void removeChild(String tagName, int i) {
		link.removeChild(tagName, i);
	}

	/**
	 * @param listener
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#removeDomChangeListener(com.gargoylesoftware.htmlunit.html.DomChangeListener)
	 */
	public void removeDomChangeListener(DomChangeListener listener) {
		link.removeDomChangeListener(listener);
	}

	/**
	 * @param eventName
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#removeEventHandler(java.lang.String)
	 */
	public final void removeEventHandler(String eventName) {
		link.removeEventHandler(eventName);
	}

	/**
	 * @param listener
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#removeHtmlAttributeChangeListener(com.gargoylesoftware.htmlunit.html.HtmlAttributeChangeListener)
	 */
	public void removeHtmlAttributeChangeListener(
			HtmlAttributeChangeListener listener) {
		link.removeHtmlAttributeChangeListener(listener);
	}

	/**
	 * @param newNode
	 * @throws IllegalStateException
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#replace(com.gargoylesoftware.htmlunit.html.DomNode)
	 */
	public void replace(DomNode newNode) throws IllegalStateException {
		link.replace(newNode);
	}

	/**
	 * @param newChild
	 * @param oldChild
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#replaceChild(org.w3c.dom.Node, org.w3c.dom.Node)
	 */
	public Node replaceChild(Node newChild, Node oldChild) {
		return link.replaceChild(newChild, oldChild);
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#rightClick()
	 */
	public Page rightClick() {
		return link.rightClick();
	}

	/**
	 * @param shiftKey
	 * @param ctrlKey
	 * @param altKey
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#rightClick(boolean, boolean, boolean)
	 */
	public Page rightClick(boolean shiftKey, boolean ctrlKey, boolean altKey) {
		return link.rightClick(shiftKey, ctrlKey, altKey);
	}

	/**
	 * @param attributeName
	 * @param attributeValue
	 * @see com.gargoylesoftware.htmlunit.html.DomElement#setAttribute(java.lang.String, java.lang.String)
	 */
	public final void setAttribute(String attributeName, String attributeValue) {
		link.setAttribute(attributeName, attributeValue);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#setAttributeNS(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void setAttributeNS(String arg0, String arg1, String arg2) {
		link.setAttributeNS(arg0, arg1, arg2);
	}

	/**
	 * @param attribute
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomElement#setAttributeNode(org.w3c.dom.Attr)
	 */
	public Attr setAttributeNode(Attr attribute) {
		return link.setAttributeNode(attribute);
	}

	/**
	 * @param attribute
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomElement#setAttributeNodeNS(org.w3c.dom.Attr)
	 */
	public Attr setAttributeNodeNS(Attr attribute) {
		return link.setAttributeNodeNS(attribute);
	}

	/**
	 * @param eventName
	 * @param eventHandler
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#setEventHandler(java.lang.String, net.sourceforge.htmlunit.corejs.javascript.Function)
	 */
	public final void setEventHandler(String eventName, Function eventHandler) {
		link.setEventHandler(eventName, eventHandler);
	}

	/**
	 * @param eventName
	 * @param jsSnippet
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#setEventHandler(java.lang.String, java.lang.String)
	 */
	public final void setEventHandler(String eventName, String jsSnippet) {
		link.setEventHandler(eventName, jsSnippet);
	}

	/**
	 * @param newId
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#setId(java.lang.String)
	 */
	public final void setId(String newId) {
		link.setId(newId);
	}

	/**
	 * @param name
	 * @param isId
	 * @see com.gargoylesoftware.htmlunit.html.DomElement#setIdAttribute(java.lang.String, boolean)
	 */
	public void setIdAttribute(String name, boolean isId) {
		link.setIdAttribute(name, isId);
	}

	/**
	 * @param namespaceURI
	 * @param localName
	 * @param isId
	 * @see com.gargoylesoftware.htmlunit.html.DomElement#setIdAttributeNS(java.lang.String, java.lang.String, boolean)
	 */
	public void setIdAttributeNS(String namespaceURI, String localName,
			boolean isId) {
		link.setIdAttributeNS(namespaceURI, localName, isId);
	}

	/**
	 * @param idAttr
	 * @param isId
	 * @see com.gargoylesoftware.htmlunit.html.DomElement#setIdAttributeNode(org.w3c.dom.Attr, boolean)
	 */
	public final void setIdAttributeNode(Attr idAttr, boolean isId) {
		link.setIdAttributeNode(idAttr, isId);
	}

	/**
	 * @param value
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#setNodeValue(java.lang.String)
	 */
	public void setNodeValue(String value) {
		link.setNodeValue(value);
	}

	/**
	 * @param prefix
	 * @see com.gargoylesoftware.htmlunit.html.DomNamespaceNode#setPrefix(java.lang.String)
	 */
	public void setPrefix(String prefix) {
		link.setPrefix(prefix);
	}

	/**
	 * @param state
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#setReadyState(java.lang.String)
	 */
	public void setReadyState(String state) {
		link.setReadyState(state);
	}

	/**
	 * @param scriptObject
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#setScriptObject(net.sourceforge.htmlunit.corejs.javascript.ScriptableObject)
	 */
	public void setScriptObject(ScriptableObject scriptObject) {
		link.setScriptObject(scriptObject);
	}

	/**
	 * @param textContent
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#setTextContent(java.lang.String)
	 */
	public void setTextContent(String textContent) {
		link.setTextContent(textContent);
	}

	/**
	 * @param key
	 * @param data
	 * @param handler
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.DomNode#setUserData(java.lang.String, java.lang.Object, org.w3c.dom.UserDataHandler)
	 */
	public Object setUserData(String key, Object data, UserDataHandler handler) {
		return link.setUserData(key, data, handler);
	}

	/**
	 * @return
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#toString()
	 */
	public String toString() {
		return link.toString();
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @return
	 * @throws IOException
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#type(char, boolean, boolean, boolean)
	 */
	public Page type(char arg0, boolean arg1, boolean arg2, boolean arg3)
			throws IOException {
		return link.type(arg0, arg1, arg2, arg3);
	}

	/**
	 * @param c
	 * @return
	 * @throws IOException
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#type(char)
	 */
	public Page type(char c) throws IOException {
		return link.type(c);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @throws IOException
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#type(java.lang.String, boolean, boolean, boolean)
	 */
	public void type(String arg0, boolean arg1, boolean arg2, boolean arg3)
			throws IOException {
		link.type(arg0, arg1, arg2, arg3);
	}

	/**
	 * @param arg0
	 * @throws IOException
	 * @see com.gargoylesoftware.htmlunit.html.HtmlElement#type(java.lang.String)
	 */
	public void type(String arg0) throws IOException {
		link.type(arg0);
	}
	
	
}
