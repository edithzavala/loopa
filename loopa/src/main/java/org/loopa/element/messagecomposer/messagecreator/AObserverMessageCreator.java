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
 
package org.loopa.element.messagecomposer.messagecreator;

import java.util.HashMap;

import org.loopa.comm.message.IMessage;
import org.loopa.comm.observerobervable.IChange;
import org.loopa.comm.observerobervable.IObserver;
import org.loopa.generic.documents.Policy;

public abstract class AObserverMessageCreator implements IMessageCreator, IObserver {
	private HashMap<String, String> policyVariables;
	
	public AObserverMessageCreator() {
		super();
	}

	@Override
	public void generateMessage(IMessage formattedMessage) {
		generate(formattedMessage);
	}
	
	@Override
	public void notified(IChange change) {
		this.updatePolicyVariables((Policy)change);
	}
	
	public void updatePolicyVariables(Policy messageComposerPolicy) {
		setPolicyVariables(messageComposerPolicy.getPolicyContent());
	}
	
	public abstract void generate(IMessage m);

	public HashMap<String, String> getPolicyVariables() {
		return policyVariables;
	}

	public void setPolicyVariables(HashMap<String, String> policyVariables) {
		this.policyVariables = policyVariables;
	}
}
