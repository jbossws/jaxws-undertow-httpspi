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

import io.undertow.server.handlers.PathHandler;

import java.util.Set;

import jakarta.xml.ws.spi.http.HttpContext;
import jakarta.xml.ws.spi.http.HttpHandler;

/**
 * @author <a href="mailto:ema@redhat.com">Jim Ma</a>
 *
 */
//TODO:Look at HttpHandlerImpl - avoid to create duplicate UndertowHttpContext to publish endpoint
public class UndertowHttpContext extends HttpContext
{
   private final String handlerpath;
   private final PathHandler pathHandler;
   private final String path;

   public UndertowHttpContext(PathHandler pathHandler, String contextPath, String path)
   {
      this.pathHandler = pathHandler;
      this.path = path;
      this.handlerpath = contextPath + path;
   }

   @Override
   public void setHandler(HttpHandler handler)
   {
      pathHandler.addExactPath(handlerpath, new UndertowHttpHandler(handler));
   }

   @Override
   public String getPath()
   {
      return this.path;
   }

   @Override
   public Object getAttribute(String name)
   {
      // TODO 
      return null;
   }

   @Override
   public Set<String> getAttributeNames()
   {
      // TODO 
      return null;
   }

}
