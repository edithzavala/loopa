/*******************************************************************************
 * Copyright (c) 2018 Universitat Politécnica de Catalunya (UPC)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Contributors: Edith Zavala
 ******************************************************************************/
package org.loopa.element.sender.messagesender;

import java.util.Map;

import org.loopa.comm.message.IMessage;
import org.loopa.comm.message.Message;
import org.loopa.executer.effector.IEffector;
import org.loopa.generic.element.component.ILoopAElementComponent;

public class ExecuterMessageSender extends AMessageSender {

    @Override
    public void processMessage(IMessage t) {
	IMessage m = process(t);
	if (m != null)
	    sendMessage(m);
    }

    protected IMessage process(IMessage m) {
	return new Message(this.getComponent().getComponentId(), getRecipientFromPolicy(m.getMessageBody()),
		m.getMessageCode(), m.getMessageType(), m.getMessageBody());
    }

    protected String getRecipientFromPolicy(Map<String, String> messageBody) {
	return this.getPolicyVariables().get(messageBody.get("type"));
    }

    protected void sendMessage(IMessage m) {
	IEffector e = (IEffector)this.getComponent()
        .getComponentRecipients(m.getMessageTo()).getRecipient();
	e.effect(m);;
    }

}
