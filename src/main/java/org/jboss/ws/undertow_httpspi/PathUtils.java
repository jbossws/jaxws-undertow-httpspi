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
