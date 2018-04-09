/*******************************************************************************
 * Copyright (c) 2018 Universitat Politécnica de Catalunya (UPC)
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
 ******************************************************************************/
package org.loopa.element.messagecomposer.dataformatter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.loopa.comm.message.IMessage;
import org.loopa.comm.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataFormatter extends ADataFormatter {
  protected final Logger LOGGER = LoggerFactory.getLogger(getClass().getName());

  @Override
  public void processMessage(IMessage t) {
    String recipients = getRecipientFromPolicy(t.getMessageBody());
    if (recipients != null) {
      Arrays.stream(recipients.split(":")).forEach(r -> {
        Map<String, String> formattedMessageBody = formatMessageContent(t.getMessageBody(), r);
        // change messageBody type for indicating a unique recipient
        formattedMessageBody.put("type", (formattedMessageBody.get("type")).concat("_").concat(r));
        createMeassage(new Message(t.getMessageFrom(), t.getMessageTo(), t.getMessageCode(),
            t.getMessageType(), formattedMessageBody));
      });
    }
  }

  protected Map<String, String> formatMessageContent(Map<String, String> m, String r) {
    /** check format required in policy if exist and format message */
    Map<String, String> formatedMessage = new HashMap<>();
    m.forEach((k, v) -> formatedMessage.put(k, v));
    return formatedMessage;
  }

  protected String getRecipientFromPolicy(Map<String, String> messageContent) {
    return this.getPolicyVariables().get(messageContent.get("type"));
  }

  protected void createMeassage(IMessage formattedMessage) {
    this.getMessageCreator().processMessage(formattedMessage);

  }
}
