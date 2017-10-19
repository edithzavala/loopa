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

package org.loopa.comm.message;

import java.util.Map;

public class Message extends AMessage {

	public Message(Map<String, String> body, int code, String from, String to) {
		super(body, code, from, to);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getMessageCode() {
		return this.getCode();
	}

	@Override
	public Map<String, String> getMessageContent() {
		return this.getBody();
	}

	@Override
	public String getMessageSender() {
		return this.getFrom();
	}

	@Override
	public String getMessageRecipient() {
		return this.getTo();
	}

}
