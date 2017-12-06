
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
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.loopa.comm.message.IMessage;
import org.loopa.comm.message.Message;
import org.loopa.element.adaptationlogic.AdaptationLogic;
import org.loopa.element.adaptationlogic.IAdaptationLogic;
import org.loopa.element.adaptationlogic.enactor.AdaptationLogicEnactor;
import org.loopa.element.adaptationlogic.enactor.IAdaptationLogicEnactor;
import org.loopa.element.functionallogic.FunctionalLogic;
import org.loopa.element.functionallogic.IFunctionalLogic;
import org.loopa.element.functionallogic.enactor.IFunctionalLogicEnactor;
import org.loopa.element.functionallogic.enactor.monitor.IMonitorManager;
import org.loopa.element.functionallogic.enactor.monitor.MonitorFunctionalLogicEnactor;
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
import org.loopa.generic.documents.IPolicy;
import org.loopa.generic.documents.Policy;
import org.loopa.generic.documents.managers.IPolicyManager;
import org.loopa.generic.documents.managers.PolicyManager;
import org.loopa.generic.element.component.ILoopAElementComponent;
import org.loopa.monitor.IMonitor;
import org.loopa.monitor.Monitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MonitorTest {

	IReceiver r;
	ISender s;
	ILogicSelector ls;
	IFunctionalLogic fl;
	IAdaptationLogic al;
	IMessageComposer mc;
	IKnowledgeManager k;
	Logger logger;

	@Before
	public void initializeComponents() {
		logger = LoggerFactory.getLogger(MonitorTest.class);

		Map<String, String> rPContent = new HashMap<String, String>();
		Map<String, String> sPContent = new HashMap<String, String>();
		Map<String, String> lsPContent = new HashMap<String, String>();
		Map<String, String> flPContent = new HashMap<String, String>();
		Map<String, String> alPContent = new HashMap<String, String>();
		Map<String, String> mcPContent = new HashMap<String, String>();
		Map<String, String> kPContent = new HashMap<String, String>();

		/* Policy content example for processing messages of type "logic" */
		rPContent.put("1", "ls");
		sPContent.put("1", "recipientX");
		/**
		 * This policy could be used for deciding in the sender what to do with the
		 * Message received from the MessageComposer
		 * 		sPContent.put("getMonData","recipientX");
		 ********************************************************/
		lsPContent.put("1", "fl");
		flPContent.put("1", "mc");
		// alPContent.put(key, value);
		mcPContent.put("1", "s");
		mcPContent.put("getMonData", "recipientX");
		// kPContent.put(key, value);
		/**/

		IPolicy rP = new Policy("receiverPolicy", rPContent);
		IPolicy sP = new Policy("senderPolicy", sPContent);
		IPolicy lsP = new Policy("logicSelectorPolicy", lsPContent);
		IPolicy flP = new Policy("functionalLogicPolicy", flPContent);
		IPolicy alP = new Policy("adaptationLogicPolicy", alPContent);
		IPolicy mcP = new Policy("messageComposerPolicy", mcPContent);
		IPolicy kP = new Policy("knowledgeManagerPolicy", kPContent);

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
			/**Example of how methods can be override for the MonitorManager to work*/
			private Map<String, String> config = new HashMap<String, String>();
			private ILoopAElementComponent component;

			@Override
			public void setConfiguration(Map<String, String> config) {
				this.config = config;

			}

			@Override
			public void setComponent(ILoopAElementComponent c) {
				this.component = c;
			}

			@Override
			public ILoopAElementComponent getComponent() {
				return this.component;
			}
			/************************************************************************/
			
			@Override
			public void processLogicData(Map<String, String> monData) {

				/** Receive mon data */
				logger.info("" + monData);

				/** Example of requesting monitoring data (do idem for sending response Messages when required) */
				// ----Creat Message
				Map<String, String> body = new HashMap<String, String>();

				// At this point a pair with key "type" is mandatory for the MessageComposer to
				// operate correctly
				body.put("type", "getMonData");

				IMessage mRequestMonData = new Message(this.getComponent().getComponentId(), config.get("1"), 1,
						"request", body);
				// ----
				//Send Message
				ILoopAElementComponent r = (ILoopAElementComponent) this.getComponent().getComponentRecipients()
						.get(mRequestMonData.getMessageTo());
				r.doOperation(mRequestMonData);
				/***************************************/

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

		r = new Receiver("r", rPM, rMP);
		s = new Sender("s", sPM, sMS);
		ls = new LogicSelector("ls", lsPM, lsMD);
		fl = new FunctionalLogic("fl", flPM, flE);
		al = new AdaptationLogic("al", alPM, alE);
		mc = new MessageComposer("mc", mcPM, mcDF, mcMC);
		k = new KnowledgeManager("k", kPM, kAKM);
	}

	@Test
	public void testCreateMonitor() {
		IMonitor m = new Monitor("MonitorTest", r, ls, fl, al, mc, s, k);
		assertNotNull(m);
	}

	@Test
	public void testDoLogicOperationMonitor() {
		IMonitor m = new Monitor("MonitorTest", r, ls, fl, al, mc, s, k);

		/*
		 * Add recipient to the Monitor (id, object) - the HashMap is exemplifying an
		 * object
		 */
		m.addRecipient("recipientX", new HashMap<String, String>());
		/**/

		/* Create some of the Message elements */
		int code = 1;
		Map<String, String> body = new HashMap<String, String>();
		body.put("ObtainedDataAttr1_name", "ObtainedDataAttr1_value");

		/* send a Message to the Monitor */
		m.getReceiver().doOperation(new Message("recipientX", "r", code, "response", body));
	}

}
