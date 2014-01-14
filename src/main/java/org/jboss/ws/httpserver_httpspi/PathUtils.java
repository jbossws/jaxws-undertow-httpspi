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

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class PathUtils
{
   /**
    * Get the final path section of an address (for servlet
    * container, this is typically a url-pattern for an endpoint)
    * 
    * @param addr
    * @return
    */
   public static String getPath(String addr)
   {
      return getPathInternal(getPathFromString(addr));
   }
   
   public static String getPath(URI addr)
   {
      return getPathInternal(addr.getPath());
   }
   
   public static String getPathFromRequest(String requestPath)
   {
      return getPathInternal(requestPath);
   }

   private static String getPathInternal(String rawpath)
   {
      String path = removeTrailingSlash(rawpath);
      if (path == null || path.length() == 0)
      {
         return path;
      }
      int idx = path.lastIndexOf("/");
      return idx > 0 ? path.substring(path.lastIndexOf("/")) : "";
   }
   
   /**
    * Get the context path section of an address
    * 
    * @param addr
    * @return
    */
   public static String getContextPath(String addr)
   {
      return getContextPathInternal(getPathFromString(addr));
   }
   
   public static String getContextPathFromRequest(String requestPath)
   {
      return getContextPathInternal(requestPath);
   }
   
   public static String getContextPath(URI addr)
   {
      return getContextPathInternal(addr.getPath());
   }
   
   private static String getContextPathInternal(String rawpath)
   {
      String path = removeTrailingSlash(rawpath);
      if (path == null || path.length() == 0)
      {
         return "/";
      }
      int idx = path.lastIndexOf("/");
      return idx > 0 ? path.substring(0, idx) : path;
   }
   
   private static String getPathFromString(String addr)
   {
      String path = null;
      try
      {
         path = new URL(addr).getPath();
      }
      catch (MalformedURLException e)
      {
         //ignore
      }
      return path;
   }
   
   public static String removeTrailingSlash(String path)
   {
      if (path != null && path.length() > 0 && path.lastIndexOf('/') == path.length() - 1)
      {
         path = path.substring(0, path.length() - 1);
      }
      return path;
   }
   
}
