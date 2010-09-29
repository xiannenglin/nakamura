package org.sakaiproject.nakamura.inform.command;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.commons.json.JSONException;
import org.sakaiproject.nakamura.api.inform.Inform;
import org.sakaiproject.nakamura.api.inform.InformException;
import org.sakaiproject.nakamura.api.inform.InformJSONUtils;
import org.sakaiproject.nakamura.api.inform.InformService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.jcr.Session;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 */
public class ListIndexInform implements Command {

  public String execute(InformService informService, SlingHttpServletRequest request)
      throws InformException, IOException {

    List<Inform> informList = new ArrayList<Inform>();
    Session session = request.getResourceResolver().adaptTo(Session.class);
    informList = informService.listIndex(session);
    try {
      return InformJSONUtils.parseJSONString(informList);
    } catch (JSONException e) {
      e.printStackTrace();
      throw new InformException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }
}
