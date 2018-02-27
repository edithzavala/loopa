/*******************************************************************************
 * Copyright (c) 2018 Universitat Politécnica de Catalunya (UPC)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Contributors: Edith Zavala
 ******************************************************************************/
package org.loopa.policy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public abstract class APolicy implements IPolicy {
	private String policyOwner;
	private Map<String, String> policyContent;
	private List<IPolicyChangeListener> listeners;

	public APolicy(String policyOwner, Map<String, String> policyContent) {
		super();
		this.policyOwner = policyOwner;
		this.policyContent = policyContent;
		this.listeners = new ArrayList<>();
	}

	@Override
	public Map<String, String> getPolicyContent() {
		return this.policyContent;
	}

	@Override
	public String getPolicyOwner() {
		return this.policyOwner;
	}

	@Override
	public void notifyPolicy() {
		notifyListeners(this);
	}

	@Override
	public List<IPolicyChangeListener> getListeners() {
		return listeners;
	}

	@Override
	public void setListeners(List<IPolicyChangeListener> listeners) {
		this.listeners = listeners;
	}

	@Override
	public void addListerner(IPolicyChangeListener pl) {
		this.listeners.add(pl);
	}

	@Override
	public void removeListerner(IPolicyChangeListener pl) {
		this.listeners.remove(pl);
	}

	@Override
	public void update(IPolicy p) {
		notifyListeners(updatePolicy(p));
	}

	public abstract IPolicy updatePolicy(IPolicy p);

	public void notifyListeners(IPolicy p) {
		Observable.just(p).subscribe(t -> {
			for (IPolicyChangeListener pl : this.getListeners())
				pl.listen(t);
		});
	}

	public void setPolicyOwner(String policyOwner) {
		this.policyOwner = policyOwner;
	}

	public void setPolicyContent(Map<String, String> policyContent) {
		this.policyContent = policyContent;
	}

}
