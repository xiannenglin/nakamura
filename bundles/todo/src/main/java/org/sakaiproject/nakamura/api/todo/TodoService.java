package org.sakaiproject.nakamura.api.todo;

import org.apache.sling.api.SlingHttpServletRequest;

/**
 * A service that allows one to fetch and store calendars.
 */
public interface TodoService {

	String clear(SlingHttpServletRequest request);

	String list(SlingHttpServletRequest request);

	String save(SlingHttpServletRequest request);

}
