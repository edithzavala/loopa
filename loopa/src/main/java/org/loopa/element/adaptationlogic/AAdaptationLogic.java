package org.loopa.element.adaptationlogic;

import org.loopa.comm.IMessage;
import org.loopa.generic.element.component.ALoopAElementComponent;

public abstract class AAdaptationLogic extends ALoopAElementComponent implements IAdaptationLogic {
	protected IAdaptationEnactor adaptationEnactor;

	@Override
	public void enactAdaptation(IMessage m) {
		adaptationEnactor.enact(m);
	}
}
