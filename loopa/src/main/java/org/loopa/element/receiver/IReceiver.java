package org.loopa.element.receiver;

import org.loopa.comm.IMessage;
import org.loopa.generic.element.component.ILoopAElementComponent;

public interface IReceiver{

	public void receiveMessage(IMessage m);

}
