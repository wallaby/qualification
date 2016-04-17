package net.gojimo.toth.qualification.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A qualification
 */
public class Qualification {
  private final String id;
  private final String name;
  private Date created;
  private Date updated;
  private List<Subject> subjects = new ArrayList<>();

  public Qualification(String id, String name, List<Subject> subjects) {
    this.id = id;
    this.name = name;
    this.subjects.addAll(subjects);
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

  public void addSubject(Subject subject) {
    subjects.add(subject);
  }

  public List<Subject> getSubjects() {
    return subjects;
  }
}
