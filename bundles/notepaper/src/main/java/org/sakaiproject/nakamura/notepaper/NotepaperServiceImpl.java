package org.sakaiproject.nakamura.notepaper;

import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.request.RequestParameter;
import org.sakaiproject.nakamura.api.notepaper.Notepaper;
import org.sakaiproject.nakamura.api.notepaper.NotepaperConstants;
import org.sakaiproject.nakamura.api.notepaper.NotepaperService;
import org.sakaiproject.nakamura.api.personal.PersonalUtils;
import org.sakaiproject.nakamura.util.JcrUtils;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

public class NotepaperServiceImpl implements NotepaperService {

  public Notepaper list(SlingHttpServletRequest request) {
    String message = "";
    String modifyTime = "";
    Session session = request.getResourceResolver().adaptTo(Session.class);
    try {
      Authorizable au = PersonalUtils.getAuthorizable(session, request.getRemoteUser());
      String notePath = PersonalUtils.getHomeFolder(au) + "/"
          + NotepaperConstants.SAKAI_NOTEPAPER_RT;
      Node noteNode = JcrUtils.deepGetOrCreateNode(session, notePath);
      message = (noteNode.getProperty(NotepaperConstants.SAKAI_NOTEPAPER_MESSAGE) == null || noteNode.getProperty(
          NotepaperConstants.SAKAI_NOTEPAPER_MESSAGE).getString().equals("")) ? "" : new String(noteNode.getProperty(
          NotepaperConstants.SAKAI_NOTEPAPER_MESSAGE).getString().getBytes("ISO-8859-1"), "UTF-8");
      modifyTime = (noteNode.getProperty(NotepaperConstants.SAKAI_NOTEPAPER_MODIFYTIME) == null || noteNode.getProperty("time")
          .equals("")) ? "" : noteNode.getProperty(NotepaperConstants.SAKAI_NOTEPAPER_MODIFYTIME).getString();
    } catch (PathNotFoundException e) {
      e.printStackTrace();
    } catch (RepositoryException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    Notepaper notepaper = new Notepaper();
    notepaper.setMessage(message);
    notepaper.setModifyTime(modifyTime);
    return notepaper;
  }

  public Notepaper save(SlingHttpServletRequest request) {
    RequestParameter parameter = request.getRequestParameter(NotepaperConstants.SAKAI_NOTEPAPER_MESSAGE);
    Session session = request.getResourceResolver().adaptTo(Session.class);
    Notepaper notepaper = new Notepaper();
    try {
      Authorizable au = PersonalUtils.getAuthorizable(session, request.getRemoteUser());
      String homefolder = PersonalUtils.getHomeFolder(au); 
      String notePath = homefolder + "/" + NotepaperConstants.SAKAI_NOTEPAPER_RT;
      Node noteNode = JcrUtils.deepGetOrCreateNode(session, notePath);
      
      String message = parameter.getString("UTF-8");
      noteNode.setProperty(NotepaperConstants.SAKAI_NOTEPAPER_MESSAGE, message);
      noteNode.setProperty(NotepaperConstants.SAKAI_NOTEPAPER_MODIFYTIME, getCurrentTime());
     
      notepaper.setMessage(message);
      notepaper.setModifyTime(getCurrentTime());
      session.save();
    } catch (RepositoryException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return notepaper;
  }

  private static String getCurrentTime() {
    Date date = new Date();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return format.format(date);
  }
}