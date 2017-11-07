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

package org.loopa.generic.documents.managers;

import java.util.HashMap;
import java.util.Map;

import org.loopa.comm.message.IMessage;
import org.loopa.generic.documents.IPolicy;
import org.loopa.generic.documents.Policy;
import org.loopa.generic.element.component.ILoopAElementComponent;

public abstract class APolicyManager implements IPolicyManager {

	private IPolicy activePolicy;
	private ILoopAElementComponent component;

	public APolicyManager(IPolicy policy) {
		super();
		this.activePolicy = policy;
	}

	@Override
	public void processPolicy(IMessage m) {
		Map<String, String> content = new HashMap<String, String>();
		//Convert "policy" (String of key:value) into a HashMap and pass it directly as the policyContent
		/*content.put("policyContent", m.getMessageContent().get("policy"));*/
		IPolicy p = new Policy(m.getMessageContent().get("type"), content);
		this.activePolicy.update(p);
	}

	@Override
	public void setComponent(ILoopAElementComponent c) {
		this.component = c;
	}

	@Override
	public ILoopAElementComponent getComponent() {
		return this.component;
	}

	@Override
	public IPolicy getActivePolicy() {
		return getPolicy();
	}

	public IPolicy getPolicy() {
		return activePolicy;
	}

	public void setPolicy(IPolicy newPolicy) {
		this.activePolicy = newPolicy;
	}

}
