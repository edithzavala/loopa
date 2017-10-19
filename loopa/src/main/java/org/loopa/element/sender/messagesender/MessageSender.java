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

package org.loopa.element.sender.messagesender;

import org.loopa.comm.message.IMessage;
import org.loopa.comm.message.Message;

public class MessageSender extends AMessageSender {

	@Override
	public void processMessage(IMessage t) {
		IMessage m = process(t);
		if (m != null)
			sendMessage(m);
	}

	protected IMessage process(IMessage m) {
		return new Message(m.getMessageContent(), m.getMessageCode(), this.getComponent().getComponentId(),
				getRecipientFromPolicy(m.getMessageCode()));
	}

	protected String getRecipientFromPolicy(int messageCode) {
		return this.getPolicyVariables().get(String.valueOf(messageCode));
	}

	protected void sendMessage(IMessage m) {
		/*send message to corresponding recipient*/
	}

}
