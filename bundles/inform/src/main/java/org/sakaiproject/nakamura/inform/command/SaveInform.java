package org.sakaiproject.nakamura.inform.command;

import static org.sakaiproject.nakamura.api.inform.InformConstants.SAKAI_INFORM_PROPERTY_CONTENT;
import static org.sakaiproject.nakamura.api.inform.InformConstants.SAKAI_INFORM_PROPERTY_PICTUREURI;
import static org.sakaiproject.nakamura.api.inform.InformConstants.SAKAI_INFORM_PROPERTY_TITLE;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.commons.json.JSONException;
import org.sakaiproject.nakamura.api.inform.Inform;
import org.sakaiproject.nakamura.api.inform.InformException;
import org.sakaiproject.nakamura.api.inform.InformJSONUtils;
import org.sakaiproject.nakamura.api.inform.InformService;

import java.io.IOException;

import javax.jcr.Session;
import javax.servlet.http.HttpServletResponse;

/**
 * To save an inform.
 */
public class SaveInform implements Command {

  public String execute(InformService InformService, SlingHttpServletRequest request)
      throws InformException, IOException {

    Session session = request.getResourceResolver().adaptTo(Session.class);
    
    String author = request.getRemoteUser();
    String title = request.getRequestParameter(SAKAI_INFORM_PROPERTY_TITLE).getString("UTF-8");
    String content = request.getRequestParameter(SAKAI_INFORM_PROPERTY_CONTENT).getString("UTF-8");
    String pictureURI = request.getRequestParameter(SAKAI_INFORM_PROPERTY_PICTUREURI).getString("UTF-8");

    Inform inform = InformFactory.getInstanceToAdd(author, title, content, pictureURI);
    inform = InformService.store(inform, session);
    try {
      return InformJSONUtils.parseJSONString(inform);
    } catch (JSONException e) {
      e.printStackTrace();
      throw new InformException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }
}
