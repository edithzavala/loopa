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
 *  	Jordi Marco
 *  	Edith Zavala
 *******************************************************************************/

package org.loopa.comm.buffer;

import java.util.LinkedList;
import java.util.List;

public class SyncronizedBuffer<Element> {
	private List<Element> buffer;

	public SyncronizedBuffer() {
		super();
		this.buffer = new LinkedList<Element>();
	}

	public synchronized Element getNext() {
		// Wait until messages is not empty
		while (buffer.size() == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		Element message = buffer.remove(0);
		notifyAll();
		return message;
	}

	public synchronized void put(Element message) {
		if (message != null) {
			buffer.add(message);
			// Notify consumer that status has changed.
			notifyAll();
		}
	}
}
