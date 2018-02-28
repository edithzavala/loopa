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
package org.loopa.policy;

import java.util.Map;

public class Policy extends APolicy {

  public Policy(String policyOwner, Map<String, String> policyContent) {
    super(policyOwner, policyContent);
  }

  @Override
  public IPolicy updatePolicy(IPolicy p) {
    p.getPolicyContent().forEach((k, v) -> {
      if (k != null) {
        this.getPolicyContent().put(k, v);
      } else {
        this.getPolicyContent().remove(k);
      }
    });
    return this;
  }

}
