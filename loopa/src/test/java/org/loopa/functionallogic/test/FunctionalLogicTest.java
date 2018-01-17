package org.loopa.functionallogic.test;
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
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.loopa.element.functionallogic.FunctionalLogic;
import org.loopa.element.functionallogic.IFunctionalLogic;
import org.loopa.element.functionallogic.enactor.IFunctionalLogicEnactor;
import org.loopa.element.functionallogic.enactor.monitor.IMonitorManager;
import org.loopa.element.functionallogic.enactor.monitor.MonitorFunctionalLogicEnactor;
import org.loopa.generic.documents.IPolicy;
import org.loopa.generic.documents.Policy;
import org.loopa.generic.documents.managers.IPolicyManager;
import org.loopa.generic.documents.managers.PolicyManager;
import org.loopa.generic.element.component.ILoopAElementComponent;

public class FunctionalLogicTest {
	IPolicyManager flPM;
	IFunctionalLogicEnactor flE;

	@Before
	public void initializeModules() {
		IPolicy flP = new Policy("functionalLogicPolicy", new HashMap<String, String>());
		flPM = new PolicyManager(flP);

		IMonitorManager mm = new IMonitorManager() {

			@Override
			public void setConfiguration(Map<String, String> config) {
				// TODO Auto-generated method stub

			}

			@Override
			public void setComponent(ILoopAElementComponent c) {
				// TODO Auto-generated method stub

			}

			@Override
			public void processLogicData(Map<String, String> monData) {
				// TODO Auto-generated method stub

			}

			@Override
			public ILoopAElementComponent getComponent() {
				// TODO Auto-generated method stub
				return null;
			}
		};

		flE = new MonitorFunctionalLogicEnactor(mm);
		flP.addListerner(flE);
	}

	@Test
	public void testCreateFunctionalLogic() {
		IFunctionalLogic fl = new FunctionalLogic("fl", flPM, flE);
		assertNotNull(fl);
	}
}
