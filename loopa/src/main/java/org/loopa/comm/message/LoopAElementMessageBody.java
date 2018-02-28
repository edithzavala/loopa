package org.loopa.comm.message;

import java.util.HashMap;
import java.util.Map;

/**
 * This type of message body is intended to be sent by elements FLs to other elements (including the
 * effectors) using their doOperation() method
 * 
 * type parameter is used by senders for deciding to which element the message should be sent
 * content parameter contains the actual content which will vary from one element to another, it
 * should be agreed by FLs
 */
public class LoopAElementMessageBody {
  private Map<String, String> body;

  public LoopAElementMessageBody(String type, String content) {
    super();
    this.body = new HashMap<>();
    this.body.put("type", type);
    this.body.put("content", content);
  }

  public Map<String, String> getMessageBody() {
    return this.body;
  }

}
