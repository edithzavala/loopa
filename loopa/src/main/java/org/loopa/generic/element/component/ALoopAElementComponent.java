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

package org.loopa.generic.element.component;

import java.util.HashMap;
import java.util.Map;
import org.loopa.comm.message.IMessage;
import org.loopa.generic.documents.Policy;
import org.loopa.generic.documents.managers.IPolicyManager;
import org.loopa.generic.documents.managers.PolicyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.reactivex.subjects.PublishSubject;

public abstract class ALoopAElementComponent implements ILoopAElementComponent {

  private IMessageManager messageManager;
  private IPolicyManager policyManager;
  private PublishSubject<IMessage> adaptMssgQueue;
  private PublishSubject<IMessage> opeMssgQueue;
  private String id;
  private Map<String, Object> recipients;
  private Logger logger;

  protected ALoopAElementComponent(String id, IPolicyManager pm, IMessageManager imm) { // old_constructor
    super();
    this.id = id;
    this.policyManager = pm;
    this.messageManager = imm;
    this.opeMssgQueue = PublishSubject.create();
    this.adaptMssgQueue = PublishSubject.create();
    this.recipients = new HashMap<String, Object>();
    this.logger = LoggerFactory.getLogger(ALoopAElementComponent.class);
    start();
  }

  protected ALoopAElementComponent(String id, IMessageManager imm) {
    super();
    this.id = id;
    this.policyManager =
        new PolicyManager(new Policy(id + "Policy", new HashMap<String, String>()));
    this.messageManager = imm;
    this.opeMssgQueue = PublishSubject.create();
    this.adaptMssgQueue = PublishSubject.create();
    this.recipients = new HashMap<String, Object>();
    this.logger = LoggerFactory.getLogger(ALoopAElementComponent.class);

  }

  @Override
  public void start() {
    this.policyManager.setComponent(this);
    this.messageManager.setComponent(this);
    this.opeMssgQueue.subscribe(m -> this.messageManager.processMessage(m));
    this.adaptMssgQueue.subscribe(t -> this.policyManager.processPolicy(t));
    this.policyManager.getActivePolicy().addListerner(this.getMessageManager());
    this.policyManager.getActivePolicy().notifyPolicy();
  }

  @Override
  public void adapt(IMessage m) {
    adaptMssgQueue.onNext(m);
  }

  @Override
  public void doOperation(IMessage m) {
    opeMssgQueue.onNext(m);
  }

  @Override
  public void setComponentRecipients(Map<String, Object> r) {
    this.recipients = r;
  }

  @Override
  public Map<String, Object> getComponentRecipients() {
    return this.recipients;
  }

  @Override
  public void addRecipient(String id, Object o) {
    this.recipients.put(id, o);
  }

  @Override
  public void removeRecipient(String id) {
    this.recipients.remove(id);
  }

  @Override
  public String getComponentId() {
    return this.id;
  }

  @Override
  public IPolicyManager getPolicyManager() {
    return policyManager;
  }

  @Override
  public void setPolicyManager(IPolicyManager policyManager) {
    this.policyManager = policyManager;
  }

  @Override
  public IMessageManager getMessageManager() {
    return messageManager;
  }

  @Override
  public void setMessageManager(IMessageManager messageManager) {
    this.messageManager = messageManager;
  }

  public PublishSubject<IMessage> getAdaptMssgQueue() {
    return adaptMssgQueue;
  }

  public void setAdaptMssgQueue(PublishSubject<IMessage> adaptMssgQueue) {
    this.adaptMssgQueue = adaptMssgQueue;
  }

  public PublishSubject<IMessage> getOpeMssgQueue() {
    return opeMssgQueue;
  }

  public void setOpeMssgQueue(PublishSubject<IMessage> opeMssgQueue) {
    this.opeMssgQueue = opeMssgQueue;
  }

}
