package org.emayor.install;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/*
 * Created on 19.05.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author mxs
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MessageKeys {

	private Properties keys;
	
	public MessageKeys(ByteArrayInputStream stream) {
		try {
			keys = new Properties();
			keys.load(stream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) { }
	}
	/**
	 * @return Returns the value of the given key.
	 */
	public String getKey(String key) {
		return keys.getProperty(key);
	}
}
