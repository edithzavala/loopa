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
package org.loopa.generic.element;

import java.util.List;
import org.loopa.element.adaptationlogic.IAdaptationLogic;
import org.loopa.element.functionallogic.IFunctionalLogic;
import org.loopa.element.knowledgemanager.IKnowledgeManager;
import org.loopa.element.logicselector.ILogicSelector;
import org.loopa.element.messagecomposer.IMessageComposer;
import org.loopa.element.receiver.IReceiver;
import org.loopa.element.sender.ISender;
import org.loopa.policy.IPolicy;
import org.loopa.recipient.IRecipient;

public interface ILoopAElement {

  /** Element **/
  // Get element id
  String getElementId();

  // Set element id
  void setElementId(String id);

  // Get element policy
  IPolicy getElementPolicy();

  // Start element operation - Connect all element's components
  void construct();

  /** Element's recipients **/

  // Add a series of recipients
  void setElementRecipients(List<IRecipient> recipients);

  // Add a recipient
  void addElementRecipient(IRecipient r);

  // Get element's recipients
  List<IRecipient> getElementRecipients();

  // Get recipient with id==is
  IRecipient getElementRecipient(String id);

  // Remove element recipient with id==id
  void removeElementRecipient(String id);

  /** Element's components **/

  // Get element's components
  IReceiver getReceiver();

  ILogicSelector getLogicSelector();

  IFunctionalLogic getFunctionalLogic();

  IAdaptationLogic getAdaptationLogic();

  IKnowledgeManager getKnowledge();

  IMessageComposer getMessageComposer();

  ISender getSender();

  // Set element's components
  void setReceiver(IReceiver receiver);

  void setLogicSelector(ILogicSelector logicSelector);

  void setFunctionalLogic(IFunctionalLogic functionalLogic);

  void setAdaptationLogic(IAdaptationLogic adaptationLogic);

  void setKnowledge(IKnowledgeManager knowledge);

  void setMessageComposer(IMessageComposer messageComposer);

  void setSender(ISender sender);
}
