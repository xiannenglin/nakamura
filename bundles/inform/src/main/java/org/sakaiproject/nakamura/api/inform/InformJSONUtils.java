package org.sakaiproject.nakamura.api.inform;

import static org.sakaiproject.nakamura.api.inform.InformConstants.SAKAI_INFORM_TIME_PATTERN;
import static org.sakaiproject.nakamura.api.inform.InformConstants.SAKAI_INFORM_JSON_KEY_ID;
import static org.sakaiproject.nakamura.api.inform.InformConstants.SAKAI_INFORM_JSON_KEY_AUTHOR;
import static org.sakaiproject.nakamura.api.inform.InformConstants.SAKAI_INFORM_JSON_KEY_TITLE;
import static org.sakaiproject.nakamura.api.inform.InformConstants.SAKAI_INFORM_JSON_KEY_CONTENT;
import static org.sakaiproject.nakamura.api.inform.InformConstants.SAKAI_INFORM_JSON_KEY_PICTUREURI;
import static org.sakaiproject.nakamura.api.inform.InformConstants.SAKAI_INFORM_JSON_KEY_CREATE_TIME;
import static org.sakaiproject.nakamura.api.inform.InformConstants.SAKAI_INFORM_JSON_KEY_MODIFY_TIME;
import static org.sakaiproject.nakamura.api.inform.InformConstants.SAKAI_INFORM_JSON_KEY_SUCCESS;
import static org.sakaiproject.nakamura.api.inform.InformConstants.SAKAI_INFORM_JSON_KEY_INFORM;
import static org.sakaiproject.nakamura.api.inform.InformConstants.SAKAI_INFORM_JSON_KEY_LIST;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * JSON utility for {@link Inform}
 * 
 * @author xiannenglin
 */
public class InformJSONUtils {

  /**
   * To parse a rm instance to a JSONObject.
   * 
   * @param inform
   * @return
   * @throws JSONException
   */
  private static JSONObject parseJSONObject(Inform inform) throws JSONException {

    SimpleDateFormat sf = new SimpleDateFormat(SAKAI_INFORM_TIME_PATTERN);
    JSONObject jsonObject = new JSONObject();

    jsonObject.put(SAKAI_INFORM_JSON_KEY_ID, inform.getId())
              .put(SAKAI_INFORM_JSON_KEY_AUTHOR,inform.getAuthor())
              .put(SAKAI_INFORM_JSON_KEY_TITLE, inform.getTitle())
              .put(SAKAI_INFORM_JSON_KEY_CONTENT, inform.getContent())
              .put(SAKAI_INFORM_JSON_KEY_PICTUREURI, inform.getPictureURI())
              .put(SAKAI_INFORM_JSON_KEY_CREATE_TIME, sf.format(inform.getCreateTime().getTime()))
              .put(SAKAI_INFORM_JSON_KEY_MODIFY_TIME, sf.format(inform.getModifyTime().getTime()));
    return jsonObject;
  }

  /**
   * To parse an Inform instance to a json string. e.g. 
   * { 
   *  "success":true, 
   *  informsList":
   *            {
   *            "id":"0012",
   *            "author":"xiannenglin",
   *            "title":"This is the title",
   *            "content":"This is the content",
   *            "pictureURI":"/upload/pic/",
   *            "createTime":"2010-08-09 13:12:30",
   *            "createTime":"2010-08-09 13:12:30" 
   *            }
   * }
   * 
   * @param inform
   * @return
   * @throws JSONException
   */
  public static String parseJSONString(Inform inform) throws JSONException {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put(SAKAI_INFORM_JSON_KEY_SUCCESS, true)
              .put(SAKAI_INFORM_JSON_KEY_INFORM,parseJSONObject(inform));
    return jsonObject.toString();
  }

  /**
   * To parse a List<Inform> to a json string. e.g. { "success":true, "informList":[ {
   * "id":"0012", "author":"xiannenglin", "title":"This is the title", "content":"This is
   * the content", "pictureURI":"/upload/pic/1", "createTime":"2010-08-09 13:12:30",
   * "createTime":"2010-08-09 13:12:30" }, { "id":"0013", "author":"xiannenglin",
   * "title":"This second title", "content":"This is the content",
   * "pictureURI":"/upload/pic/2", "createTime":"2010-08-12 15:13:34",
   * "createTime":"2010-08-13 13:12:30" } ] }
   * 
   * @param informList
   * @return
   * @throws JSONException
   */
  public static String parseJSONString(List<Inform> informList) throws JSONException {
    JSONArray jsonArray = new JSONArray();
    JSONObject jsonObject = new JSONObject();
    for (Inform inform : informList) {
      jsonArray.put(parseJSONObject(inform));
    }
    jsonObject.put(SAKAI_INFORM_JSON_KEY_SUCCESS, true)
              .put(SAKAI_INFORM_JSON_KEY_LIST,jsonArray);
    return jsonObject.toString();
  }

  /**
   * Generate a special json string when parsing json string failed.
   * 
   * @return {"success" : false}
   */
  public static String parseFalseJSONString() {
    return "{\"" + SAKAI_INFORM_JSON_KEY_SUCCESS + "\":" + false + "}";
  }
}
