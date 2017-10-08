package org.loopa.element.knowledgemanager;

import org.loopa.comm.IMessage;
import org.loopa.generic.element.component.ALoopAElementComponent;

public abstract class AKnowledgeManager extends ALoopAElementComponent implements IKnowledgeManager{
	
	IAdaptiveKnowledgeManager adaptiveKnowledgeManager;
	
	@Override
	public void processAdaptiveKnowledge(IMessage m) {
		adaptiveKnowledgeManager.process(m);
	}
}
