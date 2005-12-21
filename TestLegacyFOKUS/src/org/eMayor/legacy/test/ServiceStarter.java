/*
 * $ Created on 18.08.2005 by tku $
 */
package org.eMayor.legacy.test;

import java.rmi.Naming;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * @author <a href="mailto:Tomasz.Kusber@fokus.fraunhofer.de"> <font
 *         size="-1">Tomasz Kusber </font> </a> <font size="-1"> FHI FOKUS (C)
 *         </font>
 */
public class ServiceStarter {
	private final static Logger log = Logger.getLogger(ServiceStarter.class);

	private static String port = "2001";

	private static String host = "localhost";

	private static String context = "E2MServer";

	public static void main(String[] args) {
		BasicConfigurator.configure();
		log.setLevel(Level.ALL);

		if (!configure(args)) {
			usage();
			System.exit(10);
		}
		try {
			if (System.getSecurityManager() == null) {
				log.debug("security manager was nul -> creating a new one");
				//System.setSecurityManager(new RMISecurityManager());
			}
			log.debug("getting instance of the service");
			LegacyServer server = new LegacyServer();
			log.debug("exporting the object ...");
			//M2Einterface service =
			// (M2Einterface)UnicastRemoteObject.exportObject(server,0);
			log.debug("getting the registry ...");
			//Registry registry = LocateRegistry.getRegistry(2001);
			log.debug("register the service to the naming service");
			StringBuffer b = new StringBuffer();
			b.append("//").append(host).append(":").append(port);
			b.append("/").append(context);
			//Naming.rebind("//localhost:2001/Seville/E2MServer", server);
			if (log.isDebugEnabled())
				log.debug("rebinding to context: " + b.toString());
			Naming.rebind(b.toString(), server);
			//registry.bind("Seville/M2EServer", server);
			log.debug("system ready");
		} catch (Exception ex) {
			log.error("caught ex: " + ex.toString());
			ex.printStackTrace();
		}
	}

	private static final boolean configure(String[] args) {
		boolean ret = true;
		int i = 0;
		while (i < args.length) {
			if (args[i].equals("--help") || args[i].equals("-?")) {
				usage();
				System.exit(0);
			}
			if (args[i].equals("--port") || args[i].equals("-p")) {
				port = args[++i];
			} else if (args[i].equals("--host") || args[i].equals("-h")) {
				host = args[++i];
			} else if (args[i].equals("--context") || args[i].equals("-c")) {
				context = args[++i];
			} else {
				usage();
				System.exit(1);
			}
		}
		return ret;
	}

	private final static void usage() {
		StringBuffer b = new StringBuffer();
		b.append("Usage:\n");
		b
				.append("java -cp %CP% org.eMayor.legacy.test.seville.ServiceStarter [opts]\n\n");
		b.append("Options:\n");
		b.append("--help\\-?               print this info\n");
		b.append("--port\\-p <port>        set the rmi port (def 2001)\n");
		b
				.append("--host\\-h <host>        set the host name (def localhost)\n");
		b
				.append("--context\\-c <context>  set the context (def Seville/E2MServer)\n");
		System.out.println(b.toString());
	}
}