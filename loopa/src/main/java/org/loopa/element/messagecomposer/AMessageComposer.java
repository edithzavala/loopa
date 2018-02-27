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
package org.loopa.element.messagecomposer;

import java.util.HashMap;
import java.util.List;
import org.loopa.element.messagecomposer.dataformatter.IDataFormatter;
import org.loopa.element.messagecomposer.messagecreator.IMessageCreator;
import org.loopa.generic.element.component.ALoopAElementComponent;
import org.loopa.policy.IPolicy;
import org.loopa.policy.Policy;

public abstract class AMessageComposer extends ALoopAElementComponent implements IMessageComposer {

  private IMessageCreator mc;

  protected AMessageComposer(String id, IDataFormatter imm, IMessageCreator mc) {
    super(id, imm);
    this.mc = mc;
  }

  @Override
  public void start() {
    super.getPolicyManager().setComponent(this);
    super.getMessageManager().setComponent(this);
    this.mc.setComponent(this);
    ((IDataFormatter) this.getMessageManager()).setMessageCreator(this.mc);
    super.getOpeMssgQueue().subscribe(m -> super.getMessageManager().processMessage(m));
    super.getAdaptMssgQueue().subscribe(t -> super.getPolicyManager().processPolicy(t));
    super.getPolicyManager().getActivePolicy().addListerner(super.getMessageManager());
    super.getPolicyManager().getActivePolicy().addListerner(this.mc);
    super.getPolicyManager().getActivePolicy().notifyPolicy();
  }

  @Override
  public void addMEToPolicies(String meId, List<String> dataType) {
    IPolicy tempP = new Policy(this.getComponentId(), new HashMap<String, String>());
    dataType.forEach(dt -> {
      tempP.getPolicyContent().put(dt,
          (this.getPolicyManager().getActivePolicy().getPolicyContent().get(dt)) != null
              ? this.getPolicyManager().getActivePolicy().getPolicyContent().get(dt) + ":" + meId
              : meId);
    });
    this.getPolicyManager().getActivePolicy().update(tempP);
  }

}
