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

package org.sakaiproject.nakamura.api.search;

import org.apache.sling.api.SlingHttpServletRequest;

import java.util.List;

import javax.jcr.query.Query;
import javax.jcr.query.Row;
import javax.jcr.query.RowIterator;

public interface SearchServiceFactory {

  /**
   * Creates a merged Row Iterator from 2 iterators.
   * 
   * @param iteratorA
   * @param iteratorB
   * @return
   */
  public RowIterator getMergedRowIterator(RowIterator iteratorA, RowIterator iteratorB);

  /**
   * Gets a Row Iterator filtered for protected paths.
   * 
   * @param rowIterator
   * @return
   */
  public RowIterator getPathFilteredRowIterator(RowIterator rowIterator);

  /**
   * Create a Search Result Set from a row iterator.
   * @param rowIterator
   * @param size
   * @return
   */
  public SearchResultSet getSearchResultSet(RowIterator mergedIterator, long totalHits);

  /**
   * This method will return a SearchResultSet that contains a paged rowIterator and the
   * total hit count from Lucene.
   * 
   * @param request
   * @param query
   * @return
   * @throws SearchException
   */
  public SearchResultSet getSearchResultSet(SlingHttpServletRequest request, Query query)
      throws SearchException;

  public RowIterator getRowIteratorFromList(List<Row> savedRows);

}
