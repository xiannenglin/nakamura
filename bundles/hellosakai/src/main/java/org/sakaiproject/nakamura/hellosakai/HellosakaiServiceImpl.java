/*
 * Licensed to the Sakai Foundation (SF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The SF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.sakaiproject.nakamura.hellosakai;

import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.io.JSONWriter;
import org.sakaiproject.nakamura.api.hellosakai.HellosakaiService;

import java.io.IOException;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

/**
 * 
 */
@org.apache.felix.scr.annotations.Component(immediate = true)
@Service(value = HellosakaiServiceImpl.class)
public class HellosakaiServiceImpl implements HellosakaiService {


  public static void save(SlingHttpServletRequest request,SlingHttpServletResponse response) {
    try {
      Session session = request.getResourceResolver().adaptTo(Session.class);

//      Node root = session.getRootNode();
      Node root = request.getResourceResolver().adaptTo(Node.class);
      
      Node helloNode = root.addNode("hello");
      Node sakaiNode = helloNode.addNode("sakai");
      sakaiNode.setProperty("description", "hellosakai");

      session.save();
    } catch (RepositoryException re) {
      re.printStackTrace();
    }
  }

  public String createJSON(SlingHttpServletRequest request, SlingHttpServletResponse response) {
    JSONWriter writer = null;
    String json = null;
    try {
      writer = new JSONWriter(response.getWriter());
      writer.object().
        key("message").
        value("hellosakai").endObject();
      json = writer.toString();
    } catch (IOException e1) {
      e1.printStackTrace();
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return json;
  }
}