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
package org.loopa.messagecomposer.test;

/*******************************************************************************
 * Copyright (c) 2017 Universitat Politécnica de Catalunya (UPC)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 * Contributors: Edith Zavala
 *******************************************************************************/
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.loopa.element.messagecomposer.IMessageComposer;
import org.loopa.element.messagecomposer.MessageComposer;
import org.loopa.element.messagecomposer.dataformatter.DataFormatter;
import org.loopa.element.messagecomposer.dataformatter.IDataFormatter;
import org.loopa.element.messagecomposer.messagecreator.IMessageCreator;
import org.loopa.element.messagecomposer.messagecreator.MessageCreator;

public class MessageComposerTest {
  IDataFormatter mcDF;
  IMessageCreator mcMC;

  @Before
  public void initializeModules() {
    mcDF = new DataFormatter();
    mcMC = new MessageCreator();
  }

  @Test
  public void testCreateMessageComposer() {
    IMessageComposer mc = new MessageComposer("mc", mcDF, mcMC);
    assertNotNull(mc);
  }
}
