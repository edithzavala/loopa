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
package org.loopa.element.functionallogic.enactor.analyzer;

import org.loopa.comm.message.IMessage;
import org.loopa.element.functionallogic.enactor.AFunctionalLogicEnactor;
import org.loopa.generic.documents.IPolicy;
import org.loopa.generic.documents.Policy;

public class AnalyzerFunctionalLogicEnactor extends AFunctionalLogicEnactor {

	IAnalyzerManager am;

	public AnalyzerFunctionalLogicEnactor(IAnalyzerManager am) {
		super();
		this.am = am;
	}

	/* Refactor to get extacly the part of the policy with the config */
	@Override
	public void listen(IPolicy p) {
		am.setConfiguration(((Policy) p).getPolicyContent().get("Config"));

	}

	@Override
	public void processMessage(IMessage t) {
		// TODO Auto-generated method stub
		
	}

}
