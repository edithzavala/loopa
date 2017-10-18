package org.loopa.autonomicmanager;

import java.util.HashMap;

import org.loopa.analyzer.IAnalyzer;
import org.loopa.executer.IExecuter;
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
	
	/*Through sensor & effectors?*/
	public void setME(String id, Object me) {
		HashMap<String, Object> executerRecipients =  new HashMap<String, Object>();
		executerRecipients.put(id, me);
		HashMap<String, Object> monitorRecipients =  new HashMap<String, Object>();
		monitorRecipients.put(id, me);
	}
	
	private void connectLoop() {
		HashMap<String, Object> monitorRecipients =  new HashMap<String, Object>();
		monitorRecipients.put(this.a.getElementId(), this.a);
		monitorRecipients.put(this.kb.getElementId(), this.kb);
		
		HashMap<String, Object> analyzerRecipients =  new HashMap<String, Object>();
		analyzerRecipients.put(this.p.getElementId(), this.p);
		analyzerRecipients.put(this.kb.getElementId(), this.kb);
		
		HashMap<String, Object> plannerRecipients =  new HashMap<String, Object>();
		plannerRecipients.put(this.e.getElementId(), this.e);
		plannerRecipients.put(this.kb.getElementId(), this.kb);
		
		HashMap<String, Object> executerRecipients =  new HashMap<String, Object>();
		executerRecipients.put(this.kb.getElementId(), this.kb);
				
		m.setElementRecipients(monitorRecipients);
		a.setElementRecipients(analyzerRecipients);
		p.setElementRecipients(plannerRecipients);
		e.setElementRecipients(executerRecipients);
	}
	
}
