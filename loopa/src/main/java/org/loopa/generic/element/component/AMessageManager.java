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
package org.loopa.generic.element.component;

import java.util.Map;

import org.loopa.policy.IPolicy;

public abstract class AMessageManager implements IMessageManager {

	private Map<String, String> policyVariables;
	private ILoopAElementComponent component;

	@Override
	public void listen(IPolicy p) {
		setPolicyVariables(p.getPolicyContent());
	}

	public Map<String, String> getPolicyVariables() {
		return policyVariables;
	}

	public void setPolicyVariables(Map<String, String> policyVariables) {
		this.policyVariables = policyVariables;
	}

	@Override
	public void setComponent(ILoopAElementComponent c) {
		this.component = c;
	}

	@Override
	public ILoopAElementComponent getComponent() {
		return this.component;
	}
}
