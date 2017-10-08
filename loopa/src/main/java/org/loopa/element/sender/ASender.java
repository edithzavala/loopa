package org.loopa.element.sender;

import org.loopa.comm.IMessage;
import org.loopa.generic.element.component.ALoopAElementComponent;

public abstract class ASender extends ALoopAElementComponent implements ISender {

	protected IMessageSender messageSender;

	@Override
	public void sendMessage(IMessage m) {
		messageSender.send(m);
	}

}
