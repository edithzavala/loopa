/*******************************************************************************
 *  Copyright (c) 2017 Universitat Politécnica de Catalunya (UPC)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *  Contributors:
 *  	Edith Zavala
 *******************************************************************************/

package org.loopa.generic.element;

import org.loopa.element.adaptationlogic.IAdaptationLogic;
import org.loopa.element.functionallogic.IFunctionalLogic;
import org.loopa.element.knowledgemanager.IKnowledgeManager;
import org.loopa.element.logicselector.ILogicSelector;
import org.loopa.element.messagecomposer.IMessageComposer;
import org.loopa.element.receiver.IReceiver;
import org.loopa.element.sender.ISender;

public abstract class ALoopElement implements ILoopAElement {
	private IReceiver receiver;
	private ILogicSelector logicSelector;
	private IFunctionalLogic functionalLogic;
	private IAdaptationLogic adaptationLogic;
	private IMessageComposer messageComposer;
	private ISender sender;
	private IKnowledgeManager knowledge;

	public ALoopElement(IReceiver receiver, ILogicSelector logicSelector, IFunctionalLogic functionalLogic,
			IAdaptationLogic adaptationLogic, IMessageComposer messageComposer, ISender sender,
			IKnowledgeManager knowledge) {
		super();
		this.receiver = receiver;
		this.logicSelector = logicSelector;
		this.functionalLogic = functionalLogic;
		this.adaptationLogic = adaptationLogic;
		this.messageComposer = messageComposer;
		this.sender = sender;
		this.knowledge = knowledge;
	}

	public IReceiver getReceiver() {
		return receiver;
	}

	public void setReceiver(IReceiver receiver) {
		this.receiver = receiver;
	}

	public ILogicSelector getLogicSelector() {
		return logicSelector;
	}

	public void setLogicSelector(ILogicSelector logicSelector) {
		this.logicSelector = logicSelector;
	}

	public IFunctionalLogic getFunctionalLogic() {
		return functionalLogic;
	}

	public void setFunctionalLogic(IFunctionalLogic functionalLogic) {
		this.functionalLogic = functionalLogic;
	}

	public IAdaptationLogic getAdaptationLogic() {
		return adaptationLogic;
	}

	public void setAdaptationLogic(IAdaptationLogic adaptationLogic) {
		this.adaptationLogic = adaptationLogic;
	}

	public IMessageComposer getMessageComposer() {
		return messageComposer;
	}

	public void setMessageComposer(IMessageComposer messageComposer) {
		this.messageComposer = messageComposer;
	}

	public ISender getSender() {
		return sender;
	}

	public void setSender(ISender sender) {
		this.sender = sender;
	}

	public IKnowledgeManager getKnowledge() {
		return knowledge;
	}

	public void setKnowledge(IKnowledgeManager knowledge) {
		this.knowledge = knowledge;
	}

}
