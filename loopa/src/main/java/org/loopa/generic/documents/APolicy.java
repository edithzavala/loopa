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
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;

public abstract class APolicy implements IPolicy {
	private String policyType;
	private HashMap<String, String> policyContent;
	private List<IPolicyChangeListener> listeners;

	public APolicy(String policyType, HashMap<String, String> policyContent) {
		super();
		this.policyType = policyType;
		this.policyContent = policyContent;
		this.listeners = new ArrayList<>();
	}

	@Override
	public void update(IPolicy p) {
		updatePolicy(p);
		Observable.just(p).subscribe(t -> {
			for (IPolicyChangeListener pl : this.getListeners())
				pl.listen(t);
		});
	}

	public abstract void updatePolicy(IPolicy p);

	public String getPolicyType() {
		return policyType;
	}

	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}

	public HashMap<String, String> getPolicyContent() {
		return policyContent;
	}

	public void setPolicyContent(HashMap<String, String> policyContent) {
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
