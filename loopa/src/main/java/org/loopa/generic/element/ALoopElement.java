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
import org.loopa.generic.documents.IPolicy;
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
  private IPolicy p;

  protected ALoopElement(String id, IPolicy p, IFunctionalLogicEnactor flE, IMessageSender sMS) {
    this.id = id;
    this.recipients = new HashMap<String, Object>();
    this.p = p;

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
    //modify policies
    this.recipients.forEach((k, v) -> {
      String[] config_id = k.split(":");
      this.sender.getPolicyManager().getActivePolicy().getPolicyContent().put(config_id[0].concat("_").concat(config_id[1]),
          config_id[1]);
      String mcPolicyValue = this.messageComposer.getPolicyManager().getActivePolicy().getPolicyContent().get(config_id[0]);
      if(mcPolicyValue != null){
          this.messageComposer.getPolicyManager().getActivePolicy().getPolicyContent().put(config_id[0], mcPolicyValue.concat(":").concat(config_id[1]));
      }else{
        this.messageComposer.getPolicyManager().getActivePolicy().getPolicyContent().put(config_id[0],config_id[1]);
      }
    });
  }

  @Override
  public void addElementRecipient(String config, String id, Object o) {
    this.recipients.put(id, o);
    this.sender.addRecipient(id, o);
    //modify policies
    this.sender.getPolicyManager().getActivePolicy().getPolicyContent().put(config.concat("_").concat(id), id);
    String mcPolicyValue = this.messageComposer.getPolicyManager().getActivePolicy().getPolicyContent().get(config);
    if(mcPolicyValue != null){
     this.messageComposer.getPolicyManager().getActivePolicy().getPolicyContent().put(config, mcPolicyValue.concat(":").concat(id));
    }else{
     this.messageComposer.getPolicyManager().getActivePolicy().getPolicyContent().put(config, id);
    }
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

    String mssgInFl = this.p.getPolicyContent().get("mssgInFl");
    String mssgOutFl = this.p.getPolicyContent().get("mssgOutFl");
    String mssgInAl = this.p.getPolicyContent().get("mssgInAl");
    String mssgOutAl = this.p.getPolicyContent().get("mssgOutAl");
    String mssgAdapt = this.p.getPolicyContent().get("mssgAdapt");

    setElementComponentsRecipient(this.getReceiver(), mssgInFl, this.getLogicSelector());
    setElementComponentsRecipient(this.getReceiver(), mssgInAl, this.getLogicSelector());

    setElementComponentsRecipient(this.getLogicSelector(), mssgInFl, this.getFunctionalLogic());
    setElementComponentsRecipient(this.getLogicSelector(), mssgInAl, this.getAdaptationLogic());

    setElementComponentsRecipient(this.getFunctionalLogic(), mssgOutFl, this.getMessageComposer());

    setElementComponentsRecipient(this.getAdaptationLogic(), mssgOutAl, this.getMessageComposer());
    setElementComponentsRecipient(this.getAdaptationLogic(), mssgInAl, this.getKnowledge());

    setElementComponentsRecipient(this.getMessageComposer(), mssgOutFl, this.getSender());
    setElementComponentsRecipient(this.getMessageComposer(), mssgOutAl, this.getSender());

    setElementComponentsRecipient(this.getKnowledge(), mssgAdapt, this.getReceiver());
    setElementComponentsRecipient(this.getKnowledge(), mssgAdapt, this.getLogicSelector());
    setElementComponentsRecipient(this.getKnowledge(), mssgAdapt, this.getFunctionalLogic());
    setElementComponentsRecipient(this.getKnowledge(), mssgAdapt, this.getAdaptationLogic());
    setElementComponentsRecipient(this.getKnowledge(), mssgAdapt, this.getMessageComposer());
    setElementComponentsRecipient(this.getKnowledge(), mssgAdapt, this.getSender());

    this.getKnowledge().getPolicyManager().getActivePolicy().getPolicyContent().put(mssgInAl,
        mssgAdapt);
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
