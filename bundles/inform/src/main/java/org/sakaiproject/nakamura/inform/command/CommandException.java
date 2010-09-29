package org.sakaiproject.nakamura.inform.command;
/**
 * when a command is not find ,then throws an{@link CommandException} instance
 */
public class CommandException extends Exception{

  private static final long serialVersionUID = 6577644235417290570L;

  private int code;
  private String message;
  
  public CommandException() {
  }
  public CommandException(int code, String message) {
    super();
    setCode(code);
    setMessage(message);
  }
  public int getCode() {
    return code;
  }
  public void setCode(int code) {
    this.code = code;
  }
  public String getMessage() {
    return message;
  }
  public void setMessage(String message) {
    this.message = message;
  }
  
}
