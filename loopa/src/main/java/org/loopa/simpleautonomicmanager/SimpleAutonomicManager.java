package org.loopa.simpleautonomicmanager;

import java.util.Arrays;
import org.loopa.analyzer.IAnalyzer;
import org.loopa.executer.IExecuter;
import org.loopa.knowledgebase.IKnowledgeBase;
import org.loopa.monitor.IMonitor;
import org.loopa.planner.IPlanner;
import org.loopa.policy.IPolicy;
import org.loopa.recipient.IRecipient;
import org.loopa.recipient.Recipient;

public class SimpleAutonomicManager {
  private IMonitor m;
  private IAnalyzer a;
  private IPlanner p;
  private IExecuter e;
  private IKnowledgeBase kb;

  public SimpleAutonomicManager(IMonitor m, IAnalyzer a, IPlanner p, IExecuter e,
      IKnowledgeBase kb) {
    super();
    this.m = m;
    this.a = a;
    this.p = p;
    this.e = e;
    this.kb = kb;
  }

  public void start() {
    this.m.start();
    this.a.start();
    this.p.start();
    this.e.start();
    this.kb.start();

    /** Make a loop policy for tagging this type of data as desired elementId:typeOfData **/
    this.m.addElementRecipient(
        new Recipient(this.a.getElementId(), Arrays.asList("analysis"), this.a));
    this.m.addElementRecipient(new Recipient(this.kb.getElementId(), Arrays.asList("kb"), this.kb));

    this.a.addElementRecipient(new Recipient(this.p.getElementId(), Arrays.asList("plan"), this.p));
    this.a.addElementRecipient(new Recipient(this.kb.getElementId(), Arrays.asList("kb"), this.kb));

    this.p.addElementRecipient(
        new Recipient(this.e.getElementId(), Arrays.asList("execute"), this.e));
    this.p.addElementRecipient(new Recipient(this.kb.getElementId(), Arrays.asList("kb"), this.kb));

    this.e.addElementRecipient(new Recipient(this.kb.getElementId(), Arrays.asList("kb"), this.kb));

    this.kb.addElementRecipient(
        new Recipient(this.m.getElementId(), Arrays.asList("monitor"), this.m));
    this.kb.addElementRecipient(
        new Recipient(this.a.getElementId(), Arrays.asList("analysis"), this.a));
    this.kb
        .addElementRecipient(new Recipient(this.p.getElementId(), Arrays.asList("plan"), this.p));
    this.kb.addElementRecipient(
        new Recipient(this.e.getElementId(), Arrays.asList("execute"), this.e));

  }

  public void addME(IPolicy loopOpePolicy, IRecipient effector) {
    this.e.addElementRecipient(effector);
    /**
     * Process elements' functional logic policies and send corresponding adapt() message to loop
     * elements
     **/
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
