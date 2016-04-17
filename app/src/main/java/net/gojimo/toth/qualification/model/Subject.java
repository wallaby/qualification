package net.gojimo.toth.qualification.model;

/**
 * A subject
 */
public class Subject {
  private static final String DEFAULT_COLOUR = "#FFFFFF";
  private final String id;
  private final String title;
  private String colour;

  public Subject(String id, String title) {
    this.id = id;
    this.title = title;
    this.colour = DEFAULT_COLOUR;
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
