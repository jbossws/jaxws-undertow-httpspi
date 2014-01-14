/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2014, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.ws.httpserver_httpspi;

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

import javax.xml.ws.spi.http.HttpContext;
import javax.xml.ws.spi.http.HttpExchange;

/**
 * @author <a href="mailto:ema@redhat.com">Jim Ma</a>
 *
 */
public class UndertowHttpExchange extends HttpExchange
{
   private HttpServerExchange undertowExchange;

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
