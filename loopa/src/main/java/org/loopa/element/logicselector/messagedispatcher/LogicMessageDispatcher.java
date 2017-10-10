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

package org.loopa.element.logicselector.messagedispatcher;

import org.loopa.comm.message.IMessage;

public class LogicMessageDispatcher extends ALogicMessageDispatcher {

	@Override
	public void dispatch(IMessage m) {
		/*
		 * TODO* Select corresponding logic, e.g., according to the subject
		 * logicMainEndPoint = this.getPolicyVariables().get(((Message)m).getSubject());
		 * and send the message to logicMainEndPoint
		 */

	}

}
