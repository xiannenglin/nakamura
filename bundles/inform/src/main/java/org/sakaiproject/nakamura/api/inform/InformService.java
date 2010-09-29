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
package org.sakaiproject.nakamura.api.inform;


import java.util.List;

import javax.jcr.Node;
import javax.jcr.Repository;
import javax.jcr.Session;

/**
 * A service that allows one to fetch and store inform.
 */
public interface InformService {

  /**
   * Get one {@link Inform inform} by its id.
   * 
   * @param id
   *          The id of the {@link Inform inform}.
   * @param session
   *          The JCR {@link Session session} that is the session to access this JCR
   *          {@link Repository repository}.
   * @return The {@link Inform inform} that one want to get.
   * @throws InformNotFoundException
   *           The {@link Inform inform} of this id was not found in the JCR
   *           {@link Repository repository}.
   * @throws InformException
   *           Failed to get the {@link Inform inform} from the JCR {@link Repository
   *           repository}.
   */
  Inform get(String id, Session session) throws InformNotFoundException, InformException;

  /**
   * Get the list of lastest 5 (defined by {@link InformConstants.SAKAI_NEWS_INDEX_AMOUNT})
   * {@link Inform inform}. The inform in this list contains only id, title, and pictureURI.
   * 
   * @param session
   *          The JCR {@link Session session} that is the session to access this JCR
   *          {@link Repository repository}.
   * @return The list of {@link Inform inform} that added recently.
   * @throws InformException
   *           Failed to get the list of {@link Inform inform} from the JCR {@link Repository
   *           repository}.
   */
  List<Inform> listIndex(Session session) throws InformException;

  /**
   * Get the list of all the {@link Inform inform}. The inform in this list contains all the
   * message of the inform.
   * 
   * @param session
   *          The JCR {@link Session session} that is the session to access this JCR
   *          {@link Repository repository}.
   * @return The list of all the {@link Inform inform}.
   * @throws InformException
   *           Failed to get the list of {@link Inform inform} from the JCR {@link Repository
   *           repository}.
   */
  List<Inform> listAll(Session session) throws InformException;

  /**
   * Creates a JCR based representation of a {@link Inform inform}.
   * 
   * @param inform
   *          The inform to create in JCR {@link Node nodes}.
   * @param session
   *          The session that allows access to the JCR repository.
   * @return The top inform {@link Node node}.
   * @throws InformException
   *           Something went wrong trying to create the JCR based representation.
   */
  Inform store(Inform inform, Session session) throws InformException;

  /**
   * Update one {@link Inform inform}. This method may change the title, content, or
   * pictureURI, will change the modifyTime, and will not change the id, createTime, and
   * author.
   * 
   * @param inform
   *          The {@link Inform inform} to be updated.
   * @param session
   *          The JCR {@link Session session} that is the session to access this JCR
   *          {@link Repository repository}.
   * @return The {@link Inform inform} that one want to update.
   * @throws InformNotFoundException
   *           The {@link Inform inform} was not found in the JCR {@link Repository
   *           repository}.
   * @throws InformException
   *           Failed to get the {@link Inform inform} from the JCR {@link Repository
   *           repository}.
   */
  Inform update(Inform inform, Session session) throws InformNotFoundException, InformException;

  /**
   * Delete one {@link Inform inform} by its id.
   * 
   * @param id
   *          The id of the {@link Inform inform}.
   * @param session
   *          The JCR {@link Session session} that is the session to access this JCR
   *          {@link Repository repository}.
   * @return The {@link Inform inform} that has just been deleted, it will not contain the id.
   * @throws InformNotFoundException
   *           The {@link Inform inform} of this id was not found in the JCR
   *           {@link Repository repository}.
   * @throws InformException
   *           Failed to get the {@link Inform inform} from the JCR {@link Repository
   *           repository}.
   */
  Inform delete(String id, Session session) throws InformNotFoundException, InformException;
  
}
