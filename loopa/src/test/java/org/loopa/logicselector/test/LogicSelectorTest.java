package org.loopa.logicselector.test;
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
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.loopa.element.logicselector.ILogicSelector;
import org.loopa.element.logicselector.LogicSelector;
import org.loopa.element.logicselector.messagedispatcher.ILogicMessageDispatcher;
import org.loopa.element.logicselector.messagedispatcher.LogicMessageDispatcher;
import org.loopa.generic.documents.IPolicy;
import org.loopa.generic.documents.Policy;
import org.loopa.generic.documents.managers.IPolicyManager;
import org.loopa.generic.documents.managers.PolicyManager;

public class LogicSelectorTest {
	IPolicyManager lsPM;
	ILogicMessageDispatcher lsMD;

	@Before
	public void initializeModules() {
		IPolicy lsP = new Policy("logicSelectorPolicy", new HashMap<String, String>());
		lsPM = new PolicyManager(lsP);
		lsMD = new LogicMessageDispatcher();
		lsP.addListerner(lsMD);
	}

	@Test
	public void testCreateLogicSelector() {
		ILogicSelector ls = new LogicSelector("ls", lsPM, lsMD);
		assertNotNull(ls);
	}

}
