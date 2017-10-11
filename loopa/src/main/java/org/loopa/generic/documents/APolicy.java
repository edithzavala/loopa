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

package org.loopa.generic.documents;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public abstract class APolicy implements IPolicy {
	private String policyType;
	private Map<String, String> policyContent;
	private List<IPolicyChangeListener> listeners;

	public APolicy(String policyType, Map<String, String> policyContent) {
		super();
		this.policyType = policyType;
		this.policyContent = policyContent;
		this.listeners = new ArrayList<>();
	}

	@Override
	public void update(IPolicy p) {
		setNewPolicy(p);
	}

	@Override
	public void setPolicy(IPolicy p) {
		setNewPolicy(p);
	}

	@Override
	public Map<String, String> getContent() {
		return this.getPolicyContent();
	}

	public void setNewPolicy(IPolicy p) {
		set(p);
		Observable.just(p).subscribe(t -> {
			for (IPolicyChangeListener pl : this.getListeners())
				pl.listen(t);
		});
	}

	public abstract void set(IPolicy p);

	public String getPolicyType() {
		return policyType;
	}

	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}

	public Map<String, String> getPolicyContent() {
		return policyContent;
	}

	public void setPolicyContent(Map<String, String> policyContent) {
		this.policyContent = policyContent;
	}

	public List<IPolicyChangeListener> getListeners() {
		return listeners;
	}

	public void setListeners(List<IPolicyChangeListener> listeners) {
		this.listeners = listeners;
	}

	public void addListerner(IPolicyChangeListener pl) {
		this.listeners.add(pl);
	}

	public void removeListerner(IPolicyChangeListener pl) {
		this.listeners.remove(pl);
	}
}
