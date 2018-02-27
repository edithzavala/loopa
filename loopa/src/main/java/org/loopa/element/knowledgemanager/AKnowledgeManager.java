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
package org.loopa.element.knowledgemanager;

import java.util.HashMap;
import org.loopa.generic.element.component.ALoopAElementComponent;
import org.loopa.generic.element.component.IMessageManager;
import org.loopa.policy.IPolicy;
import org.loopa.policy.Policy;

public abstract class AKnowledgeManager extends ALoopAElementComponent
    implements IKnowledgeManager {

  protected AKnowledgeManager(String id, IMessageManager imm) {
    super(id, imm);
  }

  @Override
  public void mapAdaptMssg(String mssgIn, String mssgOut) {
    IPolicy tempP = new Policy(this.getComponentId(), new HashMap<String, String>());
    tempP.getPolicyContent().put(mssgIn, mssgOut);
    this.getPolicyManager().getActivePolicy().update(tempP);
  }
}
