package org.loopa.functionallogic.test;

/*******************************************************************************
 * Copyright (c) 2017 Universitat Politécnica de Catalunya (UPC)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 * Contributors: Edith Zavala
 *******************************************************************************/
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.loopa.element.functionallogic.FunctionalLogic;
import org.loopa.element.functionallogic.IFunctionalLogic;
import org.loopa.element.functionallogic.enactor.IFunctionalLogicEnactor;
import org.loopa.element.functionallogic.enactor.monitor.IMonitorManager;
import org.loopa.element.functionallogic.enactor.monitor.MonitorFunctionalLogicEnactor;

public class FunctionalLogicTest {

  IFunctionalLogicEnactor flE;

  @Before
  public void initializeModules() {
    IMonitorManager mm = null;
    flE = new MonitorFunctionalLogicEnactor(mm);
  }

  @Test
  public void testCreateFunctionalLogic() {
    IFunctionalLogic fl = new FunctionalLogic("fl", flE);
    assertNotNull(fl);
  }
}
