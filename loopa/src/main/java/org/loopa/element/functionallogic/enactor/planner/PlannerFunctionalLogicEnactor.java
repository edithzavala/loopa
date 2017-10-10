package org.loopa.element.functionallogic.enactor.planner;

import org.loopa.comm.message.IMessage;
import org.loopa.comm.message.Message;
import org.loopa.element.functionallogic.enactor.AFunctionalLogicEnactor;
import org.loopa.generic.documents.IPolicy;
import org.loopa.generic.documents.Policy;

public class PlannerFunctionalLogicEnactor extends AFunctionalLogicEnactor {

	IPlannerManager pm;

	public PlannerFunctionalLogicEnactor(IPlannerManager pm) {
		super();
		this.pm = pm;
	}

	/* Refactor to get extacly the part of the policy with the config */
	@Override
	public void listen(IPolicy p) {
		pm.setConfiguration(((Policy) p).getPolicyContent().get("Config"));

	}

	/* Refactor to get exactly the part of the message that is plan data */
	@Override
	public void enact(IMessage m) {
		this.pm.processPlanData(((Message) m).getBody());

	}

}
