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

import java.util.Map;
import java.util.Set;

import javax.xml.ws.spi.http.HttpContext;
import javax.xml.ws.spi.http.HttpHandler;

/**
 * A javax.xml.ws.spi.http.HttpContext that delegates
 * to a com.sun.net.httpserver.HttpContext instance.
 * 
 * @author alessio.soldano@jboss.com
 * @since 22-Aug-2010
 *
 */
public class HttpContextDelegate extends HttpContext
{
   private com.sun.net.httpserver.HttpContext delegate;
   private String path;

   public HttpContextDelegate(com.sun.net.httpserver.HttpContext delegate, String path)
   {
      this.delegate = delegate;
      this.path = path;
   }

   @Override
   public String getPath()
   {
      return path;
   }

   @Override
   public Object getAttribute(String name)
   {
      Map<String, Object> map = delegate.getAttributes();
      return map != null ? map.get(name) : null;
   }

   @Override
   public Set<String> getAttributeNames()
   {
      Map<String, Object> map = delegate.getAttributes();
      return map != null ? map.keySet() : null;
   }

   @Override
   public void setHandler(HttpHandler handler)
   {
      if (handler instanceof com.sun.net.httpserver.HttpHandler)
      {
         delegate.setHandler((com.sun.net.httpserver.HttpHandler) handler);
      }
      else
      {
         delegate.setHandler(new HttpHandlerDelegate(handler));
      }
   }

}
