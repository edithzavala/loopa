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

import org.loopa.comm.buffer.IProducer;
import org.loopa.comm.message.IMessage;

public abstract class AMessageProducer implements IProducer<IMessage> {

	private IMessage m;

	public AMessageProducer(String endPoint) {
		super();
		this.m = null;
		listen(endPoint);
	}
	
	public abstract void listen(String endPoint);

	@Override
	public IMessage produce() {
		IMessage temp = null;
		if (m != null) {
			temp = m;
			m = null;
		}
		return temp;
	}

	public IMessage getM() {
		return m;
	}

	public void setM(IMessage m) {
		this.m = m;
	}
	
}
