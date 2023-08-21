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

import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.PathHandler;
import io.undertow.util.HeaderValues;
import io.undertow.util.HttpString;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jakarta.xml.ws.spi.http.HttpContext;
import jakarta.xml.ws.spi.http.HttpExchange;

/**
 * @author <a href="mailto:ema@redhat.com">Jim Ma</a>
 *
 */
public class UndertowHttpExchange extends HttpExchange
{
   private final HttpServerExchange undertowExchange;

   private UndertowHttpContext context;

   public UndertowHttpExchange(HttpServerExchange serverExchange)
   {
      undertowExchange = serverExchange;
   }

   @Override
   public Map<String, List<String>> getRequestHeaders()
   {
      return new UndertowHeaderMap(undertowExchange.getRequestHeaders());
   }

   @Override
   public String getRequestHeader(String name)
   {
      HeaderValues headerValues = undertowExchange.getRequestHeaders().get(name);
      if (headerValues != null && headerValues.size() > 0)
      {
         String result = "";
         for (String headerValue : headerValues)
         {
            result = result + headerValue;
         }
         return result;
      }
      return null;
   }

   @Override
   public Map<String, List<String>> getResponseHeaders()
   {
      return new UndertowHeaderMap(undertowExchange.getResponseHeaders());
   }

   @Override
   public void addResponseHeader(String name, String value)
   {
      undertowExchange.getResponseHeaders().add(new HttpString(name), value);

   }

   @Override
   public String getRequestURI()
   {
      return undertowExchange.getRequestURI();
   }

   @Override
   public String getContextPath()
   {
      return PathUtils.getContextPathFromRequest(undertowExchange.getRequestPath());
   }

   @Override
   public String getRequestMethod()
   {
      return undertowExchange.getRequestMethod().toString();
   }

   @Override
   public HttpContext getHttpContext()
   {
      if (context == null)
      {
         context = new UndertowHttpContext(new PathHandler(), PathUtils.getContextPathFromRequest(undertowExchange.getRequestPath()),
               PathUtils.getPathFromRequest(undertowExchange.getRequestPath()));
      }
      return context;
   }

   @Override
   public void close() throws IOException
   {
      undertowExchange.endExchange();
   }

   @Override
   public InputStream getRequestBody() throws IOException
   {
      return undertowExchange.getInputStream();
   }

   @Override
   public OutputStream getResponseBody() throws IOException
   {
      return undertowExchange.getOutputStream();
   }

   @Override
   public void setStatus(int status)
   {
      undertowExchange.setResponseCode(status);

   }

   @Override
   public InetSocketAddress getRemoteAddress()
   {
      return undertowExchange.getSourceAddress();
   }

   @Override
   public InetSocketAddress getLocalAddress()
   {
      return undertowExchange.getDestinationAddress();
   }

   @Override
   public String getProtocol()
   {
      return undertowExchange.getProtocol().toString();
   }

   @Override
   public String getScheme()
   {
      return undertowExchange.getRequestScheme();
   }

   @Override
   public String getPathInfo()
   {
      return undertowExchange.getRequestPath();
   }

   @Override
   public String getQueryString()
   {
      return undertowExchange.getQueryString();
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

   @Override
   public Principal getUserPrincipal()
   {
      //TODO
      return null;
   }

   @Override
   public boolean isUserInRole(String role)
   {
      //TODO
      return false;
   }

}
