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

package org.loopa.comm.messageparticipant;

public abstract class AMessageParticipant implements IMessageParticipant {
	private String name;
	private String acceptedFormat;
	private String endpoint;

	public AMessageParticipant(String name, String acceptedFormat, String endpoint) {
		super();
		this.name = name;
		this.acceptedFormat = acceptedFormat;
		this.endpoint = endpoint;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAcceptedFormat() {
		return acceptedFormat;
	}

	public void setAcceptedFormat(String acceptedFormat) {
		this.acceptedFormat = acceptedFormat;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

}
