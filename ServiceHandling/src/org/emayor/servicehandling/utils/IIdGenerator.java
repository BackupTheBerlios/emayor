/*
 * Created on Feb 14, 2005
 */
package org.emayor.servicehandling.utils;

import javax.ejb.SessionBean;

/**
 * @author tku
 */
public interface IIdGenerator extends SessionBean {

	public String generateId();

}