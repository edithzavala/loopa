/*******************************************************************************
 * Copyright (c) 2018 Universitat Politécnica de Catalunya (UPC)
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
 ******************************************************************************/
package org.loopa.element.functionallogic.enactor;

import org.loopa.comm.message.IMessage;
import org.loopa.generic.element.component.AMessageManager;
import org.loopa.policy.IPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AFunctionalLogicEnactor extends AMessageManager
    implements IFunctionalLogicEnactor {

  protected final Logger LOGGER = LoggerFactory.getLogger(getClass().getName());

  private IFunctionalLogicEnactorManager lm;

  public AFunctionalLogicEnactor(IFunctionalLogicEnactorManager lm) {
    super();
    this.lm = lm;
  }

  @Override
  public void listen(IPolicy p) {
    this.setPolicyVariables(p.getPolicyContent());
    updateManagerConfig(p);
  }

  protected void updateManagerConfig(IPolicy p) {
    lm.setConfiguration(p.getPolicyContent());
  }

  @Override
  public void processMessage(IMessage t) {
    LOGGER.info("Process operational message");
    this.lm.processLogicData(t.getMessageBody());
  }

  @Override
  public IFunctionalLogicEnactorManager getManager() {
    return this.lm;
  }

  @Override
  public void setManager(IFunctionalLogicEnactorManager m) {
    this.lm = m;
  }
}
