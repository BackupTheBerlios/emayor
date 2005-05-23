/*
 * Created on 23.05.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.eMayor.PolicyEnforcement;

import org.eMayor.PolicyEnforcement.DBPolicyModule;

import java.io.File;

import java.io.FilenameFilter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import java.util.Set;

import org.emayor.servicehandling.config.Config;


import com.sun.xacml.PDPConfig;
import com.sun.xacml.PDP;
import com.sun.xacml.ctx.RequestCtx;
import com.sun.xacml.ctx.ResponseCtx;
import com.sun.xacml.finder.AttributeFinder;
import com.sun.xacml.finder.PolicyFinder;
import com.sun.xacml.finder.impl.CurrentEnvModule;




/**
 * @author tcaciuc
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class C_PDP {
	private PDP MyPDP;
	
	public ResponseCtx evaluate(RequestCtx request)
	{
		return MyPDP.evaluate(request);
	}
	public C_PDP() throws  E_PolicyEnforcementException{
		// Create the Policy Decizion Point based on the configurartion file from emayor
		try {
			Config config = Config.getInstance();
			String deployDir = config.getQuilifiedDirectoryName(config
					.getProperty("emayor.pe.info.dir"));
			
			File file = new File(deployDir);
			File[] files = file.listFiles(new PolicyFilenameFilter());
			// Create the PDP
			DBPolicyModule MyPolicyModule = new DBPolicyModule();
			CurrentEnvModule envModule = new CurrentEnvModule();
			
			
			
			// Load Policies
			if (files != null) {
				for (int i = 0; i < files.length; i++) {
					String filename = files[i].getAbsolutePath();
					MyPolicyModule.addPolicy(filename);
					
				}
				
				PolicyFinder mypolicyFinder = new PolicyFinder();
				Set policyModules = new HashSet();
				policyModules.add(MyPolicyModule);
				mypolicyFinder.setModules(policyModules);
				AttributeFinder myattrFinder = new AttributeFinder();
				List attrModules = new ArrayList();
				attrModules.add(envModule);
				myattrFinder.setModules(attrModules);
			    
				MyPDP = new PDP(new PDPConfig(myattrFinder,mypolicyFinder,null));
				
				
			} else {
				// Generate exception No policyes
			}
		} catch (Exception e) {
			// throuw exeption on configurarion error
			throw new E_PolicyEnforcementException("PolicyEnforcement->PDP: Initialization Execption:: "+ e.toString());
		} 
		
		/*catch (IOException ioex) {
			// throuw exeption error in policy files
			
		} */
	}
	private class PolicyFilenameFilter implements FilenameFilter {
		/*
		 * (non-Javadoc)
		 * 
		 * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
		 */
		public boolean accept(File dir, String filename) {
			
			boolean ret = false;
			if (filename != null && filename.endsWith(".policy.xml")) {
				ret = true;
			}
			return ret;
		}
	}
}
