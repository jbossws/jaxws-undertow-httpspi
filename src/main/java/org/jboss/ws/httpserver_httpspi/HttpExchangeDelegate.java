/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat Middleware LLC, and individual contributors
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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.ws.spi.http.HttpContext;
import javax.xml.ws.spi.http.HttpExchange;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsServer;

/**
 * A javax.xml.ws.spi.http.HttpExchange that delegates
 * to a com.sun.net.httpserver.HttpExchange instance.
 * 
 * @author alessio.soldano@jboss.com
 * @since 22-Aug-2010
 *
 */
public class HttpExchangeDelegate extends HttpExchange
{
   private com.sun.net.httpserver.HttpExchange delegate;
   private HttpContextDelegate contextDelegate;
   private OutputStream wrappeddOutputStream;
   private int status;

   public HttpExchangeDelegate(com.sun.net.httpserver.HttpExchange delegate)
   {
      this.delegate = delegate;
   }

   @Override
   public Map<String, List<String>> getRequestHeaders()
   {
      return delegate.getRequestHeaders();
   }

   @Override
   public String getRequestHeader(String name)
   {
      return delegate.getRequestHeaders().getFirst(name);
   }

   @Override
   public Map<String, List<String>> getResponseHeaders()
   {
      return delegate.getResponseHeaders();
   }

   @Override
   public void addResponseHeader(String name, String value)
   {
      delegate.getResponseHeaders().add(name, value);
   }

   @Override
   public String getRequestURI()
   {
      return delegate.getRequestURI().toString();
   }

   @Override
   public String getContextPath()
   {
      return PathUtils.getContextPath(delegate.getRequestURI());
   }

   @Override
   public String getRequestMethod()
   {
      return delegate.getRequestMethod();
   }

   @Override
   public HttpContext getHttpContext()
   {
      if (contextDelegate == null)
      {
         contextDelegate = new HttpContextDelegate(delegate.getHttpContext(), PathUtils.getPath(delegate.getRequestURI()));
      }
      return contextDelegate;
   }

   @Override
   public void close() throws IOException
   {
      delegate.close();
   }
   
   @Override
   public InputStream getRequestBody() throws IOException
   {
      return delegate.getRequestBody();
   }

   @Override
   public OutputStream getResponseBody() throws IOException
   {
      if (wrappeddOutputStream == null)
      {
         wrappeddOutputStream = new AbstractWrappedOutputStream()
         {
            /**
             * On first write we need to send the response headers and
             * the status as that won't be possible after getting the
             * response body stream from the delegate
             */
            @Override
            protected void onFirstWrite() throws IOException
            {
               if (status == 0) //assume no status -> 200 OK
               {
                  status = HttpURLConnection.HTTP_OK;
               }
               delegate.sendResponseHeaders(status, 0);
               this.wrappedStream = delegate.getResponseBody();
            }
         };
      }
      return wrappeddOutputStream;
   }

   @Override
   public void setStatus(int status)
   {
      //do not immediately set status to the delegate, as that would flush it to the
      //client together with the headers, preventing other headers from being added
      this.status = status;
   }

   @Override
   public InetSocketAddress getRemoteAddress()
   {
      return delegate.getRemoteAddress();
   }

   @Override
   public InetSocketAddress getLocalAddress()
   {
      return delegate.getLocalAddress();
   }

   @Override
   public String getProtocol()
   {
      return delegate.getProtocol();
   }

   @Override
   public String getScheme()
   {
      String scheme = delegate.getRequestURI().getScheme();
      if (scheme == null)
      {
         //fallback for relative urls (e.g. "/foo/bar?wsdl") with no scheme provided by the container
         HttpServer server = delegate.getHttpContext().getServer();
         scheme = server instanceof HttpsServer ? "https" : "http";
      }
      return scheme;
   }

   @Override
   public String getPathInfo()
   {
      return null;
   }

   @Override
   public String getQueryString()
   {
      return delegate.getRequestURI().getQuery();
   }

   @Override
   public Object getAttribute(String name)
   {
      return delegate.getAttribute(name);
   }

   @Override
   public Set<String> getAttributeNames()
   {
      throw new UnsupportedOperationException();
   }

   @Override
   public Principal getUserPrincipal()
   {
      return delegate.getPrincipal();
   }

   @Override
   public boolean isUserInRole(String role)
   {
      return false;
   }
}
