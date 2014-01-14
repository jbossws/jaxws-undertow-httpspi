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
   private Builder builder;
   private PathHandler pathHandler;
   private Undertow undertow;

   public UndertowServer(int port, String host)
   {
      builder = Undertow.builder().addListener(port, host);
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
