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

import io.undertow.server.handlers.PathHandler;

import java.util.Set;

import javax.xml.ws.spi.http.HttpContext;
import javax.xml.ws.spi.http.HttpHandler;

/**
 * @author <a href="mailto:ema@redhat.com">Jim Ma</a>
 *
 */
//TODO:Look at HttpHandlerImpl - avoid to create duplicate UndertowHttpContext to publish endpoint
public class UndertowHttpContext extends HttpContext
{
   private String handlerpath;
   private PathHandler pathHandler;
   private String path;

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
