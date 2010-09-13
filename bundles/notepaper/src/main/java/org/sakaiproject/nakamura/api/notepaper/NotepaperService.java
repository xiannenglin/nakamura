package org.sakaiproject.nakamura.api.notepaper;

import org.apache.sling.api.SlingHttpServletRequest;

/**
 * A service that allows one to fetch and store notepapers.
 */
public interface NotepaperService {
  
  Notepaper save(SlingHttpServletRequest request);

	Notepaper list(SlingHttpServletRequest request);
}
