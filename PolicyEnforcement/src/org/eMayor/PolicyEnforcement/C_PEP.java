/*
 * Created on 23.05.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.eMayor.PolicyEnforcement;

import java.io.ByteArrayOutputStream;

import java.net.URI;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import com.sun.xacml.EvaluationCtx;
import com.sun.xacml.attr.StringAttribute;
import com.sun.xacml.ctx.Attribute;
import com.sun.xacml.ctx.RequestCtx;
import com.sun.xacml.ctx.ResponseCtx;
import com.sun.xacml.ctx.Result;
import com.sun.xacml.ctx.Subject;



/**
 * @author tcaciuc
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class C_PEP {
	private C_PDP MyPDP=null;
	private static final Logger log = Logger.getLogger(C_PEP.class);
	public C_PEP (C_PDP currentPDP) throws E_PolicyEnforcementException{
		MyPDP= currentPDP;
		if (MyPDP==null){
			throw new E_PolicyEnforcementException ("PEP:: get null for PDP");
		}
			
		log.debug("PolicyEnforcement->PEP:: Initialising the PDP");
	}
	public C_PDP F_getCurrentPDP() {
		return MyPDP;
	}
	public boolean F_CanStartService(String UserRole, String SerivceName) throws  E_PolicyEnforcementException 
	{
//		 Build the Request
		log.debug("PolicyEnfocement->PEP->F_CanStartService( User Role: "+UserRole+", Service: "+SerivceName+")");
	try {
	//Creates Subjects
		HashSet attributes = new HashSet();
        attributes.add(new Attribute(new URI("role"),
                                     null, null,
                                     new StringAttribute(UserRole)));

        // bundle the attributes in a Subject with the default category
        HashSet subjects = new HashSet();
        subjects.add(new Subject(attributes));
        
        
    // Create Resources
        
        HashSet resource = new HashSet();

        // the resource being requested
        StringAttribute value1 = new StringAttribute(SerivceName);

        // create the resource using a standard, required identifier for
        // the resource being requested
        resource.add(new Attribute(new URI(EvaluationCtx.RESOURCE_ID),
                                   null, null, value1));
		
		
	// Create Action
        HashSet action = new HashSet();

        // this is a standard URI that can optionally be used to specify
        // the action being requested
        URI actionId =
            new URI("urn:oasis:names:tc:xacml:1.0:action:action-id");

        // create the action
        action.add(new Attribute(actionId, null, null,
                                 new StringAttribute("Start")));
     
        
    // Create the request    
		RequestCtx request = new RequestCtx(subjects, resource, action, new HashSet());
		
		// Evaluet the request
		ByteArrayOutputStream myOutStram = new ByteArrayOutputStream();
		request.encode(myOutStram);
		
		log.debug("PolicyEnforcement->PEP:: Created Request :: " + myOutStram.toString());
		ResponseCtx response = MyPDP.evaluate(request);
		
		
		myOutStram = new ByteArrayOutputStream();
		response.encode(myOutStram);
		
		
		
		log.debug("PolicyEnforcement->PEP:: Got Response :: " + myOutStram.toString());
		Set results = response.getResults();
		Iterator resultiterator= results.iterator();
		
		  while (resultiterator.hasNext()) {
		  	
		  
            Result finalresult = (Result)(resultiterator.next());
            int decision = finalresult.getDecision();
            String sdecision ="";
            switch (decision) {
            case Result.DECISION_DENY:  sdecision="DENY"; break;
            case Result.DECISION_INDETERMINATE: sdecision = "INDETERMINATE"; break;
            case Result.DECISION_NOT_APPLICABLE: sdecision = "NOT_APPLICABLE"; break;
            case Result.DECISION_PERMIT: sdecision = "PERMIT"; break;
            }
			
			  
            log.debug("PolicyEnfocement->PEP->Decision = " + sdecision + " or = " + decision);
            if (decision == Result.DECISION_PERMIT)
            {
            	return true;
            }
            else {
            	return false;
            }
		  }  
		
		
		
	} catch (Exception e)
	{
		throw new E_PolicyEnforcementException ("PEP:F_CanStartService: Exception \n" +e.toString());
	}
		return false;
	}
	
	public boolean F_AuthenticateUser(String sTrusted, String sRole, String sCA, String sCountry) throws  E_PolicyEnforcementException {
		
		
		log.debug("PolicyEnfocement->PEP->F_AuthenticateUser( Trusted: "+sTrusted+", Role: "+sRole+", CA: "+sCA+", Country: "+sCountry+")");
		try {
		//Creates Subjects
			HashSet attributes = new HashSet();
	        attributes.add(new Attribute(new URI("Trusted"),
	                                     null, null,
	                                     new StringAttribute(sTrusted)));

	        attributes.add(new Attribute(new URI("Role"),
                    null, null,
                    new StringAttribute(sRole)));
	        
	        attributes.add(new Attribute(new URI("CA"),
                    null, null,
                    new StringAttribute(sCA)));
	        
	        attributes.add(new Attribute(new URI("Country"),
                    null, null,
                    new StringAttribute(sCountry)));

	        
	        // bundle the attributes in a Subject with the default category
	        HashSet subjects = new HashSet();
	        subjects.add(new Subject(attributes));
	        
	        
	    // Create Resources
	        
	        HashSet resource = new HashSet();

	        // the resource being requested
	        StringAttribute value1 = new StringAttribute("eMayorPlatformAachen");

	        // create the resource using a standard, required identifier for
	        // the resource being requested
	        resource.add(new Attribute(new URI(EvaluationCtx.RESOURCE_ID),
	                                   null, null, value1));
			
			
		// Create Action
	        HashSet action = new HashSet();

	        // this is a standard URI that can optionally be used to specify
	        // the action being requested
	        URI actionId =
	            new URI("urn:oasis:names:tc:xacml:1.0:action:action-id");

	        // create the action
	        action.add(new Attribute(actionId, null, null,
	                                 new StringAttribute("Authenticate")));
	     
	        
	    // Create the request    
			RequestCtx request = new RequestCtx(subjects, resource, action, new HashSet());
			
			// Evaluet the request
			ByteArrayOutputStream myOutStram = new ByteArrayOutputStream();
			request.encode(myOutStram);
			
			log.debug("PolicyEnforcement->PEP:: Created Request :: " + myOutStram.toString());
			ResponseCtx response = MyPDP.evaluate(request);
			myOutStram = new ByteArrayOutputStream();
			response.encode(myOutStram);
			
			
			
			log.debug("PolicyEnforcement->PEP:: Got Response :: " + myOutStram.toString());
			Set results = response.getResults();
			Iterator resultiterator= results.iterator();
			
			  while (resultiterator.hasNext()) {
			  	
			  
	            Result finalresult = (Result)(resultiterator.next());
	            int decision = finalresult.getDecision();
	            String sdecision ="";
	            switch (decision) {
	            case Result.DECISION_DENY:  sdecision="DENY"; break;
	            case Result.DECISION_INDETERMINATE: sdecision = "INDETERMINATE"; break;
	            case Result.DECISION_NOT_APPLICABLE: sdecision = "NOT_APPLICABLE"; break;
	            case Result.DECISION_PERMIT: sdecision = "PERMIT"; break;
	            }
				
				  
	            log.debug("PolicyEnfocement->PEP->Decision = " + sdecision + " or = " + decision);
	            if (decision == Result.DECISION_PERMIT)
	            {
	            	return true;
	            }
	            else {
	            	return false;
	            }
			  }  
			
			
			
		} catch (Exception e)
		{
			throw new E_PolicyEnforcementException ("PEP:F_AuthenticateUser: Exception \n" +e.toString());
		}
		return false;
	}

}
