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
package org.loopa.autonomicmanager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.loopa.analyzer.IAnalyzer;
import org.loopa.executer.IExecuter;
import org.loopa.generic.element.ILoopAElement;
import org.loopa.knowledgebase.IKnowledgeBase;
import org.loopa.monitor.IMonitor;
import org.loopa.planner.IPlanner;

public class AutonomicManager {

	IMonitor m;
	IAnalyzer a;
	IPlanner p;
	IExecuter e;
	IKnowledgeBase kb;

	public AutonomicManager(IMonitor m, IAnalyzer a, IPlanner p, IExecuter e, IKnowledgeBase kb) {
		super();
		this.m = m;
		this.a = a;
		this.p = p;
		this.e = e;
		this.kb = kb;
		connectLoop();
	}

	/* Through sensor & effectors? */
	public void setME(String id, Object me) {
		this.m.addRecipient(id, me);
		this.e.addRecipient(id, me);
	}

	private void connectLoop() {
		setRecipients(this.m, Arrays.asList(this.a, this.kb));
		setRecipients(this.a, Arrays.asList(this.p, this.kb));
		setRecipients(this.p, Arrays.asList(this.e, this.kb));
		setRecipients(this.e, Arrays.asList(this.kb));
	}

	private void setRecipients(ILoopAElement e, List<ILoopAElement> recipients) {
		Map<String, Object> r = new HashMap<String, Object>();
		recipients.forEach(o -> r.put(o.getElementId(), o));
		e.setElementRecipients(r);
	}

}
