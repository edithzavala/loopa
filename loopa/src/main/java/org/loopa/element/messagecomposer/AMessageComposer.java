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

package org.loopa.element.messagecomposer;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.loopa.comm.message.IMessage;
import org.loopa.element.messagecomposer.dataformatter.IDataFormatter;
import org.loopa.element.messagecomposer.messagecreator.IMessageCreator;
import org.loopa.generic.documents.managers.IPolicyManager;
import org.loopa.generic.element.component.ALoopAElementComponent;

import io.reactivex.Observable;

public abstract class AMessageComposer extends ALoopAElementComponent implements IMessageComposer {

	private IDataFormatter dataFormatter;
	private IMessageCreator messageCreator;
	private ConcurrentLinkedQueue<IMessage> opeMssgQueue;

	public AMessageComposer(IPolicyManager policyManager, IDataFormatter dataFormatter,
			IMessageCreator messageCreator) {
		super(policyManager);
		this.dataFormatter = dataFormatter;
		this.messageCreator = messageCreator;
		Observable.fromIterable(opeMssgQueue).subscribe(t -> {
			IMessage formattedMessage = this.dataFormatter.formatData(t);
			this.messageCreator.generateMessage(formattedMessage);
		});
	}

	@Override
	public void doOperation(IMessage m) {
		opeMssgQueue.add(m);
	}

	public IDataFormatter getDataFormatter() {
		return dataFormatter;
	}

	public void setDataFormatter(IDataFormatter dataFormatter) {
		this.dataFormatter = dataFormatter;
	}

	public IMessageCreator getMessageCreator() {
		return messageCreator;
	}

	public void setMessageCreator(IMessageCreator messageCreator) {
		this.messageCreator = messageCreator;
	}

}
