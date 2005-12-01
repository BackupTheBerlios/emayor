/*
 * Created on Feb 18, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.eMayor.PolicyEnforcement;

import org.eMayor.PolicyEnforcement.CertificateValidation.*;
import org.emayor.servicehandling.config.Config;

import java.io.StringReader;
import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import javax.ejb.CreateException;

import org.apache.log4j.Logger;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.utils.Constants;
import org.apache.xml.security.utils.XMLUtils;
import org.apache.xpath.XPathAPI;

import com.sun.xacml.AbstractPolicy;
import com.sun.xacml.Rule;




import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import java.util.List;
import java.util.Set;
import org.eMayor.PolicyEnforcement.XMLSignature.VerifySignature;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * @ejb.bean name="PolicyEnforcement"
 *           display-name="eMayor Policy Enforcemet Bean"
 *           description="Description for PolicyEnforcement"
 *           jndi-name="ejb/PolicyEnforcement"
 *           type="Stateless"
 *           view-type="both"
 */
public class PolicyEnforcementBean implements SessionBean {
	private static final Logger log = Logger
		.getLogger(PolicyEnforcementBean.class);

	// Create the PEP and the PDP
	private static C_PEP MyPEP = null;
	private static VerifySignature  MyVerifier= new VerifySignature();

	/**
	 * 
	 */

	public PolicyEnforcementBean() {

		super();

		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see javax.ejb.SessionBean#setSessionContext(javax.ejb.SessionContext)
	 */
	public void setSessionContext(SessionContext ctx)
		throws EJBException,
		RemoteException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.ejb.SessionBean#ejbRemove()
	 */
	public void ejbRemove() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.ejb.SessionBean#ejbActivate()
	 */
	public void ejbActivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.ejb.SessionBean#ejbPassivate()
	 */
	public void ejbPassivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	/**
	 * Default create method
	 * 
	 * @throws CreateException
	 * @ejb.create-method
	 */
	public void ejbCreate() throws CreateException {
		// TODO Auto-generated method stub
	}

	/**
	 * Business method
	 * @ejb.interface-method  view-type = "both"
	 */
	public String F_getUserProfile(X509Certificate x509_CertChain[])
		throws E_PolicyEnforcementException {
		// TODO Auto-generated method stub
		try {
			log.debug("PolicyEnfocement->Create User Profile");
			return (new C_UserProfile(x509_CertChain))
				.F_getUserProfileasString();
		} catch (C_UserProfile.E_UserProfileException e) {
			throw new E_PolicyEnforcementException(
				"PolicyEnforcement::F_getUserProfile:: Exception \n"
					+ e.toString());
		}
	}
	/**
	 * Business method
	 * @ejb.interface-method  view-type = "both"
	 */
	public boolean F_AuthenticateUser(String UserProfile)
		throws E_PolicyEnforcementException {
		// TODO Auto-generated method stub
		C_UserProfile myUserProfile;
		// Get the configuation parameters
		Config config = null;
		int result = 0;
		CertificateValidator cv = null;
		try {
			config = Config.getInstance();

			// Validate the user certificate
			String sCRL = config
				.getProperty(Config.EMAYOR_PE_CRL_DISTRIBUTION_URL);
			boolean bUserDefCRL = new Boolean(config
				.getProperty(Config.EMAYOR_PE_CRL_USE_DEFAULT_DISTRIBUTION_URL))
				.booleanValue();

			//log.debug("Policy Enforcement->F_AuthorizeService:: get configuration as " + sCRL+ " :: " +  bUserDefCRL);
			cv = new CertificateValidator(sCRL, bUserDefCRL);
			result = CertificateValidator.CertUntrusted;

			if (MyPEP == null) {
				MyPEP = new C_PEP(new C_PDP());
				log
					.debug("Policy Enforcement->F_AuthorizeService:: Create the PEP");
			}
			myUserProfile = new C_UserProfile(UserProfile);
		} catch (Exception e) {
			throw new E_PolicyEnforcementException(
				"PolicyEnforcement::F_AuthenticateUser:: Exception \n"
					+ e.toString());
		}

		result = cv.validateChain(myUserProfile.getX509_CertChain());
		String sValidationResult = "CertUntrusted";
		if (result == CertificateValidator.CertTrusted)
			sValidationResult = "CertTrusted";
		else if (result == CertificateValidator.NoCRLConnection)
			sValidationResult = "NoCRLConnection";

		java.security.Principal myIssuer = ((myUserProfile.getX509_CertChain())[0])
			.getIssuerDN();
		String sCA = "Not Available";
		if (myIssuer != null)
			sCA = myIssuer.getName();

		return MyPEP.F_AuthenticateUser(sValidationResult, myUserProfile
			.getUserRole(), sCA, myUserProfile.getUserCountry());

	}
	/**
	 * Business method
	 * @ejb.interface-method  view-type = "both"
	 */
	public boolean F_VerifyXMLSignature(String xmlDocument, String sUserProfile)
		throws E_PolicyEnforcementException {
		
		return MyVerifier.Verify(xmlDocument, sUserProfile);
	}
	/**
	 * Business method
	 * @ejb.interface-method  view-type = "both"
	 */
	public String F_TimeStampXMLDocument(String xmlDocumentDoc)
		throws E_PolicyEnforcementException {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * Business method
	 * @ejb.interface-method  view-type = "both"
	 */
	public boolean F_VerifyXMLTimeStampedDocument(String xmlDocument)
		throws E_PolicyEnforcementException {
		// TODO Auto-generated method stub
		return true;
	}
	/**
	 * Business method
	 * @ejb.interface-method  view-type = "both"
	 */
	public boolean F_AuthorizeService(String UserProfile, String ServiceProfile)
		throws E_PolicyEnforcementException {
		// TODO Auto-generated method stub
		try {
			if (MyPEP == null) {
				MyPEP = new C_PEP(new C_PDP());
				log
					.debug("Policy Enforcement->F_AuthorizeService:: Create the PEP");
			}

			C_UserProfile MyUserProfile = new C_UserProfile(UserProfile);
			log
				.debug("Policy Enforcement->F_AuthorizeService:: Got The User Profile with role"
					+ MyUserProfile.getUserRole());
			return MyPEP.F_CanStartService(
				MyUserProfile.getUserRole(),
				ServiceProfile);
		} catch (Exception e) {
			throw new E_PolicyEnforcementException(
				"PolicyEnforcement->F_AuthorizeService: Exeption ::"
					+ e.toString());
		}

	}
	/**
	 * Business method
	 * @ejb.interface-method  view-type = "both"
	 */
	public boolean F_AuthorizeServiceStep(
		String UserProfile,
		String ServiceProfile,
		String ServiceStep) throws E_PolicyEnforcementException {
		// TODO Auto-generated method stub
		return true;
	}
	/**
	 * Business method
	 * @ejb.interface-method  view-type = "both"
	 */
	public void F_UpdatePolicies() throws E_PolicyEnforcementException {
		// TODO Auto-generated method stub
		try {
			MyPEP = new C_PEP(new C_PDP());
		} catch (Exception e) {
			throw new E_PolicyEnforcementException(
				"PolicyEnforcement->F_UpdatePolicies: Exeption ::"
					+ e.toString());
		}

	}

	/**
	 * Business method
	 * @ejb.interface-method  view-type = "both"
	 */
	public boolean F_NewUserProfile(String newUserProfile) {
		if (log.isDebugEnabled())
			log
				.debug("Policy Enforcement->F_NewUserProfile::"
					+ newUserProfile);
		return true;

	}

	/**
	 * Business method
	 * @ejb.interface-method  view-type = "both"
	 */
	public Set FPM_GetPoliciesList(String PolicySetID, List PolicyList) {
		// TODO Auto-generated method stub
		Set Result = new HashSet(); 
		try {
		
		if (MyPEP == null) {
			MyPEP = new C_PEP(new C_PDP());
			log.debug("Policy Enforcement->F_AuthorizeService:: Create the PEP");
		}
		Set mypolicies = (PolicyEnforcementBean.MyPEP.F_getCurrentPDP()).MyPolicyModule.policies;
		 if (PolicySetID!=null) {
		 	//============================
//		 	We need to list the policies from the Policy Set
        	//We check if we have a root policy
		 	AbstractPolicy policy = null;
        	if (PolicyList!=null) {
//        		We have a policy from a policy set
        		policy = FPM_findPollicy(PolicySetID, PolicyList);
        		
        		
        	}  else {
        	    // we look for a root policy
        		Iterator it = mypolicies.iterator();
    			while (it.hasNext()) {
    	            AbstractPolicy Allpolicy = (AbstractPolicy)(it.next());
    	            String myName = (Allpolicy.getId()).toString();
    	            if (myName.equals(PolicySetID)) {
    	            	
    	            	policy =Allpolicy;
    	            	break; // while
    	            } //end if
    	               
    			} // end while
        		
    			
        	} // end else
        		
        	if (policy!=null) {
        		
        	
        		
//        		 We have a root policy with sub policies
        		//Let' find the Root policy
        		if ((policy.getId().toString()).equalsIgnoreCase(PolicySetID)) {
        			//we have our policy, let's list the child policies
        			List myPoliciesChild = policy.getChildren();
        			if (myPoliciesChild!=null){// We need to be shure
        				Iterator itch = myPoliciesChild.iterator();
        				while (itch.hasNext()) {
        					Object myElement = itch.next();
        					// We need only the policy elements
        					String MyClassName = myElement.getClass().getName();
        					
        					if ((MyClassName.equals("com.sun.xacml.Policy")) || (MyClassName.equals("com.sun.xacml.PolicySet"))) {
        						// OK Supper
        						Result.add(((AbstractPolicy)myElement).getId().toString());
        					} //end if 
        					
        				} // end while
        			} //end if
        			
        		} else {
        			// wrong policy
        		} // end else
        		
        		
        	
        	
        } // end if
	
		 	//===========Big Loop end================
		 	
		 } else 
		 {
		 	Iterator it = mypolicies.iterator();
			while (it.hasNext()) {
	            AbstractPolicy policy = (AbstractPolicy)(it.next());
	            String myName = (policy.getId()).toString();
	            
	              
	                Result.add(myName);
			} //end while
		 } // end else
		
		

            
		} catch (Exception e)
		{
			log.debug("PE::FPM_GetPoliciesList Exception" + e.toString());
		}
		
		return Result;
	}
	
	/**
	 * Business method
	 * @ejb.interface-method  view-type = "both"
	 */
	public boolean FPM_isPolicySet(String PolicyID, List PolicyList) {
		boolean result = false;
		//log.debug("FPM_isPolicySet:: Start Processing ...");
		try {
			Set mypolicies = (PolicyEnforcementBean.MyPEP.F_getCurrentPDP()).MyPolicyModule.policies;
			 AbstractPolicy policy =null;
			if (PolicyList!=null) {
	         	// we need to find the right policy
	         	policy = FPM_findPollicy(PolicyID, PolicyList);
	         	
	         }  //we have a root policy
			else {
			
				Iterator it = mypolicies.iterator();
				while (it.hasNext()) {
					AbstractPolicy Newpolicy = (AbstractPolicy)(it.next());
					String myName = (Newpolicy.getId()).toString();
		         
					if (myName.equals(PolicyID)) {
						policy = Newpolicy;
					} //end if
				} //end while
				
				
			} // end else
			//log.debug("FPM_isPolicySet:: Get Policy : " + policy.getId().toString());
			List myList = policy.getChildren();
			if (myList!=null){
         	// Check if the Childerns are Policies or Policy Sets
				String FirtChild = myList.get(0).getClass().getName();
				//log.debug("FPM_isPolicySet:: Class Name of the Firt Child is "+ FirtChild);
				
				result = FirtChild.equals("com.sun.xacml.Policy");
				if (!result) result = FirtChild.equals("com.sun.xacml.PolicySet");
				
			} else
				log.debug("FPM_isPolicySet:: Policy has no Children");
		} catch (Exception e) {
			log.debug("PE::FPM_isPolicySet Exception " + e.toString());
		}
		//log.debug("FPM_isPolicySet:: Stop Processing ...");
		return result;
	}
	
	private AbstractPolicy FPM_findPollicy(String PolicyID, List PolicyList) {
		Set mypolicies = (PolicyEnforcementBean.MyPEP.F_getCurrentPDP()).MyPolicyModule.policies;
		Iterator it = mypolicies.iterator();
		//log.debug("FPM_findPollicy:: PolicyID" + PolicyID);
	/*	for (int i=0; i<PolicyList.size(); i++) {
			log.debug("FPM_findPollicy:: PolicySet[" +i+"]="+ (String)PolicyList.get(i));
		} */
		
		while (it.hasNext()) {
			 AbstractPolicy policy = (AbstractPolicy)(it.next());
	         String myName = (policy.getId()).toString();
	         if (PolicyList==null) {
	         	// we look for a root policy
	         	if (myName.equals(PolicyID)) return policy;
	         	
	         } else {
	         	
	         	// Find the Root
	         	 
	         	if (myName.equals((String)PolicyList.get(0))) {
	         		AbstractPolicy TheRoot = policy ;
	         	//	log.debug("PE::FPM_findPollicy:: find the Policy 0 = "+myName);
	         		// Lets find the need root
	         		for (int i=1; i<PolicyList.size();i++) {
	         			List MyChilds = TheRoot.getChildren();
	         			TheRoot = null; // we need to reset
	         			if (MyChilds!=null) {
	         				Iterator mynewIT = MyChilds.iterator();
	         				while (mynewIT.hasNext()) {
	         					Object MyNewObject = mynewIT.next();
	         					String MyClassName = MyNewObject.getClass().getName();
	         				//	log.debug("PE::FPM_findPollicy:: Object Type is = "+MyClassName);
	         					if ((MyClassName.equals("com.sun.xacml.Policy")) || (MyClassName.equals("com.sun.xacml.PolicySet"))) {
	         						String ChildPolicyID =((AbstractPolicy)MyNewObject).getId().toString();
	         				//		log.debug("PE::FPM_findPollicy:: Child ID = "+ChildPolicyID);
	         						if (ChildPolicyID.equals(((String) PolicyList.get(i)))) {
	         						//	log.debug("PE::FPM_findPollicy:: Child Selected as Root has ID = "+ChildPolicyID);
	         							TheRoot = (AbstractPolicy)MyNewObject;
	         							break;
	         						}  //end if
	         						
	         					} //end if
	         				} //end while
	         				
	         				
	         			} else 
	         			{
	         				log.debug("PE::FPM_findPollicy:: error, NoChildren-- shoud never happen!!!");
	         			}
	         			
	         			if (TheRoot==null) {
	         				log.debug("PE::FPM_findPollicy:. error the policy has not bean find");
	         				break; //for
	         				
							
	         			}  //end if
	         			
	         		} // end for
	         		if (TheRoot==null) break; // the while cicle;
	         		else {
	         			// Lets find the policy
	         			List MyChildren = TheRoot.getChildren();
	         			Iterator ChildIT = MyChildren.iterator();
	         			while (ChildIT.hasNext()) {
	         				Object MyNewObject = ChildIT.next();
	         				String MyClassName = MyNewObject.getClass().getName();
         					if ((MyClassName.equals("com.sun.xacml.Policy")) || (MyClassName.equals("com.sun.xacml.PolicySet"))) {
         						if (((AbstractPolicy)MyNewObject).getId().toString().equals( PolicyID )) {
         							return (AbstractPolicy)MyNewObject;
         						}
         					}
	         			}
         						
	         		}
	         	} //end if
	         	 
	         	
	         }// end if else
	         	
	         
		} // end while
			
	         
		
		return null;
	}
	
	//################################
	/**
	 * Business method
	 * @ejb.interface-method  view-type = "both"
	 */
	public List FPM_GetRuleList(String PolicyID, List PolicyList) {
		// TODO Auto-generated method stub
		
		
		
		List Result = new ArrayList(); 
	//	log.debug("FPM_GetRuleList:: Start Processing ...");
	//	log.debug("FPM_GetRuleList:: PolicyID" + PolicyID);
	/*	if (PolicyList==null) {
			log.debug("FPM_GetRuleList:: PolicySet =null");
		} else {
			for (int i=0; i<PolicyList.size(); i++) {
				log.debug("FPM_GetRuleList:: PolicySet[" +i+"]="+ (String)PolicyList.get(i));
			}	
		}
		*/
		try {
		
		if (MyPEP == null) {
			MyPEP = new C_PEP(new C_PDP());
			log.debug("Policy Enforcement->F_AuthorizeService:: Create the PEP");
		}
		Set mypolicies = (PolicyEnforcementBean.MyPEP.F_getCurrentPDP()).MyPolicyModule.policies;
		 if (PolicyID!=null) {
		 	//============================
//		 	We need to list the policies from the Policy Set
        	//We check if we have a root policy
		 	AbstractPolicy policy = null;
        	if (PolicyList!=null) {
//        		We have a policy from a policy set
        		policy = FPM_findPollicy(PolicyID, PolicyList);
        		
        		
        	}  else {
        	    // we look for a root policy
        		Iterator it = mypolicies.iterator();
    			while (it.hasNext()) {
    	            AbstractPolicy Allpolicy = (AbstractPolicy)(it.next());
    	            String myName = (Allpolicy.getId()).toString();
    	            if (myName.equals(PolicyID)) {
    	            	
    	            	policy =Allpolicy;
    	            	break; // while
    	            } //end if
    	               
    			} // end while
        		
    			
        	} // end else
        		
        	if (policy!=null) {
        		
        	
        		
//        		 We have a root policy with sub policies
        		//Let' find the Root policy
        		
        			
        		//	log.debug("FPM_GetRuleList:: The Root Policy is: " + policy.getId().toString());
        			//we have our policy, let's list the child policies
        			List myPoliciesChild = policy.getChildren();
        			if (myPoliciesChild!=null){// We need to be shure
        				Iterator itch = myPoliciesChild.iterator();
        				while (itch.hasNext()) {
        					Object myElement = itch.next();
        					// We need only the policy elements
        					String MyClassName = myElement.getClass().getName();
        					
        					if (MyClassName.equals("com.sun.xacml.Rule")) {
        						// OK Supper add the Rule ID
        					//	log.debug("FPM_GetRuleList:: Retrive Ruls:");
        						String RuleID = ((com.sun.xacml.Rule)myElement).getId().toString();
        					//	log.debug("FPM_GetRuleList:: " + RuleID);
        						Result.add(RuleID);
        						
        						// Add the Effect
        						int MyEffect = ((com.sun.xacml.Rule)myElement).getEffect();
        						if (MyEffect==0) 
        							Result.add("Permit");
        						else Result.add("Deny");
        						
        						
        					} //end if 
        					
        				} // end while
        			
        			
        		} else {
        			log.debug("FPM_GetRuleList:: The Root Policy has no childs ");
        		}
        		
        		
        	
        	
        } // end if
	
		 	//===========Big Loop end================
		 	
		 } else 
		 {
		 	//Error
		 	log.debug("PE::FPM_GetRuleList -> Error PolicyID is missing");
			 //end while
		 } // end else
		
		

            
		} catch (Exception e)
		{
			log.debug("PE::FPM_GetRuleList Exception" + e.toString());
		}
		log.debug("FPM_GetRuleList:: End Processing ...");
		return Result;
	}
	
	private Rule FPM_FindRule(String RuleID, String PolicyID, List PolicyList) {
		AbstractPolicy myPolicy = this.FPM_findPollicy(PolicyID, PolicyList);
		if (myPolicy==null){
			log.debug("FPM_FindRule:: error can not find the policy");
			return null;
		}
		List myRools = myPolicy.getChildren();
		if (myRools==null) {
			log.debug("FPM_FindRule:: error can the policy " + myPolicy.getId().toString() + " has no Children");
			
		}
		
		Iterator it = myRools.iterator();
		while (it.hasNext()){
			Object MyObject = it.next();
			String ObName = MyObject.getClass().getName();
			if (ObName.equals("com.sun.xacml.Rule")){
				Rule myRule= (Rule)MyObject;
				String myRuleID = myRule.getId().toString();
				if (myRuleID.equals(RuleID)) return myRule;
				
			}
		}
		
		return null;
	}
	//################################
	/**
	 * Business method
	 * @ejb.interface-method  view-type = "both"
	 */
	public void FPM_ChangeRuleEffect(String RuleID, String PolicyID, List PolicyList) {
		Rule myRule = this.FPM_FindRule(RuleID, PolicyID,  PolicyList);
		if (myRule==null) {
			log.debug("FPM_ChangeRuleEffect:: error can not find role");
			return;
		}
		int MyEffect = myRule.getEffect();
		List myChildren = myRule.getChildren();
		
		
		if (MyEffect==0) {
			myRule.setEffect(1);
			
		} else myRule.setEffect(0);
		
		
	}
	
	
	//################################
	/**
	 * Business method
	 * @ejb.interface-method  view-type = "both"
	 */
	public String F_getSignerRole(String SignedDocument) {
		String SignerRole="N/A";
		log.debug("F_getSignerRole:: StartProcessing...");
		if (SignedDocument!=null) {
			// let's parce the string 
			javax.xml.parsers.DocumentBuilderFactory dbf =
			javax.xml.parsers.DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			
			try {
				javax.xml.parsers.DocumentBuilder db = dbf.newDocumentBuilder();
	      		db.setErrorHandler(new org.apache.xml.security.utils.IgnoreAllErrorHandler());
	      		StringReader myStrReader = new StringReader(SignedDocument);
	    		//if (log.isDebugEnabled()) log.debug("F_StringtoDocument(String)::got the String Reader...");
	    		
	    		InputSource myInputSource = new InputSource(myStrReader);
	      		
	      		org.w3c.dom.Document doc = db.parse(myInputSource);
	     		
	      		log.debug("F_getSignerRole:: Document parsed, looking for signatures");
	            Element nscontext = XMLUtils.createDSctx(doc, "ds",
	                                                  Constants.SignatureSpecNS);
	     		NodeList myNodeList = XPathAPI.selectNodeList(doc,
	     				"//ds:Signature", nscontext);
	     		if (myNodeList!=null) {
	     			     		
	     			int Nodes = myNodeList.getLength();
	     			log.debug("FPM_getSignerRole:: Found " + Nodes+ " Signatures, looking in the last one");
	     			Element sigElement = (Element)myNodeList.item(Nodes-1);
	     			XMLSignature signature = new XMLSignature(sigElement,"");
	     			X509Certificate cert = signature.getKeyInfo().getX509Certificate();
	     			if (cert != null) {
	     				X509Certificate [] MyCertChain = new X509Certificate[1];
	     				MyCertChain[0] = cert;
	     				C_UserProfile myTempProfile = new C_UserProfile(MyCertChain);
	     				if (myTempProfile!=null) {
	     					SignerRole = myTempProfile.getUserRole();
	     					log.debug("F_getSignerRole:: The Role is "+ SignerRole);
	     				} else {
	     					log.debug("F_getSignerRole::Error cannot create a temporary UserProfile");
	     				}
	     				
	     			} else {
	     				log.debug("F_getSignerRole::Error cannot find a certificate in the signature");
	     			}
	     			
	     		} else {
	     			log.debug("F_getSignerRole::Error Can not find a signature in the Document");
	     		}
	     		
			}catch (Exception e) {
				e.printStackTrace();
				SignerRole="N/A";
			}
			
		} else
		{
			log.debug("F_getSignerRole::Error The signed Document is null");
		}
		
		log.debug("F_getSignerRole:: EndProcessing...");
		return SignerRole;
		
	}
		
		
}

