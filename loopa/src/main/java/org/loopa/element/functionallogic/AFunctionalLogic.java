package org.loopa.element.functionallogic;

import org.loopa.comm.IMessage;
import org.loopa.generic.element.component.ALoopAElementComponent;

public abstract class AFunctionalLogic extends ALoopAElementComponent implements IFunctionalLogic {
	protected IFunctionEnactor functionEnactor;

	@Override
	public void enactFunction(IMessage m) {
		functionEnactor.enact(m);
	}
}
