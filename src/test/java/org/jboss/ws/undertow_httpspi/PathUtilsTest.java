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

import java.net.URI;

import org.jboss.ws.undertow_httpspi.PathUtils;

import junit.framework.TestCase;

public class PathUtilsTest extends TestCase
{
   public void testPath() {
      assertEquals("", PathUtils.getPath("http://localhost:8080"));
      assertEquals("", PathUtils.getPath("http://localhost:8080/foo"));
      assertEquals("", PathUtils.getPath("http://localhost:8080/foo/"));
      assertEquals("/bar", PathUtils.getPath("http://localhost:8080/foo/bar"));
      assertEquals("/bar", PathUtils.getPath("http://localhost:8080/foo/bar/"));
      assertEquals("/bar", PathUtils.getPath("http://localhost:8080/foo/bar?wsdl"));
      assertEquals("/bar", PathUtils.getPath("http://localhost:8080/foo/bar/?wsdl"));
      assertEquals("/bar", PathUtils.getPath("http://localhost:8080/foo/fooagain/bar"));
      assertEquals("/bar", PathUtils.getPath("http://localhost:8080/foo/fooagain/bar?wsdl"));
      assertEquals("/bar", PathUtils.getPath("http://localhost:8080/foo/fooagain/bar?wsdl&xsd=ff"));
      assertEquals("/bar", PathUtils.getPath("http://localhost/foo/bar"));
      assertEquals("/bar", PathUtils.getPath("https://localhost/foo/bar"));
      
   }
   
   public void testContextPath() {
      assertEquals("/", PathUtils.getContextPath("http://localhost:8080"));
      assertEquals("/foo", PathUtils.getContextPath("http://localhost:8080/foo"));
      assertEquals("/foo", PathUtils.getContextPath("http://localhost:8080/foo/"));
      assertEquals("/foo", PathUtils.getContextPath("http://localhost:8080/foo/bar"));
      assertEquals("/foo", PathUtils.getContextPath("http://localhost:8080/foo/bar/"));
      assertEquals("/foo/bar", PathUtils.getContextPath("http://localhost:8080/foo/bar/baragain"));
      assertEquals("/foo", PathUtils.getContextPath("http://localhost:8080/foo/bar?wsdl&xsd=kk"));
      assertEquals("/foo", PathUtils.getContextPath("http://localhost:8080/foo/bar/?wsdl&xsd=kk"));
      assertEquals("/foo", PathUtils.getContextPath("http://localhost/foo/bar"));
      assertEquals("/foo", PathUtils.getContextPath("https://localhost/foo/bar"));
   }
   
   public void testURIPath() throws Exception {
      assertEquals("", PathUtils.getPath(new URI("http", "", "localhost", 8080, "", "", "")));
      assertEquals("", PathUtils.getPath(new URI("http", "", "localhost", 8080, "/foo", "", "")));
      assertEquals("", PathUtils.getPath(new URI("http", "", "localhost", 8080, "/foo/", "", "")));
      assertEquals("/bar", PathUtils.getPath(new URI("http", "", "localhost", 8080, "/foo/bar", "", "")));
      assertEquals("/bar", PathUtils.getPath(new URI("http", "", "localhost", 8080, "/foo/bar/", "", "")));
      assertEquals("/bar", PathUtils.getPath(new URI("http", "", "localhost", 8080, "/foo/bar", "wsdl", "")));
      assertEquals("/bar", PathUtils.getPath(new URI("http", "", "localhost", 8080, "/foo/bar/", "wsdl", "")));
      assertEquals("/bar", PathUtils.getPath(new URI("http", "", "localhost", 8080, "/foo/fooagain/bar", "", "")));
      assertEquals("/bar", PathUtils.getPath(new URI("http", "", "localhost", 8080, "/foo/fooagain/bar", "wsdl", "")));
      assertEquals("/bar", PathUtils.getPath(new URI("http", "", "localhost", 8080, "/foo/fooagain/bar", "wsdl&xsd=ff", "")));
      assertEquals("/bar", PathUtils.getPath(new URI("http", "", "localhost", 0, "/foo/bar", "", "")));
      assertEquals("/bar", PathUtils.getPath(new URI("https", "", "localhost", 0, "/foo/bar", "", "")));
   }
   
   public void testURIContextPath() throws Exception {
      assertEquals("/", PathUtils.getContextPath(new URI("http", "", "localhost", 8080, "", "", "")));
      assertEquals("/foo", PathUtils.getContextPath(new URI("http", "", "localhost", 8080, "/foo", "", "")));
      assertEquals("/foo", PathUtils.getContextPath(new URI("http", "", "localhost", 8080, "/foo/", "", "")));
      assertEquals("/foo", PathUtils.getContextPath(new URI("http", "", "localhost", 8080, "/foo/bar", "", "")));
      assertEquals("/foo", PathUtils.getContextPath(new URI("http", "", "localhost", 8080, "/foo/bar/", "", "")));
      assertEquals("/foo/bar", PathUtils.getContextPath(new URI("http", "", "localhost", 8080, "/foo/bar/baragain", "", "")));
      assertEquals("/foo", PathUtils.getContextPath(new URI("http", "", "localhost", 8080, "/foo/bar", "wsdl&xsd=kk", "")));
      assertEquals("/foo", PathUtils.getContextPath(new URI("http", "", "localhost", 8080, "/foo/bar/", "wsdl&xsd=kk", "")));
      assertEquals("/foo", PathUtils.getContextPath(new URI("http", "", "localhost", 0, "/foo/bar", "", "")));
      assertEquals("/foo", PathUtils.getContextPath(new URI("https", "", "localhost", 0, "/foo/bar", "", "")));
   }
}
