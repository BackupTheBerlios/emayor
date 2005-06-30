/*
 * Created on 23.05.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.eMayor.PolicyEnforcement;

import org.apache.log4j.Logger;
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
	private PDP MyPDP=null;
	private static final Logger log = Logger.getLogger(C_PDP.class);
	
	public PDP F_getCurrentPDP(){
		return MyPDP;
	}
	public ResponseCtx evaluate(RequestCtx request)
	{
		log.debug("PolicyEnforcement->PDP->evaluate :: got request " + request.toString());
		return MyPDP.evaluate(request);
	}
	public C_PDP() throws  E_PolicyEnforcementException{
		// Create the Policy Decizion Point based on the configurartion file from emayor
		
			log.debug("PolicyEnforcement->PDP:: Initializing..");
		try {
			Config config = Config.getInstance();
			String deployDir = config.getQuilifiedDirectoryName(config
					.getProperty("emayor.pe.info.dir"));
			
			log.debug("Get the policies directory " + deployDir);
			File file = new File(deployDir);
			
			
			File[] files = file.listFiles(new PolicyFilenameFilter());
			
			// Create the PDP
			
				
			
				DBPolicyModule MyPolicyModule = new DBPolicyModule();
				CurrentEnvModule envModule = new CurrentEnvModule();
			
			
			
			// Load Policies
			if (files != null) {
				
				if (files.length < 1)  throw new E_PolicyEnforcementException("No Policies have bean Loaded!!!!" );
				
				
				for (int i = 0; i < files.length; i++) {
					String filename = files[i].getAbsolutePath();
					MyPolicyModule.addPolicy(filename);
					
				}
				log.debug("PolicyEnforcement->PDP:: Loaded the policy files");
				PolicyFinder mypolicyFinder = new PolicyFinder();
				Set policyModules = new HashSet();
				policyModules.add(MyPolicyModule);
				mypolicyFinder.setModules(policyModules);
				AttributeFinder myattrFinder = new AttributeFinder();
				List attrModules = new ArrayList();
				attrModules.add(envModule);
				myattrFinder.setModules(attrModules);
			    
				MyPDP = new PDP(new PDPConfig(myattrFinder,mypolicyFinder,null));
				log.debug("PolicyEnforcement->PDP:: Created the PDP");
				
			} else {
				throw new E_PolicyEnforcementException("PDP: Initialization Execption:: No Policies have bean Loaded!!!!" );
			}
		} catch (Exception e) {
			// throuw exeption on configurarion error
			throw new E_PolicyEnforcementException("PDP: Initialization Execption:: "+ e.toString());
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
			if (filename != null && filename.endsWith(".policy")) {
				ret = true;
			}
			return ret;
		}
	}
}
