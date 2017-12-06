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

package org.loopa.element.functionallogic;

import org.loopa.element.functionallogic.enactor.IFunctionalLogicEnactor;
import org.loopa.generic.documents.managers.IPolicyManager;
import org.loopa.generic.element.component.ALoopAElementComponent;

public abstract class AFunctionalLogic extends ALoopAElementComponent implements IFunctionalLogic {

	protected AFunctionalLogic(String id, IPolicyManager policyManager, IFunctionalLogicEnactor imm) {
		super(id, policyManager, imm);
		imm.getManager().setComponent(this);
	}

}
