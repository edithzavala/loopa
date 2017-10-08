package org.loopa.element.looplogic;

import org.loopa.comm.IMessage;
import org.loopa.generic.element.component.ALoopAElementComponent;

public abstract class ALoopLogic extends ALoopAElementComponent implements ILoopLogic {
	protected ILoopMechanismEnactor loopMechanismEnactor;

	@Override
	public void enactLoopMechanism(IMessage m) {
		loopMechanismEnactor.enact(m);
	}
}
