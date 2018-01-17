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

package org.loopa.generic.element;

import java.util.Map;
import org.loopa.element.adaptationlogic.IAdaptationLogic;
import org.loopa.element.functionallogic.IFunctionalLogic;
import org.loopa.element.knowledgemanager.IKnowledgeManager;
import org.loopa.element.logicselector.ILogicSelector;
import org.loopa.element.messagecomposer.IMessageComposer;
import org.loopa.element.receiver.IReceiver;
import org.loopa.element.sender.ISender;

public interface ILoopAElement {

  public void start();

  public void setElementRecipients(Map<String, Object> r);

  public Map<String, Object> getElementRecipients();

  public void addElementRecipient(String config, String id, Object o);

  public void removeElementRecipient(String id);

  public String getElementId();

  public void setElementId(String id);

  public IReceiver getReceiver();

  public void setReceiver(IReceiver receiver);

  public ILogicSelector getLogicSelector();

  public void setLogicSelector(ILogicSelector logicSelector);

  public IFunctionalLogic getFunctionalLogic();

  public void setFunctionalLogic(IFunctionalLogic functionalLogic);

  public IAdaptationLogic getAdaptationLogic();

  public void setAdaptationLogic(IAdaptationLogic adaptationLogic);

  public IMessageComposer getMessageComposer();

  public void setMessageComposer(IMessageComposer messageComposer);

  public ISender getSender();

  public void setSender(ISender sender);

  public IKnowledgeManager getKnowledge();

  public void setKnowledge(IKnowledgeManager knowledge);

}
