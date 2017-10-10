package org.loopa.element.functionallogic.enactor.knowledgebase;

import org.loopa.comm.message.IMessage;
import org.loopa.comm.message.Message;
import org.loopa.element.functionallogic.enactor.AFunctionalLogicEnactor;
import org.loopa.generic.documents.IPolicy;
import org.loopa.generic.documents.Policy;

public class KnowledgeBaseFuncionalLogicEnactor extends AFunctionalLogicEnactor {
	IKnowledgeBaseManager kbm;

	public KnowledgeBaseFuncionalLogicEnactor(IKnowledgeBaseManager kbm) {
		super();
		this.kbm = kbm;
	}

	/* Refactor to get extacly the part of the policy with the config */
	@Override
	public void listen(IPolicy p) {
		kbm.setConfiguration(((Policy) p).getPolicyContent().get("Config"));

	}

	/* Refactor to get exatcly the part of the message that is knowldge data */
	@Override
	public void enact(IMessage m) {
		this.kbm.processKnowledgeData(((Message) m).getBody());

	}

}
