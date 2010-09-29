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
package org.sakaiproject.nakamura.inform;

import static org.apache.sling.jcr.resource.JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY;
import static org.sakaiproject.nakamura.api.inform.InformConstants.SAKAI_INFORM_INDEX_AMOUNT; 
import static org.sakaiproject.nakamura.api.inform.InformConstants.SAKAI_INFORM_RT; 
import static org.sakaiproject.nakamura.api.inform.InformConstants.SAKAI_INFORM_PROPERTY_AUTHOR; 
import static org.sakaiproject.nakamura.api.inform.InformConstants.SAKAI_INFORM_PROPERTY_TITLE; 
import static org.sakaiproject.nakamura.api.inform.InformConstants.SAKAI_INFORM_PROPERTY_CONTENT; 
import static org.sakaiproject.nakamura.api.inform.InformConstants.SAKAI_INFORM_PROPERTY_CREATE_TIME; 
import static org.sakaiproject.nakamura.api.inform.InformConstants.SAKAI_INFORM_PROPERTY_MODIFY_TIME; 
import static org.sakaiproject.nakamura.api.inform.InformConstants.SAKAI_INFORM_PROPERTY_PICTUREURI; 

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.sakaiproject.nakamura.api.inform.Inform;
import org.sakaiproject.nakamura.api.inform.InformConstants;
import org.sakaiproject.nakamura.api.inform.InformException;
import org.sakaiproject.nakamura.api.inform.InformNotFoundException;
import org.sakaiproject.nakamura.api.inform.InformService;
import org.sakaiproject.nakamura.inform.command.InformFactory;
import org.sakaiproject.nakamura.inform.comparator.CreateTimeDecComparator;
import org.sakaiproject.nakamura.util.JcrUtils;
import org.sakaiproject.nakamura.util.PathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.jcr.ItemNotFoundException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

/**
 * 
 */
@Component(immediate = true)
@Service(value = InformService.class)
public class InformServiceImpl implements InformService {

  public static final Logger LOGGER = LoggerFactory.getLogger(InformServiceImpl.class);

  /**
   * 
   * {@inheritDoc}
   * 
   * @see org.sakaiproject.nakamura.api.inform.InformService#get(String, Session)
   */
  public Inform get(String id, Session session) throws InformNotFoundException, InformException {
    // Start constructing the inform
    Inform inform = null;
    try {
      Node informNode = session.getNodeByIdentifier(id);
      inform = InformFactory.getInstanceByNode(informNode);
    } catch (ItemNotFoundException e) {
      LOGGER.error("Caught a ItemNotFoundException when trying to get a inform, "
          + "the cause is probably that the id is wrong, "
          + "or the node was deleted just now.", e);
      throw new InformNotFoundException(500, e.getMessage());
    } catch (RepositoryException e) {
      LOGGER.error("Caught a repositoryException when trying to get a inform, "
          + "the id of the node which store this inform is " + id, e);
      throw new InformException(500, e.getMessage());
    }
    return inform;
  }

  /**
   * 
   * {@inheritDoc}
   * 
   * @see org.sakaiproject.nakamura.api.inform.InformService#listIndex(Session)
   */
  public List<Inform> listIndex(Session session) throws InformException {
    List<Inform> informList = listAll(session);
    if (informList.size() > SAKAI_INFORM_INDEX_AMOUNT) {
      informList = informList.subList(0, SAKAI_INFORM_INDEX_AMOUNT);
    }
    return informList;
  }

  /**
   * 
   * {@inheritDoc}
   * 
   * @see org.sakaiproject.nakamura.api.inform.InformService#listAll(Session)
   */
  public List<Inform> listAll(Session session) throws InformException {
    List<Inform> informList = new ArrayList<Inform>();
    try {
      QueryManager qm = session.getWorkspace().getQueryManager();
//      QueryResult result = qm.createQuery("//" + InformConstants.SAKAI_INFORM_NODENAME, Query.XPATH).execute();
      QueryResult result = qm.createQuery(generateQueryString(), Query.XPATH).execute();

      NodeIterator nodeIterator = result.getNodes();
      while(nodeIterator.hasNext()) {
        Node node = nodeIterator.nextNode();
        Inform inform = InformFactory.getInstanceByNode(node);
        informList.add(inform);
      }
      Collections.sort(informList, new CreateTimeDecComparator());
    } catch (RepositoryException e) {
      LOGGER.error("Caught a repositoryException when trying to get all the inform", e);
      throw new InformException(500, e.getMessage());
    }
    return informList;
  }

  /**
   * {@inheritDoc}
   * 
   * @see org.sakaiproject.nakamura.api.inform.InformService#store(Inform, Session)
   */
  public Inform store(Inform inform, Session session) throws InformException {
    Node informNode = null;
    try {
      // Add a JCR node to store the inform.
      informNode = JcrUtils.deepGetOrCreateNode(session,
          getInformFolder(inform.getCreateTime())).addNode(InformConstants.SAKAI_INFORM_NODENAME);

      informNode.setProperty(SLING_RESOURCE_TYPE_PROPERTY, SAKAI_INFORM_RT);
      informNode.setProperty(SAKAI_INFORM_PROPERTY_AUTHOR, inform.getAuthor());
      informNode.setProperty(SAKAI_INFORM_PROPERTY_CONTENT, inform.getContent());
      informNode.setProperty(SAKAI_INFORM_PROPERTY_CREATE_TIME, inform.getCreateTime());
      informNode.setProperty(SAKAI_INFORM_PROPERTY_MODIFY_TIME, inform.getModifyTime());
      informNode.setProperty(SAKAI_INFORM_PROPERTY_PICTUREURI, inform.getPictureURI());
      informNode.setProperty(SAKAI_INFORM_PROPERTY_TITLE, inform.getTitle());

      // Set the inform' id to return.
      inform.setId(informNode.getIdentifier());

      // Save the entire thing.
      if (session.hasPendingChanges()) {
        session.save();
      }
    } catch (RepositoryException e) {
      LOGGER.error("Caught a repositoryException when trying to store a inform", e);
      throw new InformException(500, e.getMessage());
    }

    return inform;
  }

  /**
   * {@inheritDoc}
   * 
   * @see org.sakaiproject.nakamura.api.inform.InformService#delete(String, Session)
   */
  public Inform delete(String id, Session session) throws InformNotFoundException,
      InformException {
    Inform inform = null;
    try {
      // Get the JCR node which stored this inform to update.
      Node informNode = session.getNodeByIdentifier(id);
      inform = InformFactory.getInstanceByNode(informNode);
      // Empty the inform' id to return.
      inform.setId(null);

      // Delete the JCR node.
      informNode.remove();
      // Save the entire thing.
      if (session.hasPendingChanges()) {
        session.save();
      }
    } catch (ItemNotFoundException e) {
      LOGGER.error("Caught a ItemNotFoundException when trying to delete a inform, "
          + "the cause is probably that the id is wrong, "
          + "or the node was deleted just now.", e);
      throw new InformNotFoundException(500, e.getMessage());
    } catch (RepositoryException e) {
      LOGGER.error("Caught a repositoryException when trying to delete a inform, "
          + "the id of the node which store this inform is " + id, e);
      throw new InformException(500, e.getMessage());
    }

    return inform;
  }

  /**
   * {@inheritDoc}
   * 
   * @see org.sakaiproject.nakamura.api.inform.InformService#update(Inform, Session)
   */
  public Inform update(Inform inform, Session session) throws InformNotFoundException,
      InformException {
    try {
      // Get the JCR node which stored this inform to update.
      Node informNode = session.getNodeByIdentifier(inform.getId());

      informNode.setProperty(SAKAI_INFORM_PROPERTY_AUTHOR, inform.getAuthor());
      informNode.setProperty(SAKAI_INFORM_PROPERTY_CONTENT, inform.getContent());
      informNode.setProperty(SAKAI_INFORM_PROPERTY_MODIFY_TIME, inform.getModifyTime());
      informNode.setProperty(SAKAI_INFORM_PROPERTY_PICTUREURI, inform.getPictureURI());
      informNode.setProperty(SAKAI_INFORM_PROPERTY_TITLE, inform.getTitle());

      // Set the inform' createTime to return.
      inform.setCreateTime(informNode.getProperty(SAKAI_INFORM_PROPERTY_CREATE_TIME).getDate());

      // Save the entire thing.
      if (session.hasPendingChanges()) {
        session.save();
      }
    } catch (ItemNotFoundException e) {
      LOGGER.error("Caught a ItemNotFoundException when trying to update a inform, "
          + "the cause is probably that the id is wrong, "
          + "or the node was deleted just now.", e);
      throw new InformNotFoundException(500, e.getMessage());
    } catch (RepositoryException e) {
      LOGGER.error("Caught a repositoryException when trying to update a inform, "
          + "the id of the node which store this inform is " + inform.getId(), e);
      throw new InformException(500, e.getMessage());
    }

    return inform;
  }

  /**
   * Get the folder for a inform. It is created by the calendar of the inform, and it's
   * probably like "/_inform/2010/9/14".
   * 
   * @param c
   *          The {@link Calendar calendar} of the inform' create time.
   * @return The folder.
   */
  private String getInformFolder(Calendar c) {

    // The path to the inform.
    StringBuffer path = new StringBuffer("/");
    path.append(InformConstants.SAKAI_INFORM_HOME_NODENAME);
    path.append("/");
    path.append(c.get(Calendar.YEAR));
    path.append("/");
    path.append(1 + c.get(Calendar.MONTH));
    path.append("/");
    path.append(c.get(Calendar.DATE));

    return PathUtils.normalizePath(path.toString());
  }

  /**
   * Get the home folder for a inform. It is probably "/_inform".
   * 
   * @return The folder.
   */
  private String getInformHomeFolder() {

    // The path to the inform.
    StringBuilder path = new StringBuilder("/");
    path.append(InformConstants.SAKAI_INFORM_HOME_NODENAME);

    return PathUtils.normalizePath(path.toString());
  }
  
  /**
   * 
   * @return
   */
  private String generateQueryString() {
    StringBuffer path = new StringBuffer("//");
    path.append(InformConstants.SAKAI_INFORM_NODENAME)
      .append("[@")
      .append(SLING_RESOURCE_TYPE_PROPERTY)
      .append("='")
      .append(InformConstants.SAKAI_INFORM_RT)
      .append("']");
    
    return path.toString();
  }

}
