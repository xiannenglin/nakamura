package org.sakaiproject.nakamura.api.inform;


import java.util.Calendar;

public class Inform{

  private String id;
  private String author;
  private String title;
  private String content;
  private String pictureURI;
  private Calendar createTime;
  private Calendar modifyTime;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getPictureURI() {
    return pictureURI;
  }

  public void setPictureURI(String pictureURI) {
    this.pictureURI = pictureURI;
  }

  public Calendar getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Calendar createTime) {
    this.createTime = createTime;
  }

  public Calendar getModifyTime() {
    return modifyTime;
  }

  public void setModifyTime(Calendar modifyTime) {
    this.modifyTime = modifyTime;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    try {
      Inform that = (Inform) obj;
      return id.equals(that.getId()) && author.equals(that.getAuthor())
          && title.equals(that.getTitle()) && content.equals(that.getContent())
          && pictureURI.equals(that.getPictureURI())
          && createTime.equals(that.getCreateTime())
          && modifyTime.equals(that.getModifyTime());
    } catch (Exception e) {
      // Note: GregorianCalendar.computeTime throws
      // IllegalArgumentException if the ERA value is invalid
      // even it's in lenient mode.
    }
    return false;
  }
}
