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
 
package org.loopa.generic.documents.managers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.loopa.comm.observerobervable.IObservable;
import org.loopa.comm.observerobervable.IObserver;
import org.loopa.generic.documents.IPolicy;

public abstract class AObservablePolicyManager implements IPolicyManager, IObservable {

	private IPolicy policy;
	private List<IObserver> policyObservers;

	public AObservablePolicyManager(IPolicy policy, List<IObserver> policyObservers) {
		super();
		this.policy = policy;
		this.policyObservers = policyObservers;
	}

	@Override
	public void processPolicy(IPolicy p) {
		updatePolicy(p);
		notifyChange();

	}
	
	public abstract void updatePolicy(IPolicy p);

	public void addObserver(IObserver observer) {
		this.policyObservers.add(observer);
	}

	public void removeObserver(IObserver observer) {
		this.policyObservers.remove(observer);

	}

	public void notifyChange() {
		List<IObserver> copyPolicyObservers;
		synchronized (this) {
			copyPolicyObservers = new ArrayList<IObserver>(this.policyObservers);
		}
		Iterator<IObserver> i = copyPolicyObservers.iterator();
		while (i.hasNext()) {
			i.next().notified(this.policy);
		}

	}

	public IPolicy getPolicy() {
		return policy;
	}

	public void setPolicy(IPolicy policy) {
		this.policy = policy;
	}

	public List<IObserver> getPolicyObservers() {
		return policyObservers;
	}

	public void setPolicyObservers(List<IObserver> policyObservers) {
		this.policyObservers = policyObservers;
	}

}
