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

import java.util.Map;

import org.loopa.generic.documents.IPolicy;
import org.loopa.generic.documents.IPolicyChangeListener;
import org.loopa.generic.documents.Policy;

public abstract class AMessageCreator implements IMessageCreator, IPolicyChangeListener {
	private Map<String, String> policyVariables;

	protected AMessageCreator() {
		super();
	}

	@Override
	public void listen(IPolicy p) {
		setPolicyVariables(((Policy) p).getPolicyContent());
	}

	public Map<String, String> getPolicyVariables() {
		return policyVariables;
	}

	public void setPolicyVariables(Map<String, String> policyVariables) {
		this.policyVariables = policyVariables;
	}
}
