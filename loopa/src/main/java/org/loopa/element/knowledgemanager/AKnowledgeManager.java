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

package org.loopa.element.knowledgemanager;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.loopa.comm.message.IMessage;
import org.loopa.element.knowledgemanager.adaptiveknowledgemanager.IAdaptiveKnowledgeManager;
import org.loopa.generic.documents.managers.IPolicyManager;
import org.loopa.generic.element.component.ALoopAElementComponent;

import io.reactivex.Observable;

public abstract class AKnowledgeManager extends ALoopAElementComponent implements IKnowledgeManager {

	private IAdaptiveKnowledgeManager adaptiveKnowledgeManager;
	private ConcurrentLinkedQueue<IMessage> opeMssgQueue;

	public AKnowledgeManager(IPolicyManager policyManager, IAdaptiveKnowledgeManager adaptiveKnowledgeManager) {
		super(policyManager);
		this.adaptiveKnowledgeManager = adaptiveKnowledgeManager;
		Observable.fromIterable(opeMssgQueue).subscribe(t -> this.adaptiveKnowledgeManager.processKnowledge(t));
	}

	@Override
	public void doOperation(IMessage m) {
		opeMssgQueue.add(m);
	}

	public IAdaptiveKnowledgeManager getAdaptiveKnowledgeManager() {
		return adaptiveKnowledgeManager;
	}

	public void setAdaptiveKnowledgeManager(IAdaptiveKnowledgeManager adaptiveKnowledgeManager) {
		this.adaptiveKnowledgeManager = adaptiveKnowledgeManager;
	}
	
	
}
