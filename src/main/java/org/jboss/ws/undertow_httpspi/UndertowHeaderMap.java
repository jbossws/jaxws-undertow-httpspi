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

import io.undertow.util.HeaderMap;
import io.undertow.util.HeaderValues;
import io.undertow.util.HttpString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;



public class UndertowHeaderMap implements Map<String, List<String>>
{
   private final HeaderMap headerMap;

   public UndertowHeaderMap(HeaderMap headerMap) {
      this.headerMap = headerMap;
   }

   @Override
   public int size()
   {
      return headerMap.size();
   }

   @Override
   public boolean isEmpty()
   {
      return headerMap.size() > 0;
   }

   @Override
   public boolean containsKey(Object key)
   {
      return headerMap.contains(key.toString());
   }

   @Override
   public boolean containsValue(Object value)
   {
      Iterator<HeaderValues> ite = headerMap.iterator();
      while (ite.hasNext())
      {
         HeaderValues values = ite.next();
         if (values.contains(value))
         {
            return true;
         }
      }
      return false;
   }

   @Override
   public List<String> get(Object key)
   {
      HeaderValues values = headerMap.get(key.toString());
      List<String> result = new ArrayList<String>();
      if (values != null)
      {
         for (String value : values.toArray())
         {
            result.add(value);
         }
      }
      return result;
   }

   @Override
   public List<String> put(String key, List<String> value)
   {
      List<String> previous = get(key);
      if (previous.isEmpty())
      {
         previous = null;
      }
      headerMap.addAll(new HttpString(key), value);
      return previous;

   }

   @Override
   public List<String> remove(Object key)
   {
      List<String> previous = get(key);
      if (previous.isEmpty())
      {
         previous = null;
      }
      headerMap.remove(key.toString());
      return previous;
   }

   @Override
   public void putAll(Map<? extends String, ? extends List<String>> m)
   {
      for (String key : m.keySet())
      {
         headerMap.putAll(new HttpString(key), m.get(key));
      }

   }

   @Override
   public void clear()
   {
      headerMap.clear();

   }

   @Override
   public Set<String> keySet()
   {
      Set<String> result = new HashSet<String>();
      for (HeaderValues value : headerMap)
      {
         result.add(value.getHeaderName().toString());
      }
      return result;
   }

   @Override
   public Collection<List<String>> values()
   {
      List<List<String>> collections = new ArrayList<List<String>>();
      for (HeaderValues value : headerMap)
      {
         List<String> values = new ArrayList<String>();
         for (String headerValue : value)
         {
            values.add(headerValue);
         }
         collections.add(values);
      }
      return collections;
   }

   @Override
   public Set<java.util.Map.Entry<String, List<String>>> entrySet()
   {
      Set<java.util.Map.Entry<String, List<String>>> result = new HashSet<java.util.Map.Entry<String, List<String>>>();
      for (HeaderValues headerValues : headerMap)
      {
         final String key = headerValues.getHeaderName().toString();
         final List<String> headerValueList = new ArrayList<String>();
         for (String value : headerValues)
         {
            headerValueList.add(value);
         }
         result.add(new Entry<String, List<String>>() {

            @Override
            public String getKey()
            {
               return key;
            }

            @Override
            public List<String> getValue()
            {
               return headerValueList;
            }

            @Override
            public List<String> setValue(List<String> value)
            {
               List<String> previous = headerMap.get(key);
               if (previous.isEmpty())
               {
                  previous = null;
               }
               headerMap.addAll(new HttpString(key), value);

               return previous;

            }
         });
      }
      return result;
   }

}
