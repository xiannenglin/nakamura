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

import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.sakaiproject.nakamura.api.hellosakai.HellosakaiService;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;

@SlingServlet(methods = { "GET", "POST" }, paths = { "/system/hellosakai" }, generateComponent = true, generateService = true)
public class HellosakaiServlet extends SlingAllMethodsServlet {

  private static final long serialVersionUID = -7179274035537456977L;
  
  private static HellosakaiService hellosakaiService = new HellosakaiServiceImpl();

  // @Reference
  // protected transient SlingRepository slingRepository;

  /**
   * {@inheritDoc}
   * 
   * @see org.apache.sling.api.servlets.SlingSafeMethodsServlet#doGet(org.apache.sling.api.SlingHttpServletRequest,
   *      org.apache.sling.api.SlingHttpServletResponse)
   */
  @Override
  protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
      throws ServletException, IOException {

    response.setCharacterEncoding("UTF-8");
    PrintWriter out = response.getWriter();
    StringBuffer resultBuffer = new StringBuffer("{\"message\":\"hellosakai\"}");
//    String result = hellosakaiService.createJSON(request, response);
    out.print(resultBuffer.toString());

  }

  /**
   * {@inheritDoc}
   * 
   * @see org.apache.sling.api.servlets.SlingSafeMethodsServlet#doPost(org.apache.sling.api.SlingHttpServletRequest,
   *      org.apache.sling.api.SlingHttpServletResponse)
   */
  @Override
  protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
      throws ServletException, IOException {
    doGet(request, response);
  }

}