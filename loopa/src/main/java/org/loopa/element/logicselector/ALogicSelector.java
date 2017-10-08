package org.loopa.element.logicselector;

import org.loopa.comm.IMessage;
import org.loopa.generic.element.component.ALoopAElementComponent;

public abstract class ALogicSelector extends ALoopAElementComponent implements ILogicSelector {
	protected ILogicMessageDispatcher logicMessageDispatcher;

	@Override
	public void selectLogic(IMessage m) {
		logicMessageDispatcher.dispatch(m);
	}
}
