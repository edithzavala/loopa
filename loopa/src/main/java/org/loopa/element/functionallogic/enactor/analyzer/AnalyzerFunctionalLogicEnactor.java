package org.loopa.element.functionallogic.enactor.analyzer;

import org.loopa.comm.message.IMessage;
import org.loopa.comm.message.Message;
import org.loopa.element.functionallogic.enactor.AFunctionalLogicEnactor;
import org.loopa.generic.documents.IPolicy;
import org.loopa.generic.documents.Policy;

public class AnalyzerFunctionalLogicEnactor extends AFunctionalLogicEnactor {

	IAnalyzerManager am;

	public AnalyzerFunctionalLogicEnactor(IAnalyzerManager am) {
		super();
		this.am = am;
	}

	/* Refactor to get extacly the part of the policy with the config */
	@Override
	public void listen(IPolicy p) {
		am.setConfiguration(((Policy) p).getPolicyContent().get("Config"));

	}

	/* Refactor to get exactly the part of the message that is analysis data */
	@Override
	public void enact(IMessage m) {
		this.am.processAnalysisData(((Message) m).getBody());

	}

}
