package org.sakaiproject.nakamura.inform.command;

import org.apache.sling.api.SlingHttpServletRequest;
import org.sakaiproject.nakamura.api.inform.InformException;
import org.sakaiproject.nakamura.api.inform.InformService;

import java.io.IOException;

/**
 * The interface of all commands.
 */
public interface Command {

  /**
   * 
   * @param informService
   * @param request
   * @return
   * @throws NewsException
   * @throws IOException
   */
  public String execute(InformService informService, SlingHttpServletRequest request) throws InformException, IOException;

}
