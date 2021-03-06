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
package org.loopa.sender.test;

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
import org.loopa.comm.message.IMessage;
import org.loopa.element.sender.ISender;
import org.loopa.element.sender.Sender;
import org.loopa.element.sender.messagesender.IMessageSender;
import org.loopa.generic.element.component.ILoopAElementComponent;
import org.loopa.policy.IPolicy;

public class SenderTest {
  IMessageSender sMS;

  @Before
  public void initializeModules() {
    sMS = new IMessageSender() {

      @Override
      public void processMessage(IMessage t) {
        // TODO Auto-generated method stub

      }

      @Override
      public void setComponent(ILoopAElementComponent c) {
        // TODO Auto-generated method stub

      }

      @Override
      public ILoopAElementComponent getComponent() {
        // TODO Auto-generated method stub
        return null;
      }

      @Override
      public void listen(IPolicy p) {
        // TODO Auto-generated method stub

      }

    };
  }

  @Test
  public void testCreateSender() {
    ISender s = new Sender("s", sMS);
    assertNotNull(s);
  }
}
