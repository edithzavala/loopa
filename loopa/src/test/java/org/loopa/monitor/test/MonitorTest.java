package org.loopa.monitor.test;

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
import org.loopa.element.sender.messagesender.MessageSender;
import org.loopa.generic.element.component.ILoopAElementComponent;
import org.loopa.monitor.IMonitor;
import org.loopa.monitor.Monitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MonitorTest {

  IMessageSender sMS;
  IFunctionalLogicEnactor flE;
  Logger logger;

  @Before
  public void initializeComponents() {
    logger = LoggerFactory.getLogger(MonitorTest.class);

    this.sMS = new MessageSender();

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
        ILoopAElementComponent r = (ILoopAElementComponent) this.getComponent()
            .getComponentRecipients().get(mRequestMonData.getMessageTo());
        r.doOperation(mRequestMonData);
        /***************************************/

      }
    };
    this.flE = new MonitorFunctionalLogicEnactor(mm);
  }

  // @Test
  // public void testCreateMonitor() {
  // IMonitor m = new Monitor("MonitorTest", this.flE, this.sMS);
  // assertNotNull(m);
  // }

  // @Test
  // public void testStartMonitor() {
  // IMonitor m = new Monitor("MonitorTest", this.flE, this.sMS);
  // m.start();
  // }

  @Test
  public void testDoLogicOperationMonitor() {
    IMonitor m = new Monitor("MonitorTest", this.flE, this.sMS);
    m.start();
    /*
     * Add recipient to the Monitor (id, object) - the HashMap is exemplifying an object
     */
    m.addElementRecipient("monData", "monitor1", new HashMap<String, String>());
    /**/

    /* Create some of the Message elements */
    int code = 1;
    Map<String, String> body = new HashMap<String, String>();
    body.put("key", "value");

    /* send a Message to the Monitor */
    m.getReceiver().doOperation(
        new Message("monitor2", m.getReceiver().getComponentId(), code, "response", body));

  }

}
