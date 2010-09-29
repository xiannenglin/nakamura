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

/**
 * The constants of the inform bundle.
 */
public interface InformConstants {

  /**
   * The resource type value for a inform.
   */
  public static final String SAKAI_INFORM_RT = "sakai/inform";

  /**
   * The nodename for one inform created in a user's home folder.
   */
  public static final String SAKAI_INFORM_NODENAME = "sakai:informNode";

  /**
   * The nodename for the home folder node of all the inform.
   */
  public static final String SAKAI_INFORM_HOME_NODENAME = "_inform";

  /**
   * The index amount to list informs.
   */
  public static final int SAKAI_INFORM_INDEX_AMOUNT = 5;

  /**
   * The name of the node properties of the inform.
   */
  public static final String SAKAI_INFORM_PROPERTY_AUTHOR = "author";
  public static final String SAKAI_INFORM_PROPERTY_TITLE = "title";
  public static final String SAKAI_INFORM_PROPERTY_CONTENT = "content";
  public static final String SAKAI_INFORM_PROPERTY_PICTUREURI = "pictureURI";
  public static final String SAKAI_INFORM_PROPERTY_CREATE_TIME = "createTime";
  public static final String SAKAI_INFORM_PROPERTY_MODIFY_TIME = "modifyTime";

  /**
   * The keys of JSON object of one inform.
   */
  public static final String SAKAI_INFORM_JSON_KEY_INFORM = "inform";
  public static final String SAKAI_INFORM_JSON_KEY_SUCCESS = "success";
  public static final String SAKAI_INFORM_JSON_KEY_ID = "id";
  public static final String SAKAI_INFORM_JSON_KEY_AUTHOR = "author";
  public static final String SAKAI_INFORM_JSON_KEY_TITLE = "title";
  public static final String SAKAI_INFORM_JSON_KEY_CONTENT = "content";
  public static final String SAKAI_INFORM_JSON_KEY_PICTUREURI = "pictureURI";
  public static final String SAKAI_INFORM_JSON_KEY_CREATE_TIME = "createTime";
  public static final String SAKAI_INFORM_JSON_KEY_MODIFY_TIME = "modifyTime";

  /**
   * The key of JSON array of INFORM list.
   */
  public static final String SAKAI_INFORM_JSON_KEY_LIST = "informList";

  /**
   * The method names which are the parameter of the request to the Servlet.
   */
  public static final String SAKAI_INFORM_PARAMETER_ACTION = "action";
  public static final String SAKAI_INFORM_REQUEST_METHOD_GET = "get";
  public static final String SAKAI_INFORM_REQUEST_METHOD_INDEX_LIST = "indexList";
  public static final String SAKAI_INFORM_REQUEST_METHOD_ALL_LIST = "allList";
  public static final String SAKAI_INFORM_REQUEST_METHOD_ADD = "add";
  public static final String SAKAI_INFORM_REQUEST_METHOD_UPDATE = "update";
  public static final String SAKAI_INFORM_REQUEST_METHOD_DELETE = "delete";

  /**
   * The calendar pattern string to display.
   */
  public static final String SAKAI_INFORM_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

  public static final String SAKAI_INFORM_REGISTER_KEY = "register";
}
