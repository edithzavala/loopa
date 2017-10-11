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

package org.loopa.generic.element.component;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.loopa.comm.message.IMessage;
import org.loopa.generic.documents.managers.IPolicyManager;

import io.reactivex.Observable;

public abstract class ALoopAElementComponent implements ILoopAElementComponent {

	private IMessageManager messageProcessor;
	private IPolicyManager policyManager;
	private ConcurrentLinkedQueue<IMessage> adaptMssgQueue;
	private ConcurrentLinkedQueue<IMessage> opeMssgQueue;

	protected ALoopAElementComponent(IPolicyManager policyManager, IMessageManager imm) {
		super();
		this.policyManager = policyManager;
		this.messageProcessor = imm;
		this.adaptMssgQueue = new ConcurrentLinkedQueue<>();
		this.opeMssgQueue = new ConcurrentLinkedQueue<>();
		Observable.fromIterable(adaptMssgQueue).subscribe(t -> this.policyManager.processPolicy(t));
		Observable.fromIterable(opeMssgQueue).subscribe(t -> this.messageProcessor.processMessage(t));

	}

	@Override
	public void adapt(IMessage m) {
		adaptMssgQueue.add(m);
	}

	@Override
	public void doOperation(IMessage m) {
		opeMssgQueue.add(m);
	}

	public IPolicyManager getPolicyManager() {
		return policyManager;
	}

	public void setPolicyManager(IPolicyManager policyManager) {
		this.policyManager = policyManager;
	}

	public IMessageManager getMessageProcessor() {
		return messageProcessor;
	}

	public void setMessageProcessor(IMessageManager messageProcessor) {
		this.messageProcessor = messageProcessor;
	}

}
