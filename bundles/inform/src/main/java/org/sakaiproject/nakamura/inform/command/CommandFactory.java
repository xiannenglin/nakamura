package org.sakaiproject.nakamura.inform.command;

import org.sakaiproject.nakamura.api.inform.InformConstants;


/**
 * 
 */
public class CommandFactory {

  /**
   * only the "admin" can write(store, update, delete) inform
   * @param action
   * @return
   * @throws CommandException TODO
   */
  public static Command createWriteCommand(String action) throws CommandException {
    Command command = null;

    if (action.equalsIgnoreCase(InformConstants.SAKAI_INFORM_REQUEST_METHOD_ADD)) {
      command = new SaveInform();
    } else if (action.equalsIgnoreCase(InformConstants.SAKAI_INFORM_REQUEST_METHOD_DELETE)) {
      command = new DeleteInform();
    } else if (action.equalsIgnoreCase(InformConstants.SAKAI_INFORM_REQUEST_METHOD_UPDATE)) {
      command = new UpdateInform();
    } 
    return command;
  }
  
  /**
   * 
   * @param action
   * @return
   * @throws CommandException TODO
   */
  public static Command createReadCommand(String action) throws CommandException {
    Command command = null;

    if (action.equalsIgnoreCase(InformConstants.SAKAI_INFORM_REQUEST_METHOD_ALL_LIST)) {
      command = new ListAllInform();
    } else if (action.equalsIgnoreCase(InformConstants.SAKAI_INFORM_REQUEST_METHOD_INDEX_LIST)) {
      command = new ListIndexInform();
    } else if (action.equalsIgnoreCase(InformConstants.SAKAI_INFORM_REQUEST_METHOD_GET)) {
      command = new GetInform();
    }
    return command;
  }
}
