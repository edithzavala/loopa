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

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.loopa.comm.message.IMessage;
import org.loopa.generic.documents.managers.IPolicyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.reactivex.Observable;

public abstract class ALoopAElementComponent implements ILoopAElementComponent {

	private IMessageManager messageManager;
	private IPolicyManager policyManager;
	private ConcurrentLinkedQueue<IMessage> adaptMssgQueue;
	private ConcurrentLinkedQueue<IMessage> opeMssgQueue;
	private String id;
	private Map<String, Object> recipients;
	private Logger logger;

	protected ALoopAElementComponent(String id, IPolicyManager policyManager, IMessageManager imm) {
		super();
		this.id = id;
		this.policyManager = policyManager;
		this.messageManager = imm;
		this.adaptMssgQueue = new ConcurrentLinkedQueue<>();
		this.opeMssgQueue = new ConcurrentLinkedQueue<>();
		this.recipients = new HashMap<String, Object>();
		this.policyManager.setComponent(this);
		this.messageManager.setComponent(this);
		policyManager.getActivePolicy().notifyPolicy();
//		Observable.fromIterable(adaptMssgQueue).subscribe(t -> this.policyManager.processPolicy(t));
//		Observable.fromIterable(opeMssgQueue).subscribe(t -> this.messageManager.processMessage(t));
		logger = LoggerFactory.getLogger(ALoopAElementComponent.class);

	}


	@Override
	public void adapt(IMessage m) {
//		this.adaptMssgQueue.add(m);
		Observable.just(m).subscribe(t -> this.policyManager.processPolicy(t));
	}

	@Override
	public void doOperation(IMessage m) {
//		this.opeMssgQueue.add(m);
		Observable.just(m).subscribe(t-> this.messageManager.processMessage(t));
	}

	@Override
	public void setComponentRecipients(Map<String, Object> r) {
		this.recipients = r;
	}

	@Override
	public Map<String, Object> getComponentRecipients() {
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
	public String getComponentId() {
		return this.id;
	}

	public IPolicyManager getPolicyManager() {
		return policyManager;
	}

	public void setPolicyManager(IPolicyManager policyManager) {
		this.policyManager = policyManager;
	}

	public IMessageManager getMessageManager() {
		return messageManager;
	}

	public void setMessageManager(IMessageManager messageManager) {
		this.messageManager = messageManager;
	}

}
