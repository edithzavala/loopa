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
package org.loopa.generic.element;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
import org.loopa.policy.IPolicy;
import org.loopa.recipient.IRecipient;
import org.loopa.recipient.Recipient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ALoopElement implements ILoopAElement {
  protected final Logger LOGGER = LoggerFactory.getLogger(getClass().getName());

  private final String id;
  private IReceiver receiver;
  private ILogicSelector logicSelector;
  private IFunctionalLogic functionalLogic;
  private IAdaptationLogic adaptationLogic;
  private IMessageComposer messageComposer;
  private ISender sender;
  private IKnowledgeManager knowledge;

  private Map<String, Recipient> recipients;
  private IPolicy policy;

  /* Simplified constructor */
  protected ALoopElement(String elementId, IPolicy elementPolicy, IFunctionalLogicEnactor flE,
      IMessageSender sMS) {
    this.id = elementId;
    this.policy = elementPolicy;

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

    this.recipients = new HashMap<String, Recipient>();
    LOGGER.info("Element created");
  }

  /* Verbose constructor */
  protected ALoopElement(String elementId, IPolicy elementPolicy, IReceiver receiver,
      ILogicSelector logicSelector, IFunctionalLogic functionalLogic,
      IAdaptationLogic adaptationLogic, IMessageComposer messageComposer, ISender sender,
      IKnowledgeManager knowledge) {
    super();
    this.id = elementId;
    this.policy = elementPolicy;

    this.receiver = receiver;
    this.logicSelector = logicSelector;
    this.functionalLogic = functionalLogic;
    this.adaptationLogic = adaptationLogic;
    this.messageComposer = messageComposer;
    this.sender = sender;
    this.knowledge = knowledge;

    this.recipients = new HashMap<String, Recipient>();
    LOGGER.info("Element created");
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

    String mssgInFl = this.policy.getPolicyContent().get("mssgInFl");
    String mssgOutFl = this.policy.getPolicyContent().get("mssgOutFl");
    String mssgInAl = this.policy.getPolicyContent().get("mssgInAl");
    String mssgOutAl = this.policy.getPolicyContent().get("mssgOutAl");
    String mssgAdapt = this.policy.getPolicyContent().get("mssgAdapt");

    this.getReceiver().addRecipient(new Recipient(this.getLogicSelector().getComponentId(),
        Arrays.asList(mssgInFl, mssgInAl), this.getLogicSelector()));
    this.getLogicSelector().addRecipient(new Recipient(this.getFunctionalLogic().getComponentId(),
        Arrays.asList(mssgInFl), this.getFunctionalLogic()));
    this.getLogicSelector().addRecipient(new Recipient(this.getAdaptationLogic().getComponentId(),
        Arrays.asList(mssgInAl), this.getAdaptationLogic()));
    this.getFunctionalLogic().addRecipient(new Recipient(this.getMessageComposer().getComponentId(),
        Arrays.asList(mssgOutFl), this.getMessageComposer()));
    this.getAdaptationLogic().addRecipient(new Recipient(this.getMessageComposer().getComponentId(),
        Arrays.asList(mssgOutAl), this.getMessageComposer()));
    this.getAdaptationLogic().addRecipient(new Recipient(this.getKnowledge().getComponentId(),
        Arrays.asList(mssgInAl), this.getKnowledge()));
    this.getMessageComposer().addRecipient(new Recipient(this.getSender().getComponentId(),
        Arrays.asList(mssgOutFl, mssgOutAl), this.getSender()));
    this.getKnowledge().addRecipient(new Recipient(this.getReceiver().getComponentId(),
        Arrays.asList(mssgOutFl, mssgOutAl), this.getReceiver()));
    this.getKnowledge().addRecipient(new Recipient(this.getLogicSelector().getComponentId(),
        Arrays.asList(mssgOutFl, mssgOutAl), this.getLogicSelector()));
    this.getKnowledge().addRecipient(new Recipient(this.getFunctionalLogic().getComponentId(),
        Arrays.asList(mssgOutFl, mssgOutAl), this.getFunctionalLogic()));
    this.getKnowledge().addRecipient(new Recipient(this.getAdaptationLogic().getComponentId(),
        Arrays.asList(mssgOutFl, mssgOutAl), this.getAdaptationLogic()));
    this.getKnowledge().addRecipient(new Recipient(this.getMessageComposer().getComponentId(),
        Arrays.asList(mssgOutFl, mssgOutAl), this.getMessageComposer()));
    this.getKnowledge().addRecipient(new Recipient(this.getSender().getComponentId(),
        Arrays.asList(mssgOutFl, mssgOutAl), this.getSender()));

    this.knowledge.mapAdaptMssg(mssgInAl, mssgAdapt);
    LOGGER.info("Element started");
  }

  /** Recipients interface **/

  @Override
  public void setElementRecipients(List<IRecipient> recipients) {
    // Set recipients
    this.recipients = recipients.stream().collect(Collectors
        .toMap(recipient -> recipient.getrecipientId(), recipient -> (Recipient) recipient));

    List<IRecipient> recipientsModified = new ArrayList<IRecipient>();
    this.recipients.forEach((rId, recipient) -> {
      recipientsModified.add(modifySenderRecipientDataType(recipient));
      this.messageComposer.addMEToPolicies(recipient.getrecipientId(), recipient.getTypeOfData());
    });
    this.sender.setComponentRecipients(recipientsModified);
    LOGGER.info("Recipients set");
  }

  @Override
  public void addElementRecipient(IRecipient r) {
    // Set recipient
    this.recipients.put(r.getrecipientId(), (Recipient) r);
    this.sender.addRecipient(modifySenderRecipientDataType(r));
    this.messageComposer.addMEToPolicies(r.getrecipientId(), r.getTypeOfData());
    LOGGER.info("Recipient set");
  }

  private IRecipient modifySenderRecipientDataType(IRecipient r) {
    List<String> dataTypeMidified = new ArrayList<String>();
    ((Recipient) r).getTypeOfData()
        .forEach(dataType -> dataTypeMidified.add(dataType + "_" + r.getrecipientId()));
    return new Recipient(r.getrecipientId(), dataTypeMidified, r.getRecipient());
  }

  @Override
  public List<IRecipient> getElementRecipients() {
    return new ArrayList<>(this.recipients.values());
  }

  @Override
  public IRecipient getElementRecipients(String id) {
    return this.recipients.get(id);
  }

  @Override
  public void removeElementRecipient(String id) {
    this.recipients.remove(id);
    this.sender.removeRecipient(id);
    /** TO-DO Remove Message processor policy **/
    LOGGER.info("Recipient removed");
  }

  /** Getters and setters **/

  @Override
  public String getElementId() {
    return this.id;
  }

  @Override
  public IReceiver getReceiver() {
    return receiver;
  }

  @Override
  public ILogicSelector getLogicSelector() {
    return logicSelector;
  }

  @Override
  public IFunctionalLogic getFunctionalLogic() {
    return functionalLogic;
  }

  @Override
  public IAdaptationLogic getAdaptationLogic() {
    return adaptationLogic;
  }

  @Override
  public IMessageComposer getMessageComposer() {
    return messageComposer;
  }

  @Override
  public ISender getSender() {
    return sender;
  }

  @Override
  public IKnowledgeManager getKnowledge() {
    return knowledge;
  }

  @Override
  public void setElementId(String id) {
    // this.id = id;
  }

  @Override
  public void setReceiver(IReceiver receiver) {
    this.receiver = receiver;
    LOGGER.info("Receiver set");
  }

  @Override
  public void setLogicSelector(ILogicSelector logicSelector) {
    this.logicSelector = logicSelector;
    LOGGER.info("LogicSelector set");
  }

  @Override
  public void setFunctionalLogic(IFunctionalLogic functionalLogic) {
    this.functionalLogic = functionalLogic;
    LOGGER.info("FunctionalLogic set");
  }

  @Override
  public void setAdaptationLogic(IAdaptationLogic adaptationLogic) {
    this.adaptationLogic = adaptationLogic;
    LOGGER.info("AdaptationLogic set");
  }

  @Override
  public void setMessageComposer(IMessageComposer messageComposer) {
    this.messageComposer = messageComposer;
    LOGGER.info("MessageComposer set");
  }

  @Override
  public void setSender(ISender sender) {
    this.sender = sender;
    LOGGER.info("Sender set");
  }

  @Override
  public void setKnowledge(IKnowledgeManager knowledge) {
    this.knowledge = knowledge;
    LOGGER.info("KnowledgeManager set");
  }

}
