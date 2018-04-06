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
package org.loopa.generic.element.component;

import java.util.List;
import org.loopa.comm.message.IMessage;
import org.loopa.generic.element.ILoopAElement;
import org.loopa.policy.manager.IPolicyManager;
import org.loopa.recipient.IRecipient;

public interface ILoopAElementComponent {
  /** Element **/

  ILoopAElement getElement();

  void setElement(ILoopAElement eId);

  /** Component **/

  // Get component id
  String getComponentId();

  // // Start component operation
  void construct();

  // Process a new message
  void doOperation(IMessage m);

  // Process an adaptation request
  void adapt(IMessage m);

  /** Component's recipients **/

  // Add a series of recipients
  void setComponentRecipients(List<IRecipient> recipients);

  // Add a recipient
  void addRecipient(IRecipient r);

  // Get component's recipients
  List<IRecipient> getComponentRecipients();

  // Get recipient with id==is
  IRecipient getComponentRecipient(String id);

  // Remove component recipient with id==id
  void removeRecipient(String id);

  /** Component's managers **/

  // Get component's policy manager
  IPolicyManager getPolicyManager();

  // Set component's policy manager
  void setPolicyManager(IPolicyManager policyManager);

  // Get component's operational message manager
  IMessageManager getMessageManager();

  // Set component's operational message manager
  void setMessageManager(IMessageManager messageManager);
}
