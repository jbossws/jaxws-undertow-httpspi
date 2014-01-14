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

import java.net.URI;

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
