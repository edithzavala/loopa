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
package org.loopa.generic.element.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.loopa.comm.message.IMessage;
import org.loopa.policy.IPolicy;
import org.loopa.policy.Policy;
import org.loopa.policy.manager.IPolicyManager;
import org.loopa.policy.manager.PolicyManager;
import org.loopa.recipient.IRecipient;
import org.loopa.recipient.Recipient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.reactivex.subjects.PublishSubject;

public abstract class ALoopAElementComponent implements ILoopAElementComponent {
  protected final Logger LOGGER = LoggerFactory.getLogger(getClass().getName());

  private final String id;
  private IMessageManager messageManager;
  private IPolicyManager policyManager;
  private PublishSubject<IMessage> adaptMssgQueue;
  private PublishSubject<IMessage> opeMssgQueue;
  private Map<String, Recipient> recipients;

  /* Simplified constructor */
  protected ALoopAElementComponent(String id, IMessageManager imm) {
    super();
    this.id = id;

    this.policyManager =
        new PolicyManager(new Policy(id + "Policy", new HashMap<String, String>()));
    this.messageManager = imm;
    this.opeMssgQueue = PublishSubject.create();
    this.adaptMssgQueue = PublishSubject.create();

    this.recipients = new HashMap<String, Recipient>();
    LOGGER.info("Component created");
  }

  /* Verbose constructor */
  protected ALoopAElementComponent(String id, IPolicyManager pm, IMessageManager imm) {
    super();
    this.id = id;

    this.policyManager = pm;
    this.messageManager = imm;
    this.opeMssgQueue = PublishSubject.create();
    this.adaptMssgQueue = PublishSubject.create();
    this.recipients = new HashMap<String, Recipient>();

    LOGGER.info("Component created");
  }

  @Override
  public void start() {
    this.policyManager.setComponent(this);
    this.messageManager.setComponent(this);
    this.opeMssgQueue.subscribe(mOpe -> this.messageManager.processMessage(mOpe));
    this.adaptMssgQueue.subscribe(mAdapt -> this.policyManager.processPolicy(mAdapt));
    this.policyManager.getActivePolicy().addListerner(this.getMessageManager());
    this.policyManager.getActivePolicy().notifyPolicy();
    LOGGER.info("Component started");
  }

  /** Message management **/

  @Override
  public void adapt(IMessage m) {
    adaptMssgQueue.onNext(m);
    LOGGER.info("Adaptation message processed");
  }

  @Override
  public void doOperation(IMessage m) {
    opeMssgQueue.onNext(m);
    LOGGER.info("Operational message processed");
  }

  /** Recipients interface **/

  @Override
  public void setComponentRecipients(List<IRecipient> recipients) {
    this.recipients = recipients.stream().collect(Collectors.toMap(
        recipient -> ((Recipient) recipient).getrecipientId(), recipient -> (Recipient) recipient));
    this.recipients.forEach((rId, recipient) -> addRecipientToPolicy(recipient));
    LOGGER.info("Recepients set");
  }


  @Override
  public void addRecipient(IRecipient r) {
    this.recipients.put(r.getrecipientId(), (Recipient) r);
    addRecipientToPolicy(r);
    LOGGER.info("Recepient added");
  }

  private void addRecipientToPolicy(IRecipient r) {
    IPolicy tempP = new Policy(this.id, new HashMap<String, String>());
    r.getTypeOfData().forEach(dataType -> {
      tempP.getPolicyContent().put(dataType,
          (this.getPolicyManager().getActivePolicy().getPolicyContent().get(dataType)) != null
              ? this.getPolicyManager().getActivePolicy().getPolicyContent().get(dataType) + ":"
                  + r.getrecipientId()
              : r.getrecipientId());
    });
    this.getPolicyManager().getActivePolicy().update(tempP);
  }

  @Override
  public List<IRecipient> getComponentRecipients() {
    return new ArrayList<>(this.recipients.values());
  }

  @Override
  public IRecipient getComponentRecipients(String id) {
    return this.recipients.get(id);
  }

  @Override
  public void removeRecipient(String id) {
    this.recipients.remove(id);
    /** TO-DO Remove recipient from Policies **/
    LOGGER.info("Recepient removed");
  }

  /** Getters and setters **/

  @Override
  public String getComponentId() {
    return this.id;
  }

  @Override
  public IPolicyManager getPolicyManager() {
    return this.policyManager;
  }

  @Override
  public IMessageManager getMessageManager() {
    return this.messageManager;
  }

  public PublishSubject<IMessage> getAdaptMssgQueue() {
    return this.adaptMssgQueue;
  }

  public PublishSubject<IMessage> getOpeMssgQueue() {
    return this.opeMssgQueue;
  }

  @Override
  public void setMessageManager(IMessageManager messageManager) {
    this.messageManager = messageManager;
    LOGGER.info("MessageManager set");
  }

  @Override
  public void setPolicyManager(IPolicyManager policyManager) {
    this.policyManager = policyManager;
    LOGGER.info("PolicyManager set");
  }

  public void setAdaptMssgQueue(PublishSubject<IMessage> adaptMssgQueue) {
    this.adaptMssgQueue = adaptMssgQueue;
    LOGGER.info("AdaptMssgQueue set");
  }

  public void setOpeMssgQueue(PublishSubject<IMessage> opeMssgQueue) {
    this.opeMssgQueue = opeMssgQueue;
    LOGGER.info("OpeMssgQueue set");
  }

}
