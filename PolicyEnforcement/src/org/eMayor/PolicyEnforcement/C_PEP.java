/*
 * Created on 23.05.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.eMayor.PolicyEnforcement;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import com.sun.xacml.EvaluationCtx;
import com.sun.xacml.attr.StringAttribute;
import com.sun.xacml.ctx.Attribute;
import com.sun.xacml.ctx.RequestCtx;
import com.sun.xacml.ctx.ResponseCtx;
import com.sun.xacml.ctx.Subject;



/**
 * @author tcaciuc
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class C_PEP {
	C_PDP MyPDP;
	public C_PEP (C_PDP currentPDP){
		MyPDP= currentPDP;
	}
	public boolean F_CanStartService(String UserRole, String SerivceName){
//		 Build the Request
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
		ResponseCtx response = this.MyPDP.evaluate(request);
		Set results = response.getResults();
		
		
	} catch (Exception e)
	{
		// Print exeption
	}
		return true;
	}
}
