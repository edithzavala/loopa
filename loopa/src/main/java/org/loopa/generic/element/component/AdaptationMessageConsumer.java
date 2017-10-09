package org.loopa.generic.element.component;

import org.loopa.comm.message.IMessage;

public class AdaptationMessageConsumer extends AMessageConsumer {

	public AdaptationMessageConsumer(ILoopAElementComponent responsibleConsumer) {
		super(responsibleConsumer);
	}

	@Override
	public void consume(IMessage m, ILoopAElementComponent responsibleConsumer) {
		// TODO extract policy from message and call
		// responsibleConsumer.updatePolicy(p);
	}

}
