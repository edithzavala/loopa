package org.loopa.autonomicmanager;

import java.util.Map;
import org.loopa.recipient.IRecipient;

public interface IAutonomicManager {

  // Get AM id
  String getAutonomicManagerId();

  // // Get AM policy
  // IPolicy getAutonomicManagerPolicy();

  // Start AM operation
  void construct();

  // Add ME
  void addME(String elementId, IRecipient effector);

  // Process an adaptation

  void AdaptLoopElement(String adapter, String elementId, AMElementAdpaptationType adaptType,
      Map<String, String> adaptation);
}
