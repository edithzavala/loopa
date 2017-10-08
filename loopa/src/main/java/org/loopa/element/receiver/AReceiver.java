package org.loopa.element.receiver;

import org.loopa.comm.IMessage;
import org.loopa.generic.element.component.ALoopAElementComponent;

public abstract class AReceiver extends ALoopAElementComponent implements IReceiver {

	protected IMessageProcessor messageReceiver;
	
	@Override
	public void receiveMessage(IMessage m) {
		messageReceiver.process(m);
	}
}
