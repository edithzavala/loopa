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
 
package org.loopa.element.receiver;

import org.loopa.comm.message.IMessage;
import org.loopa.element.receiver.messageprocessor.IMessageProcessor;
import org.loopa.generic.documents.managers.IPolicyManager;
import org.loopa.generic.element.component.ALoopAElementComponent;

public abstract class AReceiver extends ALoopAElementComponent implements IReceiver {

	private IMessageProcessor messageProcessor;

	public AReceiver(String mainEndPoint, String adaptationEndPoint, IPolicyManager policyManager, IMessageProcessor messageProcessor) {
		super(mainEndPoint, adaptationEndPoint, policyManager);
		this.messageProcessor = messageProcessor;
	}

	@Override
	public void receiveMessage(IMessage m) {
		messageProcessor.processMessage(m);
	}

	public IMessageProcessor getMessageProcessor() {
		return messageProcessor;
	}

	public void setMessageProcessor(IMessageProcessor messageProcessor) {
		this.messageProcessor = messageProcessor;
	}

}
