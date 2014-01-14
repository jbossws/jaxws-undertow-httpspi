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

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * A com.sun.net.httpserver.HttpHandler delegating
 * to a javax.xml.ws.spi.http.HttpHandler instance.
 * 
 * @author alessio.soldano@jboss.com
 * @sine 22-Aug-2010
 *
 */
public class HttpHandlerDelegate implements HttpHandler {

   private javax.xml.ws.spi.http.HttpHandler delegate;
   
   public HttpHandlerDelegate(javax.xml.ws.spi.http.HttpHandler delegate)
   {
      this.delegate = delegate;
   }

   @Override
   public void handle(HttpExchange ex) throws IOException
   {
      try
      {
         delegate.handle(new HttpExchangeDelegate(ex));
      }
      catch (IOException e)
      {
         e.printStackTrace();
         throw e;
      }
      catch (RuntimeException e)
      {
         e.printStackTrace();
         throw e;
      }
      catch (Exception e)
      {
         e.printStackTrace();
         throw new RuntimeException(e);
      }
   }
   
}