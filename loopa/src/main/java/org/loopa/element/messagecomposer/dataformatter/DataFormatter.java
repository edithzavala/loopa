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

package org.loopa.element.messagecomposer.dataformatter;

import java.util.Arrays;
import java.util.Map;

import org.loopa.comm.message.IMessage;
import org.loopa.comm.message.Message;

public class DataFormatter extends ADataFormatter {

	@Override
	public void processMessage(IMessage t) {
		String[] recipients = getRecipientFromPolicy(t.getMessageBody()).split(";");
		Arrays.stream(recipients).forEach(r -> {
			Map<String, String> formattedM = formatMessageContent(t.getMessageBody(), r);
			IMessage formattedMessage = new Message(t.getMessageFrom(), t.getMessageTo(), t.getMessageCode(),
					t.getMessageType(), formattedM);
			createMeassage(formattedMessage);
		});
	}

	protected Map<String, String> formatMessageContent(Map<String, String> m, String r) {
		return m;
	}

	protected String getRecipientFromPolicy(Map<String, String> messageContent) {
		return this.getPolicyVariables().get(messageContent.get("type"));
	}

	protected void createMeassage(IMessage formattedMessage) {
		this.getMessageCreator().processMessage(formattedMessage);

	}
}
