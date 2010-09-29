package org.sakaiproject.nakamura.inform;

import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.sakaiproject.nakamura.api.inform.InformConstants;
import org.sakaiproject.nakamura.api.inform.InformException;
import org.sakaiproject.nakamura.api.inform.InformService;
import org.sakaiproject.nakamura.api.user.UserConstants;
import org.sakaiproject.nakamura.inform.command.Command;
import org.sakaiproject.nakamura.inform.command.CommandException;
import org.sakaiproject.nakamura.inform.command.CommandFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

@SlingServlet(methods = { "GET", "POST" }, paths = { "/system/inform" }, generateComponent = true, generateService = true)
public class InformServlet extends SlingAllMethodsServlet implements InformConstants {

  private static final long serialVersionUID = -7823822968522622983L;

  public static final Logger LOGGER = LoggerFactory.getLogger(InformServlet.class);
  
  @Reference
  protected transient InformService informService;
  
  /**
   * {@inheritDoc}
   * 
   * @see org.apache.sling.api.servlets.SlingSafeMethodsServlet#doGet(org.apache.sling.api.SlingHttpServletRequest,
   *      org.apache.sling.api.SlingHttpServletResponse)
   */
  @Override
  protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
      throws ServletException, IOException {

    String result = null;

    if (request.getRemoteUser().equals(UserConstants.ANON_USERID)) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
          "Anonymous users can't get the inform.");
      LOGGER.error("Anonymous users can't get the inform.");
      return;
    } else {
      String action = request.getParameter(InformConstants.SAKAI_INFORM_PARAMETER_ACTION);
      try {
        Command command = CommandFactory.createReadCommand(action);
        result = command.execute(informService, request);
      } catch (InformException e) {
        e.printStackTrace();
        response.sendError(500, "Error when creating Inform");
        LOGGER.error("Error when creating Inform");
      } catch (CommandException e) {
        e.printStackTrace();
        response.sendError(500, "Error when create an instance of Command");
        LOGGER.error("Error when create an instance of Command");
      }
    }
    response.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
    PrintWriter out = response.getWriter();
    out.print(result);
  }

  /**
   * {@inheritDoc}
   * 
   * @see org.apache.sling.api.servlets.SlingSafeMethodsServlet#doPost(org.apache.sling.api.SlingHttpServletRequest,
   *      org.apache.sling.api.SlingHttpServletResponse)
   */
  @Override
  protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
      throws ServletException, IOException {

    if (!request.getRemoteUser().equals(UserConstants.ADMIN_USERID)) {
      response.sendError(HttpServletResponse.SC_FORBIDDEN,
          "Common or Anonymous users can't save or edit the inform");
      LOGGER.error("Common or Anonymous users can't save or edit the inform");
      return;
    }

    String result = null;
    String action = request.getParameter(SAKAI_INFORM_PARAMETER_ACTION);
    try {
      Command command = CommandFactory.createWriteCommand(action);
      result = command.execute(informService, request);
    } catch (CommandException e) {
      response.sendError(500, "Error when create an instance of Command");
      e.printStackTrace();
      LOGGER.error("Error when create an instance of Command");
    } catch (InformException e) {
      response.sendError(500, "Error when create an instance of Inform using a JCR Node");
      e.printStackTrace();
      LOGGER.error("Error when creating Inform");
    }
    response.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
    PrintWriter out = response.getWriter();
    out.print(result);
  }

}