package org.loopa.autonomicmanager;

import org.loopa.policy.IPolicy;
import org.loopa.recipient.IRecipient;

public interface IAutonomicManager {

  // Get AM id
  String getAutonomicManagerId();

  // // Get AM policy
  // IPolicy getAutonomicManagerPolicy();

  // Start AM operation
  void start();

  // Add ME
  void addME(IPolicy loopOpePolicy, IRecipient effector);

  // Process an adaptation

  void AdaptLoopElement(String adapter, String elementId, AMElementAdpaptationType adaptType,
      String adaptation);
}
