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
package org.loopa.policy.manager;

import org.loopa.comm.message.IMessage;
import org.loopa.generic.element.component.ILoopAElementComponent;
import org.loopa.policy.IPolicy;

public interface IPolicyManager {
	public void processPolicy(IMessage m);

	public void setComponent(ILoopAElementComponent id);

	public ILoopAElementComponent getComponent();
	
	public IPolicy getActivePolicy();
}