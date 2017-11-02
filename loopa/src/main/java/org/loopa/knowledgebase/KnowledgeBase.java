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
package org.loopa.knowledgebase;

import org.loopa.element.adaptationlogic.IAdaptationLogic;
import org.loopa.element.functionallogic.IFunctionalLogic;
import org.loopa.element.knowledgemanager.IKnowledgeManager;
import org.loopa.element.logicselector.ILogicSelector;
import org.loopa.element.messagecomposer.IMessageComposer;
import org.loopa.element.receiver.IReceiver;
import org.loopa.element.sender.ISender;
import org.loopa.generic.element.ALoopElement;

public class KnowledgeBase extends ALoopElement implements IKnowledgeBase {

	public KnowledgeBase(String id, IReceiver receiver, ILogicSelector logicSelector,
			IFunctionalLogic functionalLogic, IAdaptationLogic adaptationLogic, IMessageComposer messageComposer,
			ISender sender, IKnowledgeManager knowledge) {
		super(id, receiver, logicSelector, functionalLogic, adaptationLogic, messageComposer, sender, knowledge);
		// TODO Auto-generated constructor stub
	}



}
