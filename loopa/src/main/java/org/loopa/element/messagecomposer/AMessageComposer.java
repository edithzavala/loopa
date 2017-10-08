package org.loopa.element.messagecomposer;

import org.loopa.comm.IMessage;
import org.loopa.generic.element.component.ALoopAElementComponent;

public abstract class AMessageComposer extends ALoopAElementComponent implements IMessageComposer {
	protected IDataFormatter dataFormatter;
	protected IMessageCreator messageCreator;

	@Override
	public void composeMessage(IMessage m) {
		String formatedData = dataFormatter.formatData(m.getSender().getDataFormat(),
				m.getDestination().getRequireFormat(), m.getContent());
		messageCreator.create(m, formatedData);
	}
}
