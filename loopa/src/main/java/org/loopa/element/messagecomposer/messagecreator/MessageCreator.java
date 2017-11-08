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

import org.loopa.comm.message.IMessage;
import org.loopa.comm.message.Message;
import org.loopa.generic.element.component.ILoopAElementComponent;

public class MessageCreator extends AMessageCreator {

	@Override
	public void processMessage(IMessage t) {
		IMessage m = createMessage(t);
		if (m != null)
			sendMessage(m);
	}

	protected IMessage createMessage(IMessage m) {
		return new Message(this.getComponent().getComponentId(), getRecipientFromPolicy(m.getMessageCode()),
				m.getMessageCode(), m.getMessageType(), m.getMessageBody());
	}

	protected String getRecipientFromPolicy(int messageCode) {
		return this.getPolicyVariables().get(String.valueOf(messageCode));
	}

	protected void sendMessage(IMessage m) {
		ILoopAElementComponent r = (ILoopAElementComponent) this.getComponent().getComponentRecipients()
				.get(m.getMessageTo());
		r.doOperation(m);

	}
}
