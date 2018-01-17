package org.loopa.autonomicmanager.test;
/*******************************************************************************
 *  Copyright (c) 2017 Universitat Politécnica de Catalunya (UPC)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *  Contributors:
 *  	Edith Zavala
 *******************************************************************************/
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.loopa.analyzer.Analyzer;
import org.loopa.analyzer.IAnalyzer;
import org.loopa.autonomicmanager.AutonomicManager;
import org.loopa.element.adaptationlogic.AdaptationLogic;
import org.loopa.element.adaptationlogic.IAdaptationLogic;
import org.loopa.element.adaptationlogic.enactor.AdaptationLogicEnactor;
import org.loopa.element.adaptationlogic.enactor.IAdaptationLogicEnactor;
import org.loopa.element.functionallogic.FunctionalLogic;
import org.loopa.element.functionallogic.IFunctionalLogic;
import org.loopa.element.functionallogic.enactor.IFunctionalLogicEnactor;
import org.loopa.element.functionallogic.enactor.analyzer.AnalyzerFunctionalLogicEnactor;
import org.loopa.element.functionallogic.enactor.analyzer.IAnalyzerManager;
import org.loopa.element.functionallogic.enactor.executer.ExecuterFunctionalLogicEnactor;
import org.loopa.element.functionallogic.enactor.executer.IExecuterManager;
import org.loopa.element.functionallogic.enactor.knowledgebase.IKnowledgeBaseManager;
import org.loopa.element.functionallogic.enactor.knowledgebase.KnowledgeBaseFuncionalLogicEnactor;
import org.loopa.element.functionallogic.enactor.monitor.IMonitorManager;
import org.loopa.element.functionallogic.enactor.monitor.MonitorFunctionalLogicEnactor;
import org.loopa.element.functionallogic.enactor.planner.IPlannerManager;
import org.loopa.element.functionallogic.enactor.planner.PlannerFunctionalLogicEnactor;
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
import org.loopa.element.sender.messagesender.MessageSender;
import org.loopa.executer.Executer;
import org.loopa.executer.IExecuter;
import org.loopa.generic.documents.IPolicy;
import org.loopa.generic.documents.Policy;
import org.loopa.generic.documents.managers.IPolicyManager;
import org.loopa.generic.documents.managers.PolicyManager;
import org.loopa.generic.element.component.ILoopAElementComponent;
import org.loopa.knowledgebase.IKnowledgeBase;
import org.loopa.knowledgebase.KnowledgeBase;
import org.loopa.monitor.IMonitor;
import org.loopa.monitor.Monitor;
import org.loopa.planner.IPlanner;
import org.loopa.planner.Planner;

public class AutonomicManagerTest {

	IMonitor m;
	IAnalyzer a;
	IPlanner p;
	IExecuter e;
	IKnowledgeBase kb;

	@Before
	public void initializeMonitor() {
		IPolicy rP = new Policy("receiverPolicy", new HashMap<String, String>());
		IPolicy sP = new Policy("senderPolicy", new HashMap<String, String>());
		IPolicy lsP = new Policy("logicSelectorPolicy", new HashMap<String, String>());
		IPolicy flP = new Policy("functionalLogicPolicy", new HashMap<String, String>());
		IPolicy alP = new Policy("adaptationLogicPolicy", new HashMap<String, String>());
		IPolicy mcP = new Policy("messageComposerPolicy", new HashMap<String, String>());
		IPolicy kP = new Policy("knowledgeManagerPolicy", new HashMap<String, String>());

		IPolicyManager rPM = new PolicyManager(rP);
		IPolicyManager sPM = new PolicyManager(sP);
		IPolicyManager lsPM = new PolicyManager(lsP);
		IPolicyManager flPM = new PolicyManager(flP);
		IPolicyManager alPM = new PolicyManager(alP);
		IPolicyManager mcPM = new PolicyManager(mcP);
		IPolicyManager kPM = new PolicyManager(kP);

		IMessageProcessor rMP = new MessageProcessor();
		IMessageSender sMS = new MessageSender();
		ILogicMessageDispatcher lsMD = new LogicMessageDispatcher();
		IMonitorManager mm = new IMonitorManager() {

			@Override
			public void setConfiguration(Map<String, String> config) {
				// TODO Auto-generated method stub

			}

			@Override
			public void setComponent(ILoopAElementComponent c) {
				// TODO Auto-generated method stub

			}

			@Override
			public void processLogicData(Map<String, String> monData) {
				// TODO Auto-generated method stub

			}

			@Override
			public ILoopAElementComponent getComponent() {
				// TODO Auto-generated method stub
				return null;
			}
		};
		IFunctionalLogicEnactor flE = new MonitorFunctionalLogicEnactor(mm);
		IAdaptationLogicEnactor alE = new AdaptationLogicEnactor();
		IDataFormatter mcDF = new DataFormatter();
		IMessageCreator mcMC = new MessageCreator();
		IAdaptiveKnowledgeManager kAKM = new AdaptiveKnowledgeManager();

		rP.addListerner(rMP);
		sP.addListerner(sMS);
		lsP.addListerner(lsMD);
		flP.addListerner(flE);
		alP.addListerner(alE);
		mcP.addListerner(mcDF);
		mcP.addListerner(mcMC);
		kP.addListerner(kAKM);

		IReceiver r = new Receiver("r", rPM, rMP);
		ISender s = new Sender("s", sPM, sMS);
		ILogicSelector ls = new LogicSelector("ls", lsPM, lsMD);
		IFunctionalLogic fl = new FunctionalLogic("fl", flPM, flE);
		IAdaptationLogic al = new AdaptationLogic("al", alPM, alE);
		IMessageComposer mc = new MessageComposer("mc", mcPM, mcDF, mcMC);
		IKnowledgeManager k = new KnowledgeManager("k", kPM, kAKM);

		m = new Monitor("MonitorTest", r, ls, fl, al, mc, s, k);
	}

	@Before
	public void initializeAnalyzer() {
		IPolicy rP = new Policy("receiverPolicy", new HashMap<String, String>());
		IPolicy sP = new Policy("senderPolicy", new HashMap<String, String>());
		IPolicy lsP = new Policy("logicSelectorPolicy", new HashMap<String, String>());
		IPolicy flP = new Policy("functionalLogicPolicy", new HashMap<String, String>());
		IPolicy alP = new Policy("adaptationLogicPolicy", new HashMap<String, String>());
		IPolicy mcP = new Policy("messageComposerPolicy", new HashMap<String, String>());
		IPolicy kP = new Policy("knowledgeManagerPolicy", new HashMap<String, String>());

		IPolicyManager rPM = new PolicyManager(rP);
		IPolicyManager sPM = new PolicyManager(sP);
		IPolicyManager lsPM = new PolicyManager(lsP);
		IPolicyManager flPM = new PolicyManager(flP);
		IPolicyManager alPM = new PolicyManager(alP);
		IPolicyManager mcPM = new PolicyManager(mcP);
		IPolicyManager kPM = new PolicyManager(kP);

		IMessageProcessor rMP = new MessageProcessor();
		IMessageSender sMS = new MessageSender();
		ILogicMessageDispatcher lsMD = new LogicMessageDispatcher();
		IAnalyzerManager am = new IAnalyzerManager() {

			@Override
			public void setConfiguration(Map<String, String> config) {
				// TODO Auto-generated method stub

			}

			@Override
			public void setComponent(ILoopAElementComponent c) {
				// TODO Auto-generated method stub

			}

			@Override
			public void processLogicData(Map<String, String> monData) {
				// TODO Auto-generated method stub

			}

			@Override
			public ILoopAElementComponent getComponent() {
				// TODO Auto-generated method stub
				return null;
			}
		};
		IFunctionalLogicEnactor flE = new AnalyzerFunctionalLogicEnactor(am);
		IAdaptationLogicEnactor alE = new AdaptationLogicEnactor();
		IDataFormatter mcDF = new DataFormatter();
		IMessageCreator mcMC = new MessageCreator();
		IAdaptiveKnowledgeManager kAKM = new AdaptiveKnowledgeManager();

		rP.addListerner(rMP);
		sP.addListerner(sMS);
		lsP.addListerner(lsMD);
		flP.addListerner(flE);
		alP.addListerner(alE);
		mcP.addListerner(mcDF);
		mcP.addListerner(mcMC);
		kP.addListerner(kAKM);

		IReceiver r = new Receiver("r", rPM, rMP);
		ISender s = new Sender("s", sPM, sMS);
		ILogicSelector ls = new LogicSelector("ls", lsPM, lsMD);
		IFunctionalLogic fl = new FunctionalLogic("fl", flPM, flE);
		IAdaptationLogic al = new AdaptationLogic("al", alPM, alE);
		IMessageComposer mc = new MessageComposer("mc", mcPM, mcDF, mcMC);
		IKnowledgeManager k = new KnowledgeManager("k", kPM, kAKM);

		a = new Analyzer("AnalyzerTest", r, ls, fl, al, mc, s, k);
	}

	@Before
	public void initializePlanner() {
		IPolicy rP = new Policy("receiverPolicy", new HashMap<String, String>());
		IPolicy sP = new Policy("senderPolicy", new HashMap<String, String>());
		IPolicy lsP = new Policy("logicSelectorPolicy", new HashMap<String, String>());
		IPolicy flP = new Policy("functionalLogicPolicy", new HashMap<String, String>());
		IPolicy alP = new Policy("adaptationLogicPolicy", new HashMap<String, String>());
		IPolicy mcP = new Policy("messageComposerPolicy", new HashMap<String, String>());
		IPolicy kP = new Policy("knowledgeManagerPolicy", new HashMap<String, String>());

		IPolicyManager rPM = new PolicyManager(rP);
		IPolicyManager sPM = new PolicyManager(sP);
		IPolicyManager lsPM = new PolicyManager(lsP);
		IPolicyManager flPM = new PolicyManager(flP);
		IPolicyManager alPM = new PolicyManager(alP);
		IPolicyManager mcPM = new PolicyManager(mcP);
		IPolicyManager kPM = new PolicyManager(kP);

		IMessageProcessor rMP = new MessageProcessor();
		IMessageSender sMS = new MessageSender();
		ILogicMessageDispatcher lsMD = new LogicMessageDispatcher();
		IPlannerManager pm = new IPlannerManager() {

			@Override
			public void setConfiguration(Map<String, String> config) {
				// TODO Auto-generated method stub

			}

			@Override
			public void setComponent(ILoopAElementComponent c) {
				// TODO Auto-generated method stub

			}

			@Override
			public void processLogicData(Map<String, String> monData) {
				// TODO Auto-generated method stub

			}

			@Override
			public ILoopAElementComponent getComponent() {
				// TODO Auto-generated method stub
				return null;
			}
		};
		IFunctionalLogicEnactor flE = new PlannerFunctionalLogicEnactor(pm);
		IAdaptationLogicEnactor alE = new AdaptationLogicEnactor();
		IDataFormatter mcDF = new DataFormatter();
		IMessageCreator mcMC = new MessageCreator();
		IAdaptiveKnowledgeManager kAKM = new AdaptiveKnowledgeManager();

		rP.addListerner(rMP);
		sP.addListerner(sMS);
		lsP.addListerner(lsMD);
		flP.addListerner(flE);
		alP.addListerner(alE);
		mcP.addListerner(mcDF);
		mcP.addListerner(mcMC);
		kP.addListerner(kAKM);

		IReceiver r = new Receiver("r", rPM, rMP);
		ISender s = new Sender("s", sPM, sMS);
		ILogicSelector ls = new LogicSelector("ls", lsPM, lsMD);
		IFunctionalLogic fl = new FunctionalLogic("fl", flPM, flE);
		IAdaptationLogic al = new AdaptationLogic("al", alPM, alE);
		IMessageComposer mc = new MessageComposer("mc", mcPM, mcDF, mcMC);
		IKnowledgeManager k = new KnowledgeManager("k", kPM, kAKM);

		p = new Planner("PlannerTest", r, ls, fl, al, mc, s, k);
	}

	@Before
	public void initializeExecuter() {
		IPolicy rP = new Policy("receiverPolicy", new HashMap<String, String>());
		IPolicy sP = new Policy("senderPolicy", new HashMap<String, String>());
		IPolicy lsP = new Policy("logicSelectorPolicy", new HashMap<String, String>());
		IPolicy flP = new Policy("functionalLogicPolicy", new HashMap<String, String>());
		IPolicy alP = new Policy("adaptationLogicPolicy", new HashMap<String, String>());
		IPolicy mcP = new Policy("messageComposerPolicy", new HashMap<String, String>());
		IPolicy kP = new Policy("knowledgeManagerPolicy", new HashMap<String, String>());

		IPolicyManager rPM = new PolicyManager(rP);
		IPolicyManager sPM = new PolicyManager(sP);
		IPolicyManager lsPM = new PolicyManager(lsP);
		IPolicyManager flPM = new PolicyManager(flP);
		IPolicyManager alPM = new PolicyManager(alP);
		IPolicyManager mcPM = new PolicyManager(mcP);
		IPolicyManager kPM = new PolicyManager(kP);

		IMessageProcessor rMP = new MessageProcessor();
		IMessageSender sMS = new MessageSender();
		ILogicMessageDispatcher lsMD = new LogicMessageDispatcher();
		IExecuterManager em = new IExecuterManager() {

			@Override
			public void setConfiguration(Map<String, String> config) {
				// TODO Auto-generated method stub

			}

			@Override
			public void setComponent(ILoopAElementComponent c) {
				// TODO Auto-generated method stub

			}

			@Override
			public void processLogicData(Map<String, String> monData) {
				// TODO Auto-generated method stub

			}

			@Override
			public ILoopAElementComponent getComponent() {
				// TODO Auto-generated method stub
				return null;
			}
		};
		IFunctionalLogicEnactor flE = new ExecuterFunctionalLogicEnactor(em);
		IAdaptationLogicEnactor alE = new AdaptationLogicEnactor();
		IDataFormatter mcDF = new DataFormatter();
		IMessageCreator mcMC = new MessageCreator();
		IAdaptiveKnowledgeManager kAKM = new AdaptiveKnowledgeManager();

		rP.addListerner(rMP);
		sP.addListerner(sMS);
		lsP.addListerner(lsMD);
		flP.addListerner(flE);
		alP.addListerner(alE);
		mcP.addListerner(mcDF);
		mcP.addListerner(mcMC);
		kP.addListerner(kAKM);

		IReceiver r = new Receiver("r", rPM, rMP);
		ISender s = new Sender("s", sPM, sMS);
		ILogicSelector ls = new LogicSelector("ls", lsPM, lsMD);
		IFunctionalLogic fl = new FunctionalLogic("fl", flPM, flE);
		IAdaptationLogic al = new AdaptationLogic("al", alPM, alE);
		IMessageComposer mc = new MessageComposer("mc", mcPM, mcDF, mcMC);
		IKnowledgeManager k = new KnowledgeManager("k", kPM, kAKM);

		e = new Executer("ExecuterTest", r, ls, fl, al, mc, s, k);
	}

	@Before
	public void initializeKB() {
		IPolicy rP = new Policy("receiverPolicy", new HashMap<String, String>());
		IPolicy sP = new Policy("senderPolicy", new HashMap<String, String>());
		IPolicy lsP = new Policy("logicSelectorPolicy", new HashMap<String, String>());
		IPolicy flP = new Policy("functionalLogicPolicy", new HashMap<String, String>());
		IPolicy alP = new Policy("adaptationLogicPolicy", new HashMap<String, String>());
		IPolicy mcP = new Policy("messageComposerPolicy", new HashMap<String, String>());
		IPolicy kP = new Policy("knowledgeManagerPolicy", new HashMap<String, String>());

		IPolicyManager rPM = new PolicyManager(rP);
		IPolicyManager sPM = new PolicyManager(sP);
		IPolicyManager lsPM = new PolicyManager(lsP);
		IPolicyManager flPM = new PolicyManager(flP);
		IPolicyManager alPM = new PolicyManager(alP);
		IPolicyManager mcPM = new PolicyManager(mcP);
		IPolicyManager kPM = new PolicyManager(kP);

		IMessageProcessor rMP = new MessageProcessor();
		IMessageSender sMS = new MessageSender();
		ILogicMessageDispatcher lsMD = new LogicMessageDispatcher();
		IKnowledgeBaseManager kbm = new IKnowledgeBaseManager() {

			@Override
			public void setConfiguration(Map<String, String> config) {
				// TODO Auto-generated method stub

			}

			@Override
			public void setComponent(ILoopAElementComponent c) {
				// TODO Auto-generated method stub

			}

			@Override
			public void processLogicData(Map<String, String> monData) {
				// TODO Auto-generated method stub

			}

			@Override
			public ILoopAElementComponent getComponent() {
				// TODO Auto-generated method stub
				return null;
			}
		};
		IFunctionalLogicEnactor flE = new KnowledgeBaseFuncionalLogicEnactor(kbm);
		IAdaptationLogicEnactor alE = new AdaptationLogicEnactor();
		IDataFormatter mcDF = new DataFormatter();
		IMessageCreator mcMC = new MessageCreator();
		IAdaptiveKnowledgeManager kAKM = new AdaptiveKnowledgeManager();

		rP.addListerner(rMP);
		sP.addListerner(sMS);
		lsP.addListerner(lsMD);
		flP.addListerner(flE);
		alP.addListerner(alE);
		mcP.addListerner(mcDF);
		mcP.addListerner(mcMC);
		kP.addListerner(kAKM);

		IReceiver r = new Receiver("r", rPM, rMP);
		ISender s = new Sender("s", sPM, sMS);
		ILogicSelector ls = new LogicSelector("ls", lsPM, lsMD);
		IFunctionalLogic fl = new FunctionalLogic("fl", flPM, flE);
		IAdaptationLogic al = new AdaptationLogic("al", alPM, alE);
		IMessageComposer mc = new MessageComposer("mc", mcPM, mcDF, mcMC);
		IKnowledgeManager k = new KnowledgeManager("k", kPM, kAKM);

		kb = new KnowledgeBase("KBTest", r, ls, fl, al, mc, s, k);
	}

	@Test
	public void testCreatAutonomicManager() {
		AutonomicManager am = new AutonomicManager(m, a, p, e, kb);
		assertNotNull(am);
	}
}
