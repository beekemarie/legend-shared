// Copyright 2020 Goldman Sachs
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.finos.legend.server.pac4j.kerberos.local;

import java.util.HashMap;
import java.util.Map;
import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;

public class SystemAccountLoginConfiguration extends Configuration
{
  private String keytabLocation;
  private String principal;
  private boolean initiator;

  public SystemAccountLoginConfiguration(
      String keytabLocation, String principal, boolean initiator)
  {
    this.keytabLocation = keytabLocation;
    this.principal = principal;
    this.initiator = initiator;
  }

  @Override
  public AppConfigurationEntry[] getAppConfigurationEntry(String name)
  {
    try
    {
      Map<String, String> options = new HashMap<String, String>();
      options.put("doNotPrompt", "true");
      options.put("useKeyTab", "true");
      options.put("keyTab", this.keytabLocation);
      options.put("principal", this.principal);
      options.put("isInitiator", Boolean.toString(this.initiator));
      options.put("debug", "false");
      options.put("storeKey", "true");

      AppConfigurationEntry ace =
          new AppConfigurationEntry(
              "com.sun.security.auth.module.Krb5LoginModule",
              AppConfigurationEntry.LoginModuleControlFlag.REQUIRED,
              options);

      AppConfigurationEntry[] entryArray = new AppConfigurationEntry[1];
      entryArray[0] = ace;

      return entryArray;
    } catch (Exception e)
    {
      throw new RuntimeException(e);
    }
  }
}
