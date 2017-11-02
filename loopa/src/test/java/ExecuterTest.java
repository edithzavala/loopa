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
import org.loopa.element.adaptationlogic.AdaptationLogic;
import org.loopa.element.adaptationlogic.IAdaptationLogic;
import org.loopa.element.adaptationlogic.enactor.AdaptationLogicEnactor;
import org.loopa.element.adaptationlogic.enactor.IAdaptationLogicEnactor;
import org.loopa.element.functionallogic.FunctionalLogic;
import org.loopa.element.functionallogic.IFunctionalLogic;
import org.loopa.element.functionallogic.enactor.IFunctionalLogicEnactor;
import org.loopa.element.functionallogic.enactor.executer.ExecuterFunctionalLogicEnactor;
import org.loopa.element.functionallogic.enactor.executer.IExecuterManager;
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

public class ExecuterTest {
	IReceiver r;
	ISender s;
	ILogicSelector ls;
	IFunctionalLogic fl;
	IAdaptationLogic al;
	IMessageComposer mc;
	IKnowledgeManager k;

	@Before
	public void initializeComponents() {
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

		r = new Receiver("r", rPM, rMP);
		s = new Sender("s", sPM, sMS);
		ls = new LogicSelector("ls", lsPM, lsMD);
		fl = new FunctionalLogic("fl", flPM, flE);
		al = new AdaptationLogic("al", alPM, alE);
		mc = new MessageComposer("mc", mcPM, mcDF, mcMC);
		k = new KnowledgeManager("k", kPM, kAKM);
	}

	@Test
	public void testCreateExecuter() {
		IExecuter e = new Executer("ExecuterTest", r, ls, fl, al, mc, s, k);
		assertNotNull(e);
	}
}
