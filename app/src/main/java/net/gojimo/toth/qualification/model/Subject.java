package net.gojimo.toth.qualification.model;

import android.graphics.Color;

/**
 * A subject
 */
public class Subject {
  private final String id;
  private final String title;
  private String colour;

  public Subject(String id, String title) {
    this.id = id;
    this.title = title;
  }

  public String getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getColour() {
    return colour;
  }

  public void setColour(String colour) {
    this.colour = colour;
  }
}
