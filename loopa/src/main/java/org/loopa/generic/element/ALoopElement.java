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

import java.util.HashMap;
import java.util.Map;

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
	private String id;
	private Map<String, Object> recipients;

	protected ALoopElement(String id, IReceiver receiver, ILogicSelector logicSelector,
			IFunctionalLogic functionalLogic, IAdaptationLogic adaptationLogic, IMessageComposer messageComposer,
			ISender sender, IKnowledgeManager knowledge) {
		super();
		this.receiver = receiver;
		this.logicSelector = logicSelector;
		this.functionalLogic = functionalLogic;
		this.adaptationLogic = adaptationLogic;
		this.messageComposer = messageComposer;
		this.sender = sender;
		this.knowledge = knowledge;
		this.id = id;
		this.recipients = new HashMap<String, Object>();
	}

	@Override
	public IReceiver getReceiver() {
		return receiver;
	}

	@Override
	public void setElementRecipients(Map<String, Object> r) {
		this.recipients = r;
		connectComponents();
	}

	@Override
	public Map<String, Object> getElementRecipients() {
		return this.recipients;
	}

	@Override
	public void addRecipient(String id, Object o) {
		this.recipients.put(id, o);
	}

	@Override
	public void removeRecipient(String id) {
		this.recipients.remove(id);
	}

	@Override
	public String getElementId() {
		return this.id;
	}

	private void connectComponents() {
		Map<String, Object> receiverRecipients = new HashMap<String, Object>();
		receiverRecipients.put(this.getLogicSelector().getComponentId(), this.getLogicSelector());

		Map<String, Object> logicSelectorRecipients = new HashMap<String, Object>();
		logicSelectorRecipients.put(this.getFunctionalLogic().getComponentId(), this.getFunctionalLogic());
		logicSelectorRecipients.put(this.getAdaptationLogic().getComponentId(), this.getAdaptationLogic());

		Map<String, Object> functionalLogicRecipients = new HashMap<String, Object>();
		functionalLogicRecipients.put(this.getMessageComposer().getComponentId(), this.getMessageComposer());

		Map<String, Object> adaptationLogicRecipients = new HashMap<String, Object>();
		adaptationLogicRecipients.put(this.getMessageComposer().getComponentId(), this.getMessageComposer());
		adaptationLogicRecipients.put(this.getKnowledge().getComponentId(), this.getKnowledge());

		Map<String, Object> messageComposerRecipients = new HashMap<String, Object>();
		messageComposerRecipients.put(this.getSender().getComponentId(), this.getSender());

		Map<String, Object> knowledgeManagerRecipients = new HashMap<String, Object>();
		knowledgeManagerRecipients.put(this.getReceiver().getComponentId(), this.getReceiver());
		knowledgeManagerRecipients.put(this.getLogicSelector().getComponentId(), this.getLogicSelector());
		knowledgeManagerRecipients.put(this.getFunctionalLogic().getComponentId(), this.getFunctionalLogic());
		knowledgeManagerRecipients.put(this.getAdaptationLogic().getComponentId(), this.getAdaptationLogic());
		knowledgeManagerRecipients.put(this.getMessageComposer().getComponentId(), this.getMessageComposer());
		knowledgeManagerRecipients.put(this.getSender().getComponentId(), this.getSender());

		this.getReceiver().setComponentRecipients(receiverRecipients);
		this.getLogicSelector().setComponentRecipients(logicSelectorRecipients);
		this.getFunctionalLogic().setComponentRecipients(functionalLogicRecipients);
		this.getAdaptationLogic().setComponentRecipients(adaptationLogicRecipients);
		this.getMessageComposer().setComponentRecipients(messageComposerRecipients);
		this.getSender().setComponentRecipients(this.recipients);
		this.getKnowledge().setComponentRecipients(knowledgeManagerRecipients);
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
