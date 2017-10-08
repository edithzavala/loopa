package org.loopa.element.sender;

import org.loopa.comm.IMessage;
import org.loopa.generic.element.component.ILoopAElementComponent;

public interface ISender {
	public void sendMessage(IMessage m);
}
