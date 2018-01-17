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

package org.loopa.generic.element.component;

import java.util.Map;
import org.loopa.comm.message.IMessage;
import org.loopa.generic.documents.managers.IPolicyManager;

public interface ILoopAElementComponent {

  public void start();

  public void adapt(IMessage m);

  public void doOperation(IMessage m);

  public void setComponentRecipients(Map<String, Object> r);

  public Map<String, Object> getComponentRecipients();

  public void addRecipient(String id, Object o);

  public void removeRecipient(String id);

  public String getComponentId();

  public IPolicyManager getPolicyManager();

  public void setPolicyManager(IPolicyManager policyManager);

  public IMessageManager getMessageManager();

  public void setMessageManager(IMessageManager messageManager);
}
