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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;
import javax.xml.ws.soap.MTOMFeature;
import javax.xml.ws.spi.http.HttpContext;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * A JAXWS 2.2 Endoint.publish(HttpContext context) API test
 * using the JDK6 httpsever as underlying http container
 * 
 * @author alessio.soldano@jboss.com
 * @since 22-Aug-2010
 *
 */
public class EndpointAPITest extends Assert
{

   private static int currentPort = 9876;

   private UndertowServer server;

   @Before
   public void setUp() throws IOException
   {
      currentPort++;
      server = new UndertowServer(currentPort, "localhost");
   }

   @After
   public void tearDown()
   {
      server.stop();
      server = null;
   }

   @Test
   public void testSingleEndpoint() throws Exception
   {

      String contextPath = "/ctxt";
      String path = "/echo";
      String address = "http://localhost:" + currentPort + contextPath + path;

      HttpContext context = HttpServerContextFactory.createHttpContext(server, contextPath, path);

      Endpoint endpoint = Endpoint.create(new EndpointBean());
      endpoint.publish(context); // Use httpserver context for publishing

      server.start();

      invokeEndpoint(address);

      endpoint.stop();
   }

   @Test
   public void testMultiplePublishSameAddress() throws Exception
   {
      server.start();
      String contextPath = "/ctxt";
      String path = "/echo";
      for (int i = 0; i < 3; i++)
      {
         HttpContext ctx = HttpServerContextFactory.createHttpContext(server, contextPath, path);
         String address = "http://localhost:" + currentPort + contextPath + path;

         Endpoint endpoint = Endpoint.create(new EndpointBean());
         endpoint.publish(ctx); // Use httpserver context for publishing

         invokeEndpoint(address);

         endpoint.stop();
      }
   }
   
   @Test
   public void testMultipleEndpointsSameContext() throws Exception
   {
      server.start();
      String contextPath = "/ctxt";
      String path = "/echo";
      int k = 3;
      Endpoint[] endpoints = new Endpoint[k];
      HttpContext[] contexts = new HttpContext[k];
      String[] addresses = new String[k];
      for (int i = 0; i < k; i++)
      {
         addresses[i] = "http://localhost:" + currentPort + contextPath + path + i;
         contexts[i] = HttpServerContextFactory.createHttpContext(server, contextPath, path + i);
         endpoints[i] = Endpoint.create(new EndpointBean());
         endpoints[i].publish(contexts[i]);
      }
      for (int i = 0; i < k; i++)
      {
         invokeEndpoint(addresses[i]);
      }
      for (int i = 0; i < k; i++)
      {
         endpoints[i].stop();
      }
   }

   @Test
   public void testMultipleEndpointsDifferentContexts() throws Exception
   {
      server.start();
      String contextPath = "/ctxt";
      String path = "/echo";
      int k = 3;
      Endpoint[] endpoints = new Endpoint[k];
      HttpContext[] contexts = new HttpContext[k];
      String[] addresses = new String[k];
      for (int i = 0; i < k; i++)
      {
         addresses[i] = "http://localhost:" + currentPort + contextPath + i + path;
         contexts[i] = HttpServerContextFactory.createHttpContext(server, contextPath + i, path);
         endpoints[i] = Endpoint.create(new EndpointBean());
         endpoints[i].publish(contexts[i]);
      }
      for (int i = 0; i < k; i++)
      {
         invokeEndpoint(addresses[i]);
      }
      for (int i = 0; i < k; i++)
      {
         endpoints[i].stop();
      }
   }

   private void invokeEndpoint(String publishURL) throws Exception
   {
      URL wsdlURL = new URL(publishURL + "?wsdl");
      QName qname = new QName("http://org.apache.cxf/jaxws/endpoint/", "EndpointService");
      Service service = Service.create(wsdlURL, qname);
      checkBasicInvocations(service);
      checkMTOMInvocation(service);
   }

   private static void checkBasicInvocations(Service service)
   {
      EndpointInterface port = (EndpointInterface) service.getPort(EndpointInterface.class);
      String helloWorld = "Hello world!";
      assertEquals(0, port.getCount());
      Object retObj = port.echo(helloWorld);
      assertEquals(helloWorld, retObj);
      assertEquals(1, port.getCount());
      port.echo(helloWorld);
      assertEquals(2, port.getCount());
      try
      {
         port.getException();
         fail("Exception expected!");
      }
      catch (Exception e)
      {
         assertEquals("Ooops", e.getMessage());
      }
   }

   private static void checkMTOMInvocation(Service service) throws IOException
   {
      DataSource ds = new DataSource()
      {
         public String getContentType()
         {
            return "text/plain";
         }

         public InputStream getInputStream() throws IOException
         {
            return new ByteArrayInputStream("some string".getBytes());
         }

         public String getName()
         {
            return "none";
         }

         public OutputStream getOutputStream() throws IOException
         {
            return null;
         }
      };
      EndpointInterface port = (EndpointInterface) service.getPort(EndpointInterface.class, new MTOMFeature(true));
      DataHandler dh = new DataHandler(ds);
      DHResponse response = port.echoDataHandler(new DHRequest(dh));
      assertNotNull(response);
      Object content = response.getDataHandler().getContent();
      assertEquals("Server data", content);
      String contentType = response.getDataHandler().getContentType();
      assertEquals("text/plain", contentType);
   }

}
