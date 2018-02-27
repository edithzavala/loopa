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
package org.loopa.knowledgebase.test;

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
import org.loopa.element.functionallogic.enactor.knowledgebase.IKnowledgeBaseFleManager;
import org.loopa.element.functionallogic.enactor.knowledgebase.KnowledgeBaseFuncionalLogicEnactor;
import org.loopa.element.sender.messagesender.IMessageSender;
import org.loopa.generic.element.component.ILoopAElementComponent;
import org.loopa.knowledgebase.IKnowledgeBase;
import org.loopa.knowledgebase.KnowledgeBase;
import org.loopa.policy.IPolicy;
import org.loopa.policy.Policy;
import org.loopa.recipient.Recipient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KnowledgeBaseTest {
  IMessageSender sMS;
  IFunctionalLogicEnactor flE;
  Logger logger;
  IPolicy kbp;

  @Before
  public void initializeComponents() {
    logger = LoggerFactory.getLogger(KnowledgeBaseTest.class);

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
    this.kbp = new Policy("PlannerTest", new HashMap<String, String>() {
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

    IKnowledgeBaseFleManager kbm = new IKnowledgeBaseFleManager() {
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
      public void processLogicData(Map<String, String> kbData) {

        logger.info("" + kbData);

        Map<String, String> body = new HashMap<String, String>();

        body.put("type", "dataToAnalyze");

        IMessage messageToAnalyzer =
            new Message(this.getComponent().getComponentId(), config.get("4"), 4, "request", body);

        ILoopAElementComponent r = ((ILoopAElementComponent) this.getComponent()
            .getComponentRecipient(messageToAnalyzer.getMessageTo()).getRecipient());
        r.doOperation(messageToAnalyzer);

      }


    };

    this.flE = new KnowledgeBaseFuncionalLogicEnactor(kbm);
  }

  @Test
  public void testCreateKB() {
    IKnowledgeBase k = new KnowledgeBase("KBTest", kbp, flE, sMS);
    assertNotNull(k);
  }

  @Test
  public void testDoLogicOperationPlanner() {
    IKnowledgeBase k = new KnowledgeBase("KBTest", kbp, flE, sMS);
    k.start();

    k.addElementRecipient(
        new Recipient("analyzer", Arrays.asList("dataToAnalyze"), new HashMap<String, String>()));

    int code = 1;
    Map<String, String> body = new HashMap<String, String>();
    body.put("persist", "thisData");

    k.getReceiver().doOperation(
        new Message("monitor", k.getReceiver().getComponentId(), code, "request", body));

  }
}
