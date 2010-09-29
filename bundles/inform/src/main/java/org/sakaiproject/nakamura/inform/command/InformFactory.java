package org.sakaiproject.nakamura.inform.command;

import static org.apache.sling.jcr.resource.JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY;
import static org.sakaiproject.nakamura.api.inform.InformConstants.SAKAI_INFORM_RT;
import static org.sakaiproject.nakamura.api.inform.InformConstants.SAKAI_INFORM_PROPERTY_AUTHOR;
import static org.sakaiproject.nakamura.api.inform.InformConstants.SAKAI_INFORM_PROPERTY_TITLE;
import static org.sakaiproject.nakamura.api.inform.InformConstants.SAKAI_INFORM_PROPERTY_CONTENT;
import static org.sakaiproject.nakamura.api.inform.InformConstants.SAKAI_INFORM_PROPERTY_CREATE_TIME;
import static org.sakaiproject.nakamura.api.inform.InformConstants.SAKAI_INFORM_PROPERTY_MODIFY_TIME;
import static org.sakaiproject.nakamura.api.inform.InformConstants.SAKAI_INFORM_PROPERTY_PICTUREURI;


import org.sakaiproject.nakamura.api.inform.Inform;
import org.sakaiproject.nakamura.api.inform.InformException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

/**
 * Create {@link Inform inform} instance.
 */
public class InformFactory {

  
  public static final Logger LOGGER = LoggerFactory.getLogger(InformFactory.class);
  
  /**
   * Create an empty instance of {@link Inform inform}.
   * 
   * @return Empty instance of {@link Inform inform}.
   */
  public static Inform emptyInstance() {

    Inform inform = new Inform();
    inform.setId("");
    inform.setAuthor("");
    inform.setTitle("");
    inform.setContent("");
    inform.setPictureURI("");

    return inform;
  }

  /**
   * Create an instance of {@link Inform inform} to add, using author, title, content, and
   * pictureURI. This field createTime and modifyTime of this instance are both the
   * current time.
   * 
   * @param author
   * @param title
   * @param content
   * @param pictureURI
   * @return Instance of {@link Inform inform}.
   */
  public static Inform getInstanceToAdd(String author, String title, String content,
      String pictureURI) {

    Inform inform = new Inform();
    inform.setAuthor(author);
    inform.setTitle(title);
    inform.setContent(content);
    inform.setPictureURI(pictureURI);
    inform.setCreateTime(Calendar.getInstance());
    inform.setModifyTime(inform.getCreateTime());

    return inform;
  }

  /**
   * Create an instance of {@link Inform inform} to update, using id, author, title, content,
   * and pictureURI. This field createTime of this instance is null, and modifyTime is the
   * current time.
   * 
   * @param id
   * @param author
   * @param title
   * @param content
   * @param pictureURI
   * @return Instance of {@link Inform inform}.
   */
  public static Inform getInstanceToUpdate(String id, String author, String title,
      String content, String pictureURI) {

    Inform inform = new Inform();
    inform.setId(id);
    inform.setAuthor(author);
    inform.setTitle(title);
    inform.setContent(content);
    inform.setPictureURI(pictureURI);
    inform.setModifyTime(Calendar.getInstance());
    
    return inform;
  }

  /**
   * Create an instance of {@link Inform inform} using a JCR {@link Node node}.
   * 
   * @param informNode
   * @return Instance of {@link Inform inform}.
   * @throws InformException
   *           The resource type of the JCR {@link Node node} is not the resource type of
   *           {@link Inform inform}, or caught a repositoryException was threw when trying
   *           to get the properties of the JCR {@link Node node}.
   */
  public static Inform getInstanceByNode(Node informNode) throws InformException {

    try {
      if (!SAKAI_INFORM_RT.equals(informNode.getProperty(SLING_RESOURCE_TYPE_PROPERTY).getString())) {
        throw new InformException(500,
            "The node to struct the inform has wrong resource type.");
      }

      Inform inform = new Inform();
      inform.setId(informNode.getIdentifier());
      inform.setAuthor(informNode.getProperty(SAKAI_INFORM_PROPERTY_AUTHOR).getString());
      inform.setTitle(informNode.getProperty(SAKAI_INFORM_PROPERTY_TITLE).getString());
      inform.setContent(informNode.getProperty(SAKAI_INFORM_PROPERTY_CONTENT).getString());
      inform.setCreateTime(informNode.getProperty(SAKAI_INFORM_PROPERTY_CREATE_TIME).getDate());
      inform.setModifyTime(informNode.getProperty(SAKAI_INFORM_PROPERTY_MODIFY_TIME).getDate());
      inform.setPictureURI(informNode.getProperty(SAKAI_INFORM_PROPERTY_PICTUREURI).getString());

      return inform;
    } catch (RepositoryException e) {
      LOGGER.error("Caught a repositoryException when struct a inform by a node", e);
      throw new InformException(500, e.getMessage());
    }
  }
}
