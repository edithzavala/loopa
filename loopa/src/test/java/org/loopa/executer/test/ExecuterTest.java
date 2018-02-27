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
package org.loopa.executer.test;

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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.loopa.comm.message.IMessage;
import org.loopa.comm.message.Message;
import org.loopa.element.functionallogic.enactor.IFunctionalLogicEnactor;
import org.loopa.element.functionallogic.enactor.executer.ExecuterFunctionalLogicEnactor;
import org.loopa.element.functionallogic.enactor.executer.IExecuterManager;
import org.loopa.element.sender.messagesender.IMessageSender;
import org.loopa.executer.Executer;
import org.loopa.executer.IExecuter;
import org.loopa.generic.element.component.ILoopAElementComponent;
import org.loopa.policy.IPolicy;
import org.loopa.policy.Policy;
import org.loopa.recipient.Recipient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExecuterTest {
  IMessageSender sMS;
  IFunctionalLogicEnactor flE;
  Logger logger;
  IPolicy ep;

  @Before
  public void initializeComponents() {
    logger = LoggerFactory.getLogger(ExecuterTest.class);

    this.sMS = new IMessageSender() {

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
    this.ep = new Policy("ExecuterTest", new HashMap<String, String>() {
      {
        /**
         * mssgInFl:1 mssgInAl:2 mssgAdapt:3 mssgOutFl:4 mssgOutAl:5
         */
        put("mssgInFl", "1");
        put("mssgInAl", "2");
        put("mssgAdapt", "3");
        put("mssgOutFl", "4");
        put("mssgOutAl", "5");
      }
    });

    IExecuterManager em = new IExecuterManager() {
      private Map<String, String> config = new HashMap<String, String>();
      private ILoopAElementComponent component;

      @Override
      public void setConfiguration(Map<String, String> config) {
        this.config = config;

      }

      @Override
      public void setComponent(ILoopAElementComponent c) {
        this.component = c;
      }

      @Override
      public ILoopAElementComponent getComponent() {
        return this.component;
      }

      @Override
      public void processLogicData(Map<String, String> executeData) {

        logger.info("" + executeData);

        Map<String, String> body = new HashMap<String, String>();

        body.put("type", "callME");

        IMessage messageToME =
            new Message(this.getComponent().getComponentId(), config.get("4"), 4, "request", body);

        ILoopAElementComponent r = ((ILoopAElementComponent) this.getComponent()
            .getComponentRecipients(messageToME.getMessageTo()).getRecipient());
        r.doOperation(messageToME);

      }


    };

    this.flE = new ExecuterFunctionalLogicEnactor(em);
  }

  @Test
  public void testCreateExecuter() {
    IExecuter e = new Executer("ExecuterTest", ep, flE, sMS);
    assertNotNull(e);
  }

  @Test
  public void testDoLogicOperationPlanner() {
    IExecuter e = new Executer("ExecuterTest", ep, flE, sMS);
    e.start();

    e.addElementRecipient(
        new Recipient("me", Arrays.asList("callME"), new HashMap<String, String>()));

    int code = 1;
    Map<String, String> body = new HashMap<String, String>();
    body.put("enact", "thisAdaptation");

    e.getReceiver().doOperation(
        new Message("planner", e.getReceiver().getComponentId(), code, "request", body));

  }
}
