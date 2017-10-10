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

package org.loopa.generic.documents;

import java.util.HashMap;

public class Policy extends APolicy {

	public Policy(String policyType, HashMap<String, String> policyContent) {
		super(policyType, policyContent);
	}

	@Override
	public void updatePolicy(IPolicy p) {
		this.setPolicyType(((Policy) p).getPolicyType());
		this.setPolicyContent(((Policy) p).getPolicyContent());
	}

}
