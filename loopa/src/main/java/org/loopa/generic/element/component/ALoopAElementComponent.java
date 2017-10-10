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
import java.util.concurrent.ConcurrentLinkedQueue;

import org.loopa.comm.message.IMessage;
import org.loopa.comm.message.Message;
import org.loopa.generic.documents.IPolicy;
import org.loopa.generic.documents.Policy;
import org.loopa.generic.documents.managers.IPolicyManager;

import io.reactivex.Observable;

public abstract class ALoopAElementComponent implements ILoopAElementComponent {

	private IPolicyManager policyManager;
	private ConcurrentLinkedQueue<IMessage> adaptMssgQueue;

	public ALoopAElementComponent(IPolicyManager policyManager) {
		super();
		this.policyManager = policyManager;
		this.adaptMssgQueue = new ConcurrentLinkedQueue<>();
		Observable.fromIterable(adaptMssgQueue)
				.subscribe(t -> this.policyManager.processPolicy(extractPolicyFromMessage(t)));

	}

	@Override
	public void adapt(IMessage m) {
		adaptMssgQueue.add(m);
	}

	/* Refactor this for matching policy format */
	private IPolicy extractPolicyFromMessage(IMessage m) {
		return new Policy(((Message) m).getBody(), new HashMap<String, String>());
	}

	public IPolicyManager getPolicyManager() {
		return policyManager;
	}

	public void setPolicyManager(IPolicyManager policyManager) {
		this.policyManager = policyManager;
	}

}
