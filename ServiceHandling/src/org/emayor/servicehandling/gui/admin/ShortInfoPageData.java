/*
 * Created on Jun 6, 2005
 */
package org.emayor.servicehandling.gui.admin;

import java.io.Serializable;

/**
 * @author tku
 */
public class ShortInfoPageData implements Serializable {
    public static final String ATT_NAME = "SHORT_INFO_PAGE_DATA";
    
    private String sleepTime;
    private String redirectionUrl;
    private String pageTitle;
    private String sectionTitle;
    private String redirectionText;
    private String redirectionCancelAction;
    private String redirectionCancelButtonTitle;
    private String redirectionAction;
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
    /**
     * @return Returns the redirectionAction.
     */
    public String getRedirectionAction() {
        return redirectionAction;
    }
    /**
     * @param redirectionAction The redirectionAction to set.
     */
    public void setRedirectionAction(String redirectionAction) {
        this.redirectionAction = redirectionAction;
    }
    /**
     * @return Returns the redirectionCancelAction.
     */
    public String getRedirectionCancelAction() {
        return redirectionCancelAction;
    }
    /**
     * @param redirectionCancelAction The redirectionCancelAction to set.
     */
    public void setRedirectionCancelAction(String redirectionCancelAction) {
        this.redirectionCancelAction = redirectionCancelAction;
    }
    /**
     * @return Returns the redirectionText.
     */
    public String getRedirectionText() {
        return redirectionText;
    }
    /**
     * @param redirectionText The redirectionText to set.
     */
    public void setRedirectionText(String redirectionText) {
        this.redirectionText = redirectionText;
    }
    /**
     * @return Returns the redirectionUrl.
     */
    public String getRedirectionUrl() {
        return redirectionUrl;
    }
    /**
     * @param redirectionUrl The redirectionUrl to set.
     */
    public void setRedirectionUrl(String redirectionUrl) {
        this.redirectionUrl = redirectionUrl;
    }
    /**
     * @return Returns the sleepTime.
     */
    public String getSleepTime() {
        return sleepTime;
    }
    /**
     * @param sleepTime The sleepTime to set.
     */
    public void setSleepTime(String sleepTime) {
        this.sleepTime = sleepTime;
    }
    /**
     * @return Returns the sectionTitle.
     */
    public String getSectionTitle() {
        return sectionTitle;
    }
    /**
     * @param sectionTitle The sectionTitle to set.
     */
    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }
    /**
     * @return Returns the redirectionCancelButtonTitle.
     */
    public String getRedirectionCancelButtonTitle() {
        return redirectionCancelButtonTitle;
    }
    /**
     * @param redirectionCancelButtonTitle The redirectionCancelButtonTitle to set.
     */
    public void setRedirectionCancelButtonTitle(
            String redirectionCancelButtonTitle) {
        this.redirectionCancelButtonTitle = redirectionCancelButtonTitle;
    }
}
