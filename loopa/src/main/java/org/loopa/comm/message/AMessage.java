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

public abstract class AMessage implements IMessage {
	private String to;
	private String from;
	private int code;
	private String type;
	private Map<String, String> body;

	public AMessage(String to, String from, int code, String type, Map<String, String> body) {
		super();
		this.to = to;
		this.from = from;
		this.code = code;
		this.type = type;
		this.body = body;
	}

	@Override
	public String getMessageTo() {
		return this.to;
	}

	@Override
	public String getMessageFrom() {
		return this.from;
	}

	@Override
	public int getMessageCode(){
		return this.code;
	}

	@Override
	public String getMessageType(){
		return this.type;
	}

	@Override
	public Map<String, String> getMessageBody(){
		return this.body;
	}
}
