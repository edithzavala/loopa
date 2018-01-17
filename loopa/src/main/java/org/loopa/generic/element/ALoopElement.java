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

package org.loopa.generic.element;

import java.util.HashMap;
import java.util.Map;
import org.loopa.element.adaptationlogic.AdaptationLogic;
import org.loopa.element.adaptationlogic.IAdaptationLogic;
import org.loopa.element.adaptationlogic.enactor.AdaptationLogicEnactor;
import org.loopa.element.adaptationlogic.enactor.IAdaptationLogicEnactor;
import org.loopa.element.functionallogic.FunctionalLogic;
import org.loopa.element.functionallogic.IFunctionalLogic;
import org.loopa.element.functionallogic.enactor.IFunctionalLogicEnactor;
import org.loopa.element.knowledgemanager.IKnowledgeManager;
import org.loopa.element.knowledgemanager.KnowledgeManager;
import org.loopa.element.knowledgemanager.adaptiveknowledgemanager.AdaptiveKnowledgeManager;
import org.loopa.element.knowledgemanager.adaptiveknowledgemanager.IAdaptiveKnowledgeManager;
import org.loopa.element.logicselector.ILogicSelector;
import org.loopa.element.logicselector.LogicSelector;
import org.loopa.element.logicselector.messagedispatcher.ILogicMessageDispatcher;
import org.loopa.element.logicselector.messagedispatcher.LogicMessageDispatcher;
import org.loopa.element.messagecomposer.IMessageComposer;
import org.loopa.element.messagecomposer.MessageComposer;
import org.loopa.element.messagecomposer.dataformatter.DataFormatter;
import org.loopa.element.messagecomposer.dataformatter.IDataFormatter;
import org.loopa.element.messagecomposer.messagecreator.IMessageCreator;
import org.loopa.element.messagecomposer.messagecreator.MessageCreator;
import org.loopa.element.receiver.IReceiver;
import org.loopa.element.receiver.Receiver;
import org.loopa.element.receiver.messageprocessor.IMessageProcessor;
import org.loopa.element.receiver.messageprocessor.MessageProcessor;
import org.loopa.element.sender.ISender;
import org.loopa.element.sender.Sender;
import org.loopa.element.sender.messagesender.IMessageSender;
import org.loopa.generic.element.component.ILoopAElementComponent;

public abstract class ALoopElement implements ILoopAElement {
  private IReceiver receiver;
  private ILogicSelector logicSelector;
  private IFunctionalLogic functionalLogic;
  private IAdaptationLogic adaptationLogic;
  private IMessageComposer messageComposer;
  private ISender sender;
  private IKnowledgeManager knowledge;
  private String id;
  private Map<String, Object> recipients;

  protected ALoopElement(String id, IFunctionalLogicEnactor flE, IMessageSender sMS) {
    this.id = id;
    this.recipients = new HashMap<String, Object>();

    IMessageProcessor rMP = new MessageProcessor();
    ILogicMessageDispatcher lsMD = new LogicMessageDispatcher();
    IAdaptationLogicEnactor alE = new AdaptationLogicEnactor();
    IDataFormatter mcDF = new DataFormatter();
    IMessageCreator mcMC = new MessageCreator();
    IAdaptiveKnowledgeManager kAKM = new AdaptiveKnowledgeManager();

    this.receiver = new Receiver("receiver", rMP);
    this.sender = new Sender("sender", sMS);
    this.logicSelector = new LogicSelector("logicSelector", lsMD);
    this.functionalLogic = new FunctionalLogic("functionalLogic", flE);
    this.adaptationLogic = new AdaptationLogic("adaptationLogic", alE);
    this.messageComposer = new MessageComposer("messageComposer", mcDF, mcMC);
    this.knowledge = new KnowledgeManager("knowledge", kAKM);
  }

  protected ALoopElement(String id, IReceiver receiver, ILogicSelector logicSelector,
      IFunctionalLogic functionalLogic, IAdaptationLogic adaptationLogic,
      IMessageComposer messageComposer, ISender sender, IKnowledgeManager knowledge) { // Old_constructor
    super();
    this.receiver = receiver;
    this.logicSelector = logicSelector;
    this.functionalLogic = functionalLogic;
    this.adaptationLogic = adaptationLogic;
    this.messageComposer = messageComposer;
    this.sender = sender;
    this.knowledge = knowledge;
    this.id = id;
    this.recipients = new HashMap<String, Object>();
    start();
  }


  @Override
  public void setElementRecipients(Map<String, Object> r) {
    this.recipients = r;
    this.sender.setComponentRecipients(this.recipients);
    this.recipients.forEach((k, v) -> {
      String[] config_id = k.split(":");
      this.sender.getPolicyManager().getActivePolicy().getPolicyContent().put(config_id[0],
          config_id[1]);
    });
  }

  @Override
  public void addElementRecipient(String config, String id, Object o) {
    this.recipients.put(id, o);
    this.sender.addRecipient(id, o);
    this.sender.getPolicyManager().getActivePolicy().getPolicyContent().put(config, id);
  }

  @Override
  public Map<String, Object> getElementRecipients() {
    return this.recipients;
  }

  @Override
  public void removeElementRecipient(String id) {
    this.recipients.remove(id);
    this.sender.removeRecipient(id);
  }


  @Override
  public void start() {

    this.receiver.start();
    this.sender.start();
    this.logicSelector.start();
    this.functionalLogic.start();
    this.adaptationLogic.start();
    this.messageComposer.start();
    this.knowledge.start();

    /**
     * 1: messageIn_NormalOpe 2: messageIn_Adapt 3: AdaptReqFromKM 4: messageOut_normalOpe 5:
     * messageOut_Adapt
     */

    setElementComponentsRecipient(this.getReceiver(), "1", this.getLogicSelector());
    setElementComponentsRecipient(this.getReceiver(), "2", this.getLogicSelector());

    setElementComponentsRecipient(this.getLogicSelector(), "1", this.getFunctionalLogic());
    setElementComponentsRecipient(this.getLogicSelector(), "2", this.getAdaptationLogic());

    setElementComponentsRecipient(this.getFunctionalLogic(), "4", this.getMessageComposer());

    setElementComponentsRecipient(this.getAdaptationLogic(), "5", this.getMessageComposer());
    setElementComponentsRecipient(this.getAdaptationLogic(), "2", this.getKnowledge());

    setElementComponentsRecipient(this.getMessageComposer(), "4", this.getSender());
    setElementComponentsRecipient(this.getMessageComposer(), "5", this.getSender());

    setElementComponentsRecipient(this.getKnowledge(), "3", this.getReceiver());
    setElementComponentsRecipient(this.getKnowledge(), "3", this.getLogicSelector());
    setElementComponentsRecipient(this.getKnowledge(), "3", this.getFunctionalLogic());
    setElementComponentsRecipient(this.getKnowledge(), "3", this.getAdaptationLogic());
    setElementComponentsRecipient(this.getKnowledge(), "3", this.getMessageComposer());
    setElementComponentsRecipient(this.getKnowledge(), "3", this.getSender());
  }


  private void setElementComponentsRecipient(ILoopAElementComponent c, String config,
      ILoopAElementComponent recipient) {
    c.addRecipient(recipient.getComponentId(), recipient);
    c.getPolicyManager().getActivePolicy().getPolicyContent().put(config,
        recipient.getComponentId());

  }

  // getters_setters

  @Override
  public String getElementId() {
    return this.id;
  }

  @Override
  public void setElementId(String id) {
    this.id = id;
  }

  @Override
  public IReceiver getReceiver() {
    return receiver;
  }

  @Override
  public void setReceiver(IReceiver receiver) {
    this.receiver = receiver;
  }

  @Override
  public ILogicSelector getLogicSelector() {
    return logicSelector;
  }

  @Override
  public void setLogicSelector(ILogicSelector logicSelector) {
    this.logicSelector = logicSelector;
  }

  @Override
  public IFunctionalLogic getFunctionalLogic() {
    return functionalLogic;
  }

  @Override
  public void setFunctionalLogic(IFunctionalLogic functionalLogic) {
    this.functionalLogic = functionalLogic;
  }

  @Override
  public IAdaptationLogic getAdaptationLogic() {
    return adaptationLogic;
  }

  @Override
  public void setAdaptationLogic(IAdaptationLogic adaptationLogic) {
    this.adaptationLogic = adaptationLogic;
  }

  @Override
  public IMessageComposer getMessageComposer() {
    return messageComposer;
  }

  @Override
  public void setMessageComposer(IMessageComposer messageComposer) {
    this.messageComposer = messageComposer;
  }

  @Override
  public ISender getSender() {
    return sender;
  }

  @Override
  public void setSender(ISender sender) {
    this.sender = sender;
  }

  @Override
  public IKnowledgeManager getKnowledge() {
    return knowledge;
  }

  @Override
  public void setKnowledge(IKnowledgeManager knowledge) {
    this.knowledge = knowledge;
  }

}
