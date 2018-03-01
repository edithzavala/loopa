package org.loopa.comm.message;

import java.util.HashMap;
import java.util.Map;

/**
 * This type of body message is intended to to be sent to elements for adapting they policies
 */
public class PolicyConfigMessageBody {
  private Map<String, String> body;

  public PolicyConfigMessageBody(String policyOwner, String policyContent) {
    super();
    this.body = new HashMap<>();
    this.body.put("policyOwner", policyOwner);
    this.body.put("policyContent", policyContent);
  }

  public Map<String, String> getMessageBody() {
    return this.body;
  }
}
