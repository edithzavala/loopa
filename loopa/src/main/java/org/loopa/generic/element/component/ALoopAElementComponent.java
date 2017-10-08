package org.loopa.generic.element.component;

import org.loopa.documents.IPolicy;
import org.loopa.documents.IPolicyManager;

public abstract class ALoopAElementComponent implements ILoopAElementComponent {
	protected String endpoint;
	protected IPolicy policy;
	protected IPolicyManager policyManager;
	
	@Override
	public void processPolicy(IPolicy p) {
		policyManager.processPolicy(p);
	}
}
