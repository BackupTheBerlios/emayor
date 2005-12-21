/*
 * $ Created on 18.08.2005 by tku $
 */
package org.eMayor.legacy.test.test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.rmi.Naming;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eMayor.AdaptationLayer.ejb.M2Einterface;

/**
 * @author <a href="mailto:Tomasz.Kusber@fokus.fraunhofer.de"> <font
 *         size="-1">Tomasz Kusber </font> </a> <font size="-1"> FHI FOKUS (C)
 *         </font>
 */
public class TestClient {
	private static final Logger log = Logger.getLogger(TestClient.class);

	private static final Charset charset = Charset.forName("UTF-8");

	public static void main(String[] args) {
		BasicConfigurator.configure();
		log.setLevel(Level.ALL);
		try {
			Object obj = Naming.lookup("//localhost:2001/E2MServer");
			if (obj != null) {
				M2Einterface server = (M2Einterface) obj;
				String request = readDocument("/xml/SampleResidenceCertificationRequestDocument.xml");
				String ret = server.ResidenceRequest(request);
				log.debug("got from server:\n" + ret);
			} else {
				log.error("the reference got from NS was NULL");
			}
		} catch (Exception ex) {
			log.error("caught ex: " + ex.toString());
			ex.printStackTrace();
		}
	}

	private final static String readDocument(String doc) {
		String ret = "";
		if (log.isDebugEnabled())
			log.debug("try to read document: " + doc);
		try {
			InputStream is = TestClient.class.getResourceAsStream(doc);
			BufferedReader br = new BufferedReader(new InputStreamReader(is,
					charset));
			String line = null;
			StringBuffer b = new StringBuffer();
			while ((line = br.readLine()) != null)
				b.append(line.trim());
			if (log.isDebugEnabled())
				log.debug("read document:\n" + b.toString());
			ret = b.toString();
		} catch (Exception ex) {
			log.error("caught ex: " + ex.toString());
		}
		return ret;
	}
}