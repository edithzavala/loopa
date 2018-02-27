/*******************************************************************************
 * Copyright (c) 2018 Universitat Politécnica de Catalunya (UPC)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Contributors: Edith Zavala
 ******************************************************************************/
package org.loopa.knowledgebase;

import org.loopa.element.functionallogic.enactor.IFunctionalLogicEnactor;
import org.loopa.element.sender.messagesender.IMessageSender;
import org.loopa.generic.element.ALoopElement;
import org.loopa.policy.IPolicy;

public class KnowledgeBase extends ALoopElement implements IKnowledgeBase {

  public KnowledgeBase(String id, IPolicy p, IFunctionalLogicEnactor flE, IMessageSender sMS) {
    super(id, p, flE, sMS);
  }

}
