package org.sakaiproject.nakamura.inform.command;

import static org.sakaiproject.nakamura.api.inform.InformConstants.SAKAI_INFORM_JSON_KEY_ID;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.commons.json.JSONException;
import org.sakaiproject.nakamura.api.inform.Inform;
import org.sakaiproject.nakamura.api.inform.InformException;
import org.sakaiproject.nakamura.api.inform.InformJSONUtils;
import org.sakaiproject.nakamura.api.inform.InformNotFoundException;
import org.sakaiproject.nakamura.api.inform.InformService;

import java.io.IOException;

import javax.jcr.Session;
import javax.servlet.http.HttpServletResponse;

/**
 * To get a inform.
 */
public class GetInform implements Command {

  public String execute(InformService informService, SlingHttpServletRequest request)
      throws InformException, IOException {

    String id = request.getParameter(SAKAI_INFORM_JSON_KEY_ID);
    Session session = request.getResourceResolver().adaptTo(Session.class);
    Inform inform = null;
    try {
      inform = informService.get(id, session);
    } catch (InformNotFoundException e) {
      return InformJSONUtils.parseFalseJSONString();
    }
    try {
      return InformJSONUtils.parseJSONString(inform);
    } catch (JSONException e) {
      e.printStackTrace();
      throw new InformException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }
}
