package org.loopa.element.messagecomposer;

import org.loopa.comm.IMessage;

public interface IMessageCreator {

	public void create(IMessage preprocessedMessage, String formatedContent);

}
