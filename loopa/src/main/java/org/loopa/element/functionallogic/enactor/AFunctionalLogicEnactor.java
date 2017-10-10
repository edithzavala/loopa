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

package org.loopa.element.functionallogic.enactor;

import java.util.HashMap;

import org.loopa.comm.message.IMessage;
import org.loopa.generic.documents.IPolicyChangeListener;

public abstract class AFunctionalLogicEnactor implements IFunctionalLogicEnactor, IPolicyChangeListener {
	private HashMap<String, String> policyVariables;

	public AFunctionalLogicEnactor() {
		super();
	}

	@Override
	public void enactFunctionalLogic(IMessage m) {
		enact(m);
	}

	public abstract void enact(IMessage m);

	public HashMap<String, String> getPolicyVariables() {
		return policyVariables;
	}

	public void setPolicyVariables(HashMap<String, String> policyVariables) {
		this.policyVariables = policyVariables;
	}
}