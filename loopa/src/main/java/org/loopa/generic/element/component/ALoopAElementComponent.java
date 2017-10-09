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

package org.loopa.generic.element.component;

import org.loopa.comm.buffer.ConsumerTask;
import org.loopa.comm.buffer.IConsumer;
import org.loopa.comm.buffer.IProducer;
import org.loopa.comm.buffer.ProducerTask;
import org.loopa.comm.buffer.SyncronizedBuffer;
import org.loopa.comm.message.IMessage;
import org.loopa.generic.documents.IPolicy;
import org.loopa.generic.documents.managers.IPolicyManager;

public abstract class ALoopAElementComponent implements ILoopAElementComponent {
	private String mainEndPoint;
	private String adaptationEndPoint;
	private IPolicyManager policyManager;

	private SyncronizedBuffer<IMessage> mainEndPointBuffer;
	private SyncronizedBuffer<IMessage> adaptationEndPointBuffer;

	private IConsumer<IMessage> mainEndPointConsumer;
	private IProducer<IMessage> mainEndPointProducer;
	private IConsumer<IMessage> adaptationEndPointConsumer;
	private IProducer<IMessage> adaptationEndPointProducer;

	/* Add Async buffers + consumers + producers */

	
	
	public ALoopAElementComponent(String mainEndPoint, String adaptationEndPoint, IPolicyManager policyManager,
			SyncronizedBuffer<IMessage> mainEndPointBuffer, SyncronizedBuffer<IMessage> adaptationEndPointBuffer,
			IConsumer<IMessage> mainEndPointConsumer, IProducer<IMessage> mainEndPointProducer,
			IConsumer<IMessage> adaptationEndPointConsumer, IProducer<IMessage> adaptationEndPointProducer) {
		super();
		this.mainEndPoint = mainEndPoint;
		this.adaptationEndPoint = adaptationEndPoint;
		this.policyManager = policyManager;
		this.mainEndPointBuffer = mainEndPointBuffer;
		this.adaptationEndPointBuffer = adaptationEndPointBuffer;
		this.mainEndPointConsumer = mainEndPointConsumer;
		this.mainEndPointProducer = mainEndPointProducer;
		this.adaptationEndPointConsumer = adaptationEndPointConsumer;
		this.adaptationEndPointProducer = adaptationEndPointProducer;
	}


	@Override
	public void updatePolicy(IPolicy p) {
		policyManager.processPolicy(p);
	}
	
	@Override
	public void startComm() {
		Thread mainEndPointConsumerThread = new Thread(
				new ConsumerTask<IMessage>(this.mainEndPointBuffer, this.mainEndPointConsumer));
		Thread mainEndPointProducerThread = new Thread(
				new ProducerTask<IMessage>(this.mainEndPointBuffer, this.mainEndPointProducer));
		Thread adaptationEndPointConsumerThread = new Thread(
				new ConsumerTask<IMessage>(this.adaptationEndPointBuffer, this.adaptationEndPointConsumer));
		Thread adaptationEndPointProducerThread = new Thread(
				new ProducerTask<IMessage>(this.adaptationEndPointBuffer, this.adaptationEndPointProducer));

		mainEndPointConsumerThread.start();
		mainEndPointProducerThread.start();
		adaptationEndPointConsumerThread.start();
		adaptationEndPointProducerThread.start();
	}

	/*
	 * Add Abstract method: Call component method for process IMessage received in
	 * mainEndPoint
	 */

	public String getMainEndPoint() {
		return mainEndPoint;
	}

	public void setMainEndPoint(String mainEndPoint) {
		this.mainEndPoint = mainEndPoint;
	}

	public String getAdaptationEndPoint() {
		return adaptationEndPoint;
	}

	public void setAdaptationEndPoint(String adaptationEndPoint) {
		this.adaptationEndPoint = adaptationEndPoint;
	}

	public IPolicyManager getPolicyManager() {
		return policyManager;
	}

	public void setPolicyManager(IPolicyManager policyManager) {
		this.policyManager = policyManager;
	}

	public SyncronizedBuffer<IMessage> getMainEndPointBuffer() {
		return mainEndPointBuffer;
	}

	public void setMainEndPointBuffer(SyncronizedBuffer<IMessage> mainEndPointBuffer) {
		this.mainEndPointBuffer = mainEndPointBuffer;
	}

	public SyncronizedBuffer<IMessage> getAdaptationEndPointBuffer() {
		return adaptationEndPointBuffer;
	}

	public void setAdaptationEndPointBuffer(SyncronizedBuffer<IMessage> adaptationEndPointBuffer) {
		this.adaptationEndPointBuffer = adaptationEndPointBuffer;
	}

	public IConsumer<IMessage> getMainEndPointConsumer() {
		return mainEndPointConsumer;
	}

	public void setMainEndPointConsumer(IConsumer<IMessage> mainEndPointConsumer) {
		this.mainEndPointConsumer = mainEndPointConsumer;
	}

	public IProducer<IMessage> getMainEndPointProducer() {
		return mainEndPointProducer;
	}

	public void setMainEndPointProducer(IProducer<IMessage> mainEndPointProducer) {
		this.mainEndPointProducer = mainEndPointProducer;
	}

	public IConsumer<IMessage> getAdaptationEndPointConsumer() {
		return adaptationEndPointConsumer;
	}

	public void setAdaptationEndPointConsumer(IConsumer<IMessage> adaptationEndPointConsumer) {
		this.adaptationEndPointConsumer = adaptationEndPointConsumer;
	}

	public IProducer<IMessage> getAdaptationEndPointProducer() {
		return adaptationEndPointProducer;
	}

	public void setAdaptationEndPointProducer(IProducer<IMessage> adaptationEndPointProducer) {
		this.adaptationEndPointProducer = adaptationEndPointProducer;
	}

}
