package org.loopa.element.functionallogic.enactor.monitor;

import org.loopa.comm.message.IMessage;
import org.loopa.comm.message.Message;
import org.loopa.element.functionallogic.enactor.AFunctionalLogicEnactor;
import org.loopa.generic.documents.IPolicy;
import org.loopa.generic.documents.Policy;

public class MonitorFunctionalLogicEnactor extends AFunctionalLogicEnactor {

	IMonitorsManager mm;

	public MonitorFunctionalLogicEnactor(IMonitorsManager mm) {
		super();
		this.mm = mm;
	}

	/*Refactor to get extacly the part of the policy with the config*/
	@Override
	public void listen(IPolicy p) {
		mm.setConfiguration(((Policy) p).getPolicyContent().get("Config"));

	}

	/*Refactor to get exactly the part of the message that is monitoring data*/
	@Override
	public void enact(IMessage m) {
		this.mm.processMonData(((Message)m).getBody()); 

	}

}
