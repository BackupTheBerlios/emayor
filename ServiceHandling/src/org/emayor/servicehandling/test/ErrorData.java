/*
 * $ Created on Aug 12, 2005 by tku $
 */
package org.emayor.servicehandling.test;

/**
 * @author <a href="mailto:Tomasz.Kusber@fokus.fraunhofer.de"> <font
 *         size="-1">Tomasz Kusber </font> </a> <font size="-1"> FHI FOKUS (C)
 *         </font>
 */
public class ErrorData {

	private String pageTitle = "FATAL ERROR";

	private String errorTitle = "An error has occured!";

	private boolean enableLink = false;
	
	private String linkLabel = "RESET";
	
	private String linkLocation = "";
	
	private String errorMessage = "Please stay cool and notify the webmaster! :-)";
	
	private boolean emailLinkEnabled = false;
	
	private String emailLink = "";

	/**
	 * @return Returns the emailLink.
	 */
	public String getEmailLink() {
		return emailLink;
	}
	/**
	 * @param emailLink The emailLink to set.
	 */
	public void setEmailLink(String emailLink) {
		this.emailLink = emailLink;
	}
	/**
	 * @return Returns the emailLinkEnabled.
	 */
	public boolean isEmailLinkEnabled() {
		return emailLinkEnabled;
	}
	/**
	 * @param emailLinkEnabled The emailLinkEnabled to set.
	 */
	public void setEmailLinkEnabled(boolean emailLinkEnabled) {
		this.emailLinkEnabled = emailLinkEnabled;
	}
	/**
	 * @return Returns the enableLink.
	 */
	public boolean isEnableLink() {
		return enableLink;
	}
	/**
	 * @param enableLink The enableLink to set.
	 */
	public void setEnableLink(boolean enableLink) {
		this.enableLink = enableLink;
	}
	/**
	 * @return Returns the errorMessage.
	 */
	public String getErrorMessage() {
		return errorMessage;
	}
	/**
	 * @param errorMessage The errorMessage to set.
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	/**
	 * @return Returns the errorTitle.
	 */
	public String getErrorTitle() {
		return errorTitle;
	}
	/**
	 * @param errorTitle The errorTitle to set.
	 */
	public void setErrorTitle(String errorTitle) {
		this.errorTitle = errorTitle;
	}
	/**
	 * @return Returns the linkLabel.
	 */
	public String getLinkLabel() {
		return linkLabel;
	}
	/**
	 * @param linkLabel The linkLabel to set.
	 */
	public void setLinkLabel(String linkLabel) {
		this.linkLabel = linkLabel;
	}
	/**
	 * @return Returns the linkLocation.
	 */
	public String getLinkLocation() {
		return linkLocation;
	}
	/**
	 * @param linkLocation The linkLocation to set.
	 */
	public void setLinkLocation(String linkLocation) {
		this.linkLocation = linkLocation;
	}
	/**
	 * @return Returns the pageTitle.
	 */
	public String getPageTitle() {
		return pageTitle;
	}
	/**
	 * @param pageTitle The pageTitle to set.
	 */
	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}
}