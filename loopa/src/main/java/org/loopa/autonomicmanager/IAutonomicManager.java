package org.loopa.autonomicmanager;

import java.util.Map;
import org.loopa.recipient.IRecipient;

public interface IAutonomicManager {

  // Get AM id
  String getAutonomicManagerId();

  // // Get AM policy
  // IPolicy getAutonomicManagerPolicy();

  // Start AM operation
  void start();

  // Add ME
  void addME(Map<String, Map<String, String>> policyContentByElement, IRecipient effector);

  // Process an adaptation

  void AdaptLoopElement(String adapter, String elementId, AMElementAdpaptationType adaptType,
      Map<String, String> adaptation);
}
