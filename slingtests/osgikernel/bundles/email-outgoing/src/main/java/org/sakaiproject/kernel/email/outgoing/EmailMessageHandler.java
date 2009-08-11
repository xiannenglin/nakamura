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
package org.sakaiproject.kernel.email.outgoing;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.sling.jcr.api.SlingRepository;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.sakaiproject.kernel.api.message.MessageConstants;
import org.sakaiproject.kernel.api.message.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

/**
 * Handler for messages that are intended for email delivery. Needs to be started
 * immediately to make sure it registers with JCR as soon as possible.
 */
@Component(label = "%external.message.handler.name", description = "%external.message.handler.description", immediate = true)
public class EmailMessageHandler implements MessageHandler {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(EmailMessageHandler.class);

  @Reference
  protected SlingRepository slingRepository;

  @Reference
  protected EventAdmin eventAdmin;

  private static final String TYPE = MessageConstants.TYPE_EMAIL;

  public String getType() {
    return TYPE;
  }

  public void handle(Event event, Node node) {
    LOGGER.debug("Started handling an email message");
    Properties props = new Properties();
    try {
      props.put(OutgoingEmailMessageListener.NODE_PATH_PROPERTY, node.getPath());
      Event emailEvent = new Event(OutgoingEmailMessageListener.QUEUE_NAME, props);
      eventAdmin.postEvent(emailEvent);
    } catch (RepositoryException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

}
