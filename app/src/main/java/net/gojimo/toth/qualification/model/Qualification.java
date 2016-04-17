package net.gojimo.toth.qualification.model;

import java.util.Date;

/**
 * A qualification
 */
public class Qualification {
  private final String id;
  private final String name;
  private Date created;
  private Date updated;

  public Qualification(String id, String name) {
    this.id = id;
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public Date getUpdated() {
    return updated;
  }

  public void setUpdated(Date updated) {
    this.updated = updated;
  }
}
