package org.loopa.generic.element.component;

import org.loopa.comm.buffer.IConsumer;
import org.loopa.comm.message.IMessage;

public abstract class AMessageConsumer implements IConsumer<IMessage> {

	private ILoopAElementComponent responsibleConsumer;

	public AMessageConsumer(ILoopAElementComponent responsibleConsumer) {
		super();
		this.responsibleConsumer = responsibleConsumer;
	}
	
	@Override
	public void consume(IMessage m) {
		consume(m, this.responsibleConsumer);
	}
	
	public abstract void consume(IMessage m, ILoopAElementComponent responsibleConsumer);

	public ILoopAElementComponent getResponsibleConsumer() {
		return responsibleConsumer;
	}

	public void setResponsibleConsumer(ILoopAElementComponent responsibleConsumer) {
		this.responsibleConsumer = responsibleConsumer;
	}

}
