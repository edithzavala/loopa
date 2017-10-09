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

import java.util.Timer;
import java.util.TimerTask;

public class PeriodicMessageProducer extends AMessageProducer {

	public PeriodicMessageProducer(String endPoint) {
		super(endPoint);
	}

	@Override
	public void listen(String endPoint) {
		Timer timer = new Timer();
		timer.schedule(new Cycle(this), 0,30);
	}

	class Cycle extends TimerTask {
		PeriodicMessageProducer p;

		public Cycle(PeriodicMessageProducer p) {
			this.p = p;
		}

		@Override
		public void run() {
				//revise endpoint
		}
	}
}
