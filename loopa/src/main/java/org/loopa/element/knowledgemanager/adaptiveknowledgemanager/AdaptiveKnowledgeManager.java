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
package org.loopa.element.knowledgemanager.adaptiveknowledgemanager;

import org.loopa.comm.message.IMessage;
import org.loopa.comm.message.Message;
import org.loopa.generic.element.component.ILoopAElementComponent;

public class AdaptiveKnowledgeManager extends AAdaptiveKnowledgeManager {

  @Override
  public void processMessage(IMessage t) {
    IMessage m = processAdaptationMessage(t);
    if (m != null)
      sendMessage(m);
  }

  protected IMessage processAdaptationMessage(IMessage m) {
    return new Message(this.getComponent().getComponentId(), m.getMessageBody().get("policyOwner"),
        getCodeFromPolicy(m.getMessageCode()), m.getMessageType(), m.getMessageBody());
  }

  protected int getCodeFromPolicy(int messageCode) {
    return Integer.parseInt(this.getPolicyVariables().get(String.valueOf(messageCode)));
  }

  protected void sendMessage(IMessage m) {
    ((ILoopAElementComponent) this.getComponent().getComponentRecipient(m.getMessageTo())
        .getRecipient()).adapt(m);
  }

}
