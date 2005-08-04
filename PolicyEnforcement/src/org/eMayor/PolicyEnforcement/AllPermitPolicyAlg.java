/*
 * Created on 04.08.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.eMayor.PolicyEnforcement;
import com.sun.xacml.AbstractPolicy;
//import org.apache.log4j.Logger;
import com.sun.xacml.EvaluationCtx;
import com.sun.xacml.MatchResult;


import com.sun.xacml.combine.PolicyCombiningAlgorithm;
import com.sun.xacml.ctx.Result;


import java.net.URI;
import java.net.URISyntaxException;


import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
/**
 * @author tcaciuc
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AllPermitPolicyAlg  extends PolicyCombiningAlgorithm {
//   private static final Logger log = Logger.getLogger(AllPermitPolicyAlg.class);
	public AllPermitPolicyAlg() throws URISyntaxException {
		super (new URI("policy-combining-alg:allpermit"));
	}
	public Result combine(EvaluationCtx context, List policies) {
		boolean AllPolicies = true;
		Set denyObligations = new HashSet();
		Iterator it = policies.iterator();
		while (it.hasNext())
		{
//            log.debug("AllPermitPolicyAlg:: Algorithms Started");
			AbstractPolicy mypolicy = (AbstractPolicy) (it.next());
//			log.debug("AllPermitPolicyAlg:: Get First Policy ::" +  mypolicy.getId().toString());
			MatchResult mymatch = mypolicy.match(context);
//			log.debug("AllPermitPolicyAlg:: Evalueated Rule " + mypolicy.getId().toString() + "  :: Result  " + mymatch.getResult());
			if (mymatch.getResult()==MatchResult.MATCH) {
				Result myresult = mypolicy.evaluate(context);
				if (myresult.getDecision()!=Result.DECISION_PERMIT) {
					AllPolicies = false;
					break;
				}
				
			}
		}
		
		if (AllPolicies) return new Result(Result.DECISION_PERMIT, denyObligations);
		else return new Result(Result.DECISION_DENY, denyObligations);
		
		
	}

}
