package org.loopa.element.functionallogic.enactor.executer;

import org.loopa.comm.message.IMessage;
import org.loopa.comm.message.Message;
import org.loopa.element.functionallogic.enactor.AFunctionalLogicEnactor;
import org.loopa.generic.documents.IPolicy;
import org.loopa.generic.documents.Policy;

public class ExecuterFunctionalLogicEnactor extends AFunctionalLogicEnactor {

	IExecuterManager em;

	public ExecuterFunctionalLogicEnactor(IExecuterManager em) {
		super();
		this.em = em;
	}

	/* Refactor to get extacly the part of the policy with the config */
	@Override
	public void listen(IPolicy p) {
		em.setConfiguration(((Policy) p).getPolicyContent().get("Config"));

	}

	/* Refactor to get exactly the part of the message that is execution data */
	@Override
	public void enact(IMessage m) {
		this.em.processExecutionData(((Message) m).getBody());

	}
}
