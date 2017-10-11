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

import org.loopa.comm.message.IMessage;
import org.loopa.generic.documents.IPolicy;

public abstract class APolicyManager implements IPolicyManager{

	private IPolicy activePolicy;


	public APolicyManager(IPolicy policy) {
		super();
		this.activePolicy = policy;
	}

	@Override
	public void processPolicy(IMessage m) {
		/*Transform to Policy*/
		IPolicy p = null;
		updatePolicy(p);
	}
	
	public abstract void updatePolicy(IPolicy p);


	public IPolicy getActivePolicy() {
		return activePolicy;
	}

	public void setACtivePolicy(IPolicy newPolicy) {
		this.activePolicy = newPolicy;
	}


}
