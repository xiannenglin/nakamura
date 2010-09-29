package org.sakaiproject.nakamura.inform.comparator;

import org.sakaiproject.nakamura.api.inform.Inform;

import java.io.Serializable;
import java.util.Comparator;

/**
 * This class will be used to sort the {@link Inform inform}, it can let it sorted decreased
 * by the create time of the {@link Inform inform}.
 * 
 * @author YaodanZhang
 */
public class CreateTimeDecComparator implements Comparator<Inform>, Serializable {

  private static final long serialVersionUID = -5049730900022357829L;

  public int compare(Inform inform1, Inform inform2) {
    return inform2.getCreateTime().compareTo(inform1.getCreateTime());
  }

}
