/*
 * Created on 04.08.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.eMayor.PolicyEnforcement;


import com.sun.xacml.EvaluationCtx;

import com.sun.xacml.Rule;

import com.sun.xacml.combine.RuleCombiningAlgorithm;
import com.sun.xacml.ctx.Result;


import java.net.URI;
import java.net.URISyntaxException;


import java.util.Iterator;
import java.util.List;

//import org.apache.log4j.Logger;



/**
 * @author tcaciuc
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AllPermitRuleAlg extends RuleCombiningAlgorithm{
	//private static final Logger log = Logger.getLogger(AllPermitRuleAlg.class);
	public AllPermitRuleAlg() throws URISyntaxException {
		super(new URI("rule-combining-alg:allpermit"));
		
	}
	public Result combine (EvaluationCtx context, List rules) {
		boolean AllRules = true;
		Iterator it = rules.iterator();
		while (it.hasNext())
		{
		//	log.debug("AllPermitRuleAlg:: Algorithms Started");
			Rule myrule = (Rule)(it.next());
			Result result = myrule.evaluate(context);
			
			//log.debug("AllPermitRuleAlg:: Evalueated Rule " + myrule.getId().toString() + "  :: Result  " + result.getDecision());
			if (result.getDecision()!=Result.DECISION_PERMIT)
			{
				AllRules=false;
				break;
			}
				
		}
		if (AllRules) return new Result (Result.DECISION_PERMIT);
		else return new Result (Result.DECISION_DENY);
		
		
		
		
	}
	
	
}
