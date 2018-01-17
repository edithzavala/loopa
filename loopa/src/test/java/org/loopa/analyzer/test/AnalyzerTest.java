package org.loopa.analyzer.test;

import static org.junit.Assert.assertNotNull;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.loopa.analyzer.Analyzer;
import org.loopa.analyzer.IAnalyzer;
import org.loopa.comm.message.IMessage;
import org.loopa.comm.message.Message;
import org.loopa.element.functionallogic.enactor.IFunctionalLogicEnactor;
import org.loopa.element.functionallogic.enactor.analyzer.AnalyzerFunctionalLogicEnactor;
import org.loopa.element.functionallogic.enactor.analyzer.IAnalyzerManager;
import org.loopa.element.sender.messagesender.IMessageSender;
import org.loopa.element.sender.messagesender.MessageSender;
import org.loopa.generic.documents.IPolicy;
import org.loopa.generic.documents.Policy;
import org.loopa.generic.element.component.ILoopAElementComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnalyzerTest {

  IMessageSender sMS;
  IFunctionalLogicEnactor flE;
  Logger logger;
  IPolicy ap;

  @Before
  public void initializeComponents() {
    logger = LoggerFactory.getLogger(AnalyzerTest.class);

    this.sMS = new MessageSender();
    this.ap = new Policy("AnalyzerTest", new HashMap<String, String>() {
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

    IAnalyzerManager am = new IAnalyzerManager() {
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
      public void processLogicData(Map<String, String> analysisData) {

        logger.info("" + analysisData);

        Map<String, String> body = new HashMap<String, String>();

        body.put("type", "plannerData");

        IMessage messageToPlanner =
            new Message(this.getComponent().getComponentId(), config.get("4"), 4, "request", body);

        ILoopAElementComponent r = (ILoopAElementComponent) this.getComponent()
            .getComponentRecipients().get(messageToPlanner.getMessageTo());
        r.doOperation(messageToPlanner);

      }


    };

    this.flE = new AnalyzerFunctionalLogicEnactor(am);
  }

  @Test
  public void testCreateAnalyzer() {
    IAnalyzer a = new Analyzer("AnalyzerTest", ap, flE, sMS);
    assertNotNull(a);
  }

  @Test
  public void testDoLogicOperationAnalyzer() {
    IAnalyzer a = new Analyzer("AnalyzerTest", ap, flE, sMS);
    a.start();

    a.addElementRecipient("plannerData", "planner", new HashMap<String, String>());

    int code = 1;
    Map<String, String> body = new HashMap<String, String>();
    body.put("analyze", "thisMonData");

    a.getReceiver().doOperation(
        new Message("monitor", a.getReceiver().getComponentId(), code, "request", body));

  }
}
