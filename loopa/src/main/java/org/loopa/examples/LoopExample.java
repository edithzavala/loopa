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
import java.util.Map;
import org.loopa.analyzer.Analyzer;
import org.loopa.analyzer.IAnalyzer;
import org.loopa.comm.message.IMessage;
import org.loopa.comm.message.Message;
import org.loopa.element.functionallogic.enactor.IFunctionalLogicEnactor;
import org.loopa.element.functionallogic.enactor.analyzer.AnalyzerFunctionalLogicEnactor;
import org.loopa.element.functionallogic.enactor.executer.ExecuterFunctionalLogicEnactor;
import org.loopa.element.functionallogic.enactor.knowledgebase.KnowledgeBaseFuncionalLogicEnactor;
import org.loopa.element.functionallogic.enactor.monitor.MonitorFunctionalLogicEnactor;
import org.loopa.element.functionallogic.enactor.planner.PlannerFunctionalLogicEnactor;
import org.loopa.element.sender.messagesender.IMessageSender;
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
import org.loopa.simpleautonomicmanager.SimpleAutonomicManager;

public class LoopExample {

  public static void main(String[] args) {
    IMessageSender monitorMS = new ExampleMessageSender();
    IMessageSender analyzerMS = new ExampleMessageSender();
    IMessageSender plannerMS = new ExampleMessageSender();
    IMessageSender executerMS = new ExampleExecuterMessageSender();
    IMessageSender kbMS = new ExampleMessageSender();

    IFunctionalLogicEnactor monitorFle = new MonitorFunctionalLogicEnactor(new MonitorFleManager());
    IFunctionalLogicEnactor analyzerFle =
        new AnalyzerFunctionalLogicEnactor(new AnalyzerFleManager());
    IFunctionalLogicEnactor plannerFle = new PlannerFunctionalLogicEnactor(new PlannerFleManager());
    IFunctionalLogicEnactor executerFle =
        new ExecuterFunctionalLogicEnactor(new ExecuterFleManager());
    IFunctionalLogicEnactor kbFle =
        new KnowledgeBaseFuncionalLogicEnactor(new KnowledgeBaseFleManager());

    IPolicy loopaElementsMessMngmtPolicy =
        new Policy("loopaElements", new HashMap<String, String>() {
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

    IMonitor m = new Monitor("Monitor", loopaElementsMessMngmtPolicy, monitorFle, monitorMS);
    IAnalyzer a = new Analyzer("Analyzer", loopaElementsMessMngmtPolicy, analyzerFle, analyzerMS);
    IPlanner p = new Planner("Planner", loopaElementsMessMngmtPolicy, plannerFle, plannerMS);
    IExecuter e = new Executer("Executer", loopaElementsMessMngmtPolicy, executerFle, executerMS);
    IKnowledgeBase kb = new KnowledgeBase("Kb", loopaElementsMessMngmtPolicy, kbFle, kbMS);

    SimpleAutonomicManager loopa = new SimpleAutonomicManager(m, a, p, e, kb);
    loopa.start();

    loopa.addME(new Policy("loopa", new HashMap<>()),
        new Recipient("effector", Arrays.asList("adaptation"), new Effector()));

    ISensor s = new Sensor(loopa.getMonitor());

    IMessage mssg = new Message("me1", loopa.getMonitor().getElementId(), 1, "response",
        new HashMap<String, String>());

    s.processSensorData(mssg);

    Map<String, String> messageContent = new HashMap<String, String>();
    messageContent.put("policyOwner", loopa.getMonitor().getReceiver().getComponentId());
    messageContent.put("policyContent", "var1:value1");
    IMessage mssgAdapt =
        new Message("ksam", loopa.getMonitor().getElementId(), 2, "request", messageContent);

    loopa.getMonitor().getReceiver().doOperation(mssgAdapt);
  }

}
