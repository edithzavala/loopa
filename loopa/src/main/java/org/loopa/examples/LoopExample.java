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
package org.loopa.examples;

import java.util.Arrays;
import java.util.HashMap;
import org.loopa.analyzer.Analyzer;
import org.loopa.analyzer.IAnalyzer;
import org.loopa.autonomicmanager.AMElementAdpaptationType;
import org.loopa.autonomicmanager.SimpleAutonomicManager;
import org.loopa.comm.message.AMMessageBodyType;
import org.loopa.comm.message.LoopAElementMessageCode;
import org.loopa.element.functionallogic.enactor.analyzer.AnalyzerFunctionalLogicEnactor;
import org.loopa.element.functionallogic.enactor.executer.ExecuterFunctionalLogicEnactor;
import org.loopa.element.functionallogic.enactor.knowledgebase.KnowledgeBaseFuncionalLogicEnactor;
import org.loopa.element.functionallogic.enactor.monitor.MonitorFunctionalLogicEnactor;
import org.loopa.element.functionallogic.enactor.planner.PlannerFunctionalLogicEnactor;
import org.loopa.executer.Executer;
import org.loopa.executer.IExecuter;
import org.loopa.knowledgebase.IKnowledgeBase;
import org.loopa.knowledgebase.KnowledgeBase;
import org.loopa.monitor.IMonitor;
import org.loopa.monitor.Monitor;
import org.loopa.monitor.sensor.ISensor;
import org.loopa.planner.IPlanner;
import org.loopa.planner.Planner;
import org.loopa.policy.IPolicy;
import org.loopa.policy.Policy;
import org.loopa.recipient.Recipient;

public class LoopExample {

  public static void main(String[] args) {

    /** Configuration **/
    IPolicy loopaElementsMessMngmtPolicy =
        new Policy("loopaElements", new HashMap<String, String>() {
          {
            // TODO get codes from a config file
            put(LoopAElementMessageCode.MSSGINFL.toString(), "1");
            put(LoopAElementMessageCode.MSSGINAL.toString(), "2");
            put(LoopAElementMessageCode.MSSGADAPT.toString(), "3");
            put(LoopAElementMessageCode.MSSGOUTFL.toString(), "4");
            put(LoopAElementMessageCode.MSSGOUTAL.toString(), "5");
          }
        });

    // TODO get ids from a config file
    IMonitor m = new Monitor("Monitor", loopaElementsMessMngmtPolicy,
        new MonitorFunctionalLogicEnactor(new MonitorFleManager()), new ExampleMessageSender());
    IAnalyzer a = new Analyzer("Analyzer", loopaElementsMessMngmtPolicy,
        new AnalyzerFunctionalLogicEnactor(new AnalyzerFleManager()), new ExampleMessageSender());
    IPlanner p = new Planner("Planner", loopaElementsMessMngmtPolicy,
        new PlannerFunctionalLogicEnactor(new PlannerFleManager()), new ExampleMessageSender());
    IExecuter e = new Executer("Executer", loopaElementsMessMngmtPolicy,
        new ExecuterFunctionalLogicEnactor(new ExecuterFleManager()),
        new ExampleExecuterMessageSender());
    IKnowledgeBase kb = new KnowledgeBase("Kb", loopaElementsMessMngmtPolicy,
        new KnowledgeBaseFuncionalLogicEnactor(new KnowledgeBaseFleManager()),
        new ExampleMessageSender());

    IPolicy amPolicy = new Policy("autonomicManagerPolicy", new HashMap<String, String>() {
      {
        put(m.getElementId(), AMMessageBodyType.MONITOR.toString());
        put(a.getElementId(), AMMessageBodyType.ANALYZE.toString());
        put(p.getElementId(), AMMessageBodyType.PLAN.toString());
        put(e.getElementId(), AMMessageBodyType.EXECUTE.toString());
        put(kb.getElementId(), AMMessageBodyType.KB.toString());
      }
    });


    /** Usage **/
    SimpleAutonomicManager loopa =
        new SimpleAutonomicManager("autonomicManager", amPolicy, m, a, p, e, kb);
    loopa.start();

    // TODO Add other type of data for supporting an extended formatter
    loopa.addME(new Policy("loopa", new HashMap<>()), new Recipient("effector",
        Arrays.asList(AMMessageBodyType.ADAPTATION.toString()), new Effector()));

    // Example of sending a message with sensor data to the loop
    ISensor s = new Sensor(loopa.getMonitor());
    s.processSensorData("me1", new HashMap<String, String>());

    // Example of sending a message with policy adaptation to the loop
    loopa.AdaptLoopElement("ksam", loopa.getMonitor().getElementId(),
        AMElementAdpaptationType.RECEIVER, "var1:value1,var2:value2,var3");
  }

}
