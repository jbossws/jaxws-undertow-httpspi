/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jboss.ws.undertow_httpspi;

import io.undertow.Undertow;
import io.undertow.Undertow.Builder;
import io.undertow.server.handlers.BlockingHandler;
import io.undertow.server.handlers.PathHandler;

/**
 * @author <a href="mailto:ema@redhat.com">Jim Ma</a>
 *
 */
public class UndertowServer
{
   private final Builder builder;
   private final PathHandler pathHandler;
   private Undertow undertow;

   public UndertowServer(int port, String host)
   {
      builder = Undertow.builder().addHttpListener(port, host);
      pathHandler = new PathHandler();
   }

   public Builder getBuilder()
   {
      return builder;
   }

   public PathHandler getPathHandler()
   {
      return pathHandler;
   }

   public void start()
   {
      undertow = builder.setHandler(new BlockingHandler(pathHandler)).build();
      undertow.start();
   }

   public void stop()
   {
      if (undertow != null)
      {
         undertow.stop();
      }
   }

}
