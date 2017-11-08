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

import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
		// Convert policyContent string into Map
		// @author code extracted from
		// https://stackoverflow.com/questions/41483398/split-string-and-store-it-into-hashmap-java-8
		Map<String, String> content = Pattern.compile("\\s,\\s")
				.splitAsStream((m.getMessageBody().get("policyContent"))).map(s -> s.split(":", 2))
				.collect(Collectors.toMap(a -> a[0], a -> a.length > 1 ? a[1] : ""));
		
		IPolicy p = new Policy(m.getMessageBody().get("policyOwner"), content);
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
