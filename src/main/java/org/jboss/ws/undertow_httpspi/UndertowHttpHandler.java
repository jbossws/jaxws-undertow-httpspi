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

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;

/**
 * @author <a href="mailto:ema@redhat.com">Jim Ma</a>
 *
 */
public class UndertowHttpHandler implements HttpHandler
{

   private final jakarta.xml.ws.spi.http.HttpHandler spihandler;

   public UndertowHttpHandler(jakarta.xml.ws.spi.http.HttpHandler handler)
   {
      spihandler = handler;
   }

   @Override
   public void handleRequest(HttpServerExchange exchange) throws Exception
   {
      spihandler.handle(new UndertowHttpExchange(exchange));
   }

}
