package org.sakaiproject.nakamura.inform.command;

import static org.sakaiproject.nakamura.api.inform.InformConstants.SAKAI_INFORM_JSON_KEY_ID;
import static org.sakaiproject.nakamura.api.inform.InformConstants.SAKAI_INFORM_PROPERTY_AUTHOR;
import static org.sakaiproject.nakamura.api.inform.InformConstants.SAKAI_INFORM_PROPERTY_CONTENT;
import static org.sakaiproject.nakamura.api.inform.InformConstants.SAKAI_INFORM_PROPERTY_PICTUREURI;
import static org.sakaiproject.nakamura.api.inform.InformConstants.SAKAI_INFORM_PROPERTY_TITLE;

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
 * To update an inform.
 */
public class UpdateInform implements Command {

  public String execute(InformService informService, SlingHttpServletRequest request)
      throws InformException, IOException {

    Session session = request.getResourceResolver().adaptTo(Session.class);

    String id = request.getRequestParameter(SAKAI_INFORM_JSON_KEY_ID).getString("UTF-8");
    String author = request.getRequestParameter(SAKAI_INFORM_PROPERTY_AUTHOR).getString("UTF-8");
    String title = request.getRequestParameter(SAKAI_INFORM_PROPERTY_TITLE).getString("UTF-8");
    String content = request.getRequestParameter(SAKAI_INFORM_PROPERTY_CONTENT).getString("UTF-8");
    String pictureURI = request.getRequestParameter(SAKAI_INFORM_PROPERTY_PICTUREURI).getString("UTF-8");

    Inform inform = InformFactory.getInstanceToUpdate(id, author, title, content, pictureURI);
    try {
      inform = informService.update(inform, session);
    } catch (InformNotFoundException e) {
      return InformJSONUtils.parseFalseJSONString();
    }
    // notify the observers registered to the subject to update
    inform.notifyObservers(session);
    try {
      return InformJSONUtils.parseJSONString(inform);
    } catch (JSONException e) {
      e.printStackTrace();
      throw new InformException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }
}