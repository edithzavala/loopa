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
package org.loopa.monitor.test;

import static org.junit.Assert.assertNotNull;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.loopa.comm.message.IMessage;
import org.loopa.comm.message.Message;
import org.loopa.element.functionallogic.enactor.IFunctionalLogicEnactor;
import org.loopa.element.functionallogic.enactor.monitor.IMonitorManager;
import org.loopa.element.functionallogic.enactor.monitor.MonitorFunctionalLogicEnactor;
import org.loopa.element.sender.messagesender.IMessageSender;
import org.loopa.generic.element.component.ILoopAElementComponent;
import org.loopa.monitor.IMonitor;
import org.loopa.monitor.Monitor;
import org.loopa.policy.IPolicy;
import org.loopa.policy.Policy;
import org.loopa.recipient.Recipient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MonitorTest {

  IMessageSender sMS;
  IFunctionalLogicEnactor flE;
  Logger logger;
  IPolicy mp;

  @Before
  public void initializeComponents() {
    logger = LoggerFactory.getLogger(MonitorTest.class);

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
    this.mp = new Policy("MonitorTest", new HashMap<String, String>() {
      {
        /**
         * mssgInFl:1 mssgInAl:2 mssgAdapt:3 mssgOutFl:4 mssgOutAl:5
         */
        put("mssgInFl", "1");
        put("mssgInAl", "2");
        put("mssgAdapt", "3"); // KM to other components
        put("mssgOutFl", "4");
        put("mssgOutAl", "5");
      }
    });

    IMonitorManager mm = new IMonitorManager() {
      /** Example of how methods can be override for the MonitorManager to work */
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

      /************************************************************************/

      @Override
      public void processLogicData(Map<String, String> monData) {

        /** Receive mon data */
        logger.info("" + monData);

        /**
         * Example of requesting monitoring data (do idem for sending response Messages when
         * required)
         */
        // ----Creat Message
        Map<String, String> body = new HashMap<String, String>();

        // Put the type of message in the body if require an special format y create the policy for
        // the formatter
        // e.g., body.put("type", "recipientX");

        body.put("type", "monData");

        IMessage mRequestMonData =
            new Message(this.getComponent().getComponentId(), config.get("4"), 4, "request", body);
        // ----
        // Send Message
        ILoopAElementComponent r = ((ILoopAElementComponent) this.getComponent()
            .getComponentRecipients(mRequestMonData.getMessageTo()).getRecipient());
        r.doOperation(mRequestMonData);
        /***************************************/

      }
    };
    this.flE = new MonitorFunctionalLogicEnactor(mm);
  }

  @Test
  public void testCreateMonitor() {
    IMonitor m = new Monitor("MonitorTest", this.mp, this.flE, this.sMS);
    assertNotNull(m);
  }

  @Test
  public void testStartMonitor() {
    IMonitor m = new Monitor("MonitorTest", this.mp, this.flE, this.sMS);
    m.start();
  }

  @Test
  public void testDoLogicOperationMonitor() {
    IMonitor m = new Monitor("MonitorTest", this.mp, this.flE, this.sMS);
    m.start();
    /*
     * Add recipient to the Monitor (id, object) - the HashMap is exemplifying an object
     */
    m.addElementRecipient(
        new Recipient("monitor1", Arrays.asList("monData"), new HashMap<String, String>()));
    /**/

    /* Create some of the Message elements */
    int code = 1;
    Map<String, String> body = new HashMap<String, String>();
    body.put("key", "value");

    /* send a Message to the Monitor */
    m.getReceiver().doOperation(
        new Message("monitor2", m.getReceiver().getComponentId(), code, "response", body));

  }

  @Test
  public void testDoAdaptMonitor() {
    IMonitor m = new Monitor("MonitorTest", this.mp, this.flE, this.sMS);
    m.start();

    int code = 2;
    logger.info(
        "current policy" + m.getReceiver().getPolicyManager().getActivePolicy().getPolicyContent());
    Map<String, String> body = new HashMap<String, String>();
    body.put("policyOwner", m.getReceiver().getComponentId());
    body.put("policyContent", "newPolicyVar:newValue");

    m.getReceiver()
        .adapt(new Message("otherLoop", m.getReceiver().getComponentId(), code, "request", body));

    logger.info(
        "new policy" + m.getReceiver().getPolicyManager().getActivePolicy().getPolicyContent());
  }

}
