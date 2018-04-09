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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import org.loopa.comm.message.IMessage;
import org.loopa.generic.element.ILoopAElement;
import org.loopa.policy.IPolicy;
import org.loopa.policy.Policy;
import org.loopa.policy.manager.IPolicyManager;
import org.loopa.policy.manager.PolicyManager;
import org.loopa.recipient.IRecipient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.reactivex.subjects.PublishSubject;

public abstract class ALoopAElementComponent implements ILoopAElementComponent {
  protected final Logger LOGGER = LoggerFactory.getLogger(getClass().getName());

  private final String id;
  private ILoopAElement element;
  private IMessageManager messageManager;
  private IPolicyManager policyManager;
  private PublishSubject<IMessage> adaptMssgQueue;
  private PublishSubject<IMessage> opeMssgQueue;
  private Map<String, IRecipient> recipients;
  private ExecutorService executorOpe = Executors.newSingleThreadExecutor();
  private ExecutorService executorAdapt = Executors.newSingleThreadExecutor();

  /* Simplified constructor */
  protected ALoopAElementComponent(String id, IMessageManager imm) {
    super();
    // LOGGER.info("create component");
    this.id = id;

    this.policyManager =
        new PolicyManager(new Policy(id + "Policy", new HashMap<String, String>()));
    this.messageManager = imm;
    this.opeMssgQueue = PublishSubject.create();
    this.adaptMssgQueue = PublishSubject.create();

    this.recipients = new HashMap<>();
  }

  /* Verbose constructor */
  protected ALoopAElementComponent(String id, IPolicyManager pm, IMessageManager imm) {
    super();
    // LOGGER.info("create component");
    this.id = id;

    this.policyManager = pm;
    this.messageManager = imm;
    this.opeMssgQueue = PublishSubject.create();
    this.adaptMssgQueue = PublishSubject.create();
    this.recipients = new HashMap<>();
  }

  @Override
  public void construct() {
    // LOGGER.info(this.getElement().getElementId() + " " + this.id + " | start component");
    this.policyManager.setComponent(this);
    this.messageManager.setComponent(this);
    this.opeMssgQueue
        .subscribe(mOpe -> executorOpe.execute(() -> this.messageManager.processMessage(mOpe)));
    this.adaptMssgQueue
        .subscribe(mAdapt -> executorAdapt.execute(() -> this.policyManager.processPolicy(mAdapt)));
    this.policyManager.getActivePolicy().addListerner(this.getMessageManager());
    this.policyManager.getActivePolicy().notifyPolicy();
  }

  /** Message management **/

  @Override
  public void adapt(IMessage m) {
    // LOGGER.info(this.getElement().getElementId() + " " + this.id + " | process adaptation
    // message");
    adaptMssgQueue.onNext(m);
  }

  @Override
  public void doOperation(IMessage m) {
    // LOGGER.info(this.getElement().getElementId() + " " + this.id + " | process operational
    // message");
    opeMssgQueue.onNext(m);
  }

  /** Recipients interface **/

  @Override
  public void setComponentRecipients(List<IRecipient> recipients) {
    // LOGGER.info(this.getElement().getElementId() + " " + this.id + " | set recipients");
    this.recipients = recipients.stream()
        .collect(Collectors.toMap(recipient -> recipient.getrecipientId(), recipient -> recipient));
    this.recipients.forEach((rId, recipient) -> addRecipientToPolicy(recipient));
  }


  @Override
  public void addRecipient(IRecipient r) {
    // LOGGER.info(this.getElement().getElementId() + " " + this.id + " | add recipient");
    this.recipients.put(r.getrecipientId(), r);
    addRecipientToPolicy(r);
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
  public IRecipient getComponentRecipient(String id) {
    return this.recipients.get(id);
  }

  @Override
  public void removeRecipient(String id) {
    // LOGGER.info(this.getElement().getElementId() + " " + this.id + " | remove recipient");
    this.recipients.remove(id);
    /** TO-DO Remove recipient from Policies **/
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
    // LOGGER.info(this.getElement().getElementId() + " " + this.id + " | set PolicyManager");
    this.policyManager = policyManager;
  }

  public void setAdaptMssgQueue(PublishSubject<IMessage> adaptMssgQueue) {
    // LOGGER.info(this.getElement().getElementId() + " " + this.id + " | set AdaptMssgQueue");
    this.adaptMssgQueue = adaptMssgQueue;
  }

  public void setOpeMssgQueue(PublishSubject<IMessage> opeMssgQueue) {
    // LOGGER.info(this.getElement().getElementId() + " " + this.id + " | set OpeMssgQueue");
    this.opeMssgQueue = opeMssgQueue;
  }

  @Override
  public void setElement(ILoopAElement eId) {
    // LOGGER.info("set Element");
    this.element = eId;
  }

  @Override
  public ILoopAElement getElement() {
    return this.element;
  }

}
