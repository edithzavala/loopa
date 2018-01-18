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
package org.loopa.autonomicmanager;

import org.loopa.analyzer.IAnalyzer;
import org.loopa.executer.IExecuter;
import org.loopa.knowledgebase.IKnowledgeBase;
import org.loopa.monitor.IMonitor;
import org.loopa.planner.IPlanner;

public class AutonomicManagerOneElementOfEach {

  IMonitor m;
  IAnalyzer a;
  IPlanner p;
  IExecuter e;
  IKnowledgeBase kb;

  public AutonomicManagerOneElementOfEach(IMonitor m, IAnalyzer a, IPlanner p, IExecuter e,
      IKnowledgeBase kb) {
    super();
    this.m = m;
    this.a = a;
    this.p = p;
    this.e = e;
    this.kb = kb;
  }

  /* Through sensor & effectors? */
  // public void setME(String config, String id, Object me) {
  // this.m.addElementRecipient(config, id, me);
  // this.e.addElementRecipient(config, id, me);
  // }

  public void start() {
    this.m.start();
    this.a.start();
    this.p.start();
    this.e.start();
    this.kb.start();

    /*
     * codes used for distribute messages, e.g., analysis, could be also asked through policies as
     * codes for distributing messages inside elements
     */
    this.m.addElementRecipient("analysis", this.a.getElementId(), this.a);
    this.m.addElementRecipient("kb", this.kb.getElementId(), this.kb);

    this.a.addElementRecipient("plan", this.p.getElementId(), this.p);
    this.a.addElementRecipient("kb", this.kb.getElementId(), this.kb);

    this.p.addElementRecipient("execute", this.e.getElementId(), this.e);
    this.p.addElementRecipient("kb", this.kb.getElementId(), this.kb);

    this.e.addElementRecipient("kb", this.kb.getElementId(), this.kb);

    this.kb.addElementRecipient("monitor", this.m.getElementId(), this.m);
    this.kb.addElementRecipient("analysis", this.a.getElementId(), this.a);
    this.kb.addElementRecipient("plan", this.p.getElementId(), this.p);
    this.kb.addElementRecipient("execute", this.e.getElementId(), this.e);

  }

  public IMonitor getMonitor() {
    return m;
  }

  public void setMonitor(IMonitor m) {
    this.m = m;
  }

  public IAnalyzer getAanalyzer() {
    return a;
  }

  public void setAnalyzer(IAnalyzer a) {
    this.a = a;
  }

  public IPlanner getPlanner() {
    return p;
  }

  public void setPlanner(IPlanner p) {
    this.p = p;
  }

  public IExecuter getExecuter() {
    return e;
  }

  public void setExecuter(IExecuter e) {
    this.e = e;
  }

  public IKnowledgeBase getKb() {
    return kb;
  }

  public void setKb(IKnowledgeBase kb) {
    this.kb = kb;
  }



}