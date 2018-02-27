package org.loopa.examples;

import java.util.HashMap;
import java.util.Map;
import org.loopa.comm.message.IMessage;
import org.loopa.comm.message.Message;
import org.loopa.element.functionallogic.enactor.monitor.IMonitorFleManager;
import org.loopa.generic.element.component.ILoopAElementComponent;
import org.loopa.policy.IPolicy;
import org.loopa.policy.Policy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;;

public class MonitorFleManager implements IMonitorFleManager {

  protected final Logger LOGGER = LoggerFactory.getLogger(getClass().getName());

  private IPolicy managerPolicy = new Policy("MonitorFleManager", null);

  private ILoopAElementComponent owner = null;

  @Override
  public void setConfiguration(Map<String, String> config) {
    LOGGER.info("Set configuration");
    this.managerPolicy.setPolicyContent(config);
  }

  @Override
  public void processLogicData(Map<String, String> monData) {
    LOGGER.info("Receive data");
    sendMonDataToAnalysis();

  }

  private void sendMonDataToAnalysis() {
    LOGGER.info("Send message to analysis");
    String code =
        this.getComponent().getElement().getElementPolicy().getPolicyContent().get("mssgOutFl");
    Map<String, String> messageContent = new HashMap<String, String>();
    messageContent.put("type", "analysis");
    messageContent.put("SymptomId", "1");
    IMessage mssg =
        new Message(this.owner.getComponentId(), this.managerPolicy.getPolicyContent().get(code),
            Integer.parseInt(code), "request", messageContent);
    ((ILoopAElementComponent) this.owner.getComponentRecipient(mssg.getMessageTo()).getRecipient())
        .doOperation(mssg);
  }

  @Override
  public void setComponent(ILoopAElementComponent c) {
    LOGGER.info("Set component");
    this.owner = c;
  }

  @Override
  public ILoopAElementComponent getComponent() {
    return this.owner;
  }
}
