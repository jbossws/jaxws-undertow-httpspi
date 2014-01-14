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
/**
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

package org.jboss.ws.httpserver_httpspi;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Provides a convenient hook onFirstWrite() for those needing
 * to wrap an output stream.
 *
 */
public abstract class AbstractWrappedOutputStream extends OutputStream {

   protected OutputStream wrappedStream;
   protected boolean written;
   
   protected AbstractWrappedOutputStream() {
       super();
   }

   @Override
   public void write(byte[] b, int off, int len) throws IOException {
       if (!written) {
           onFirstWrite();
           written = true;
       }
       if (wrappedStream != null) {
           wrappedStream.write(b, off, len);
       }
   }

   protected void onFirstWrite() throws IOException {
   }

   @Override
   public void write(byte[] b) throws IOException {
       write(b, 0, b.length);
   }

   @Override
   public void write(int b) throws IOException {
       if (!written) {
           onFirstWrite();
           written = true;
       }
       if (wrappedStream != null) {
           wrappedStream.write(b);
       }
   }
   
   @Override
   public void close() throws IOException {
       if (wrappedStream != null) {
           wrappedStream.close();
       }
   }

   @Override
   public void flush() throws IOException {
       if (written && wrappedStream != null) {
           wrappedStream.flush();
       }
   }
}

