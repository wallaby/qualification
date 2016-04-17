package net.gojimo.toth.qualification.util;

import android.util.Log;

import net.gojimo.toth.qualification.model.Qualification;
import net.gojimo.toth.qualification.model.Subject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Parse model objects from JSON objects
 */
public class JsonHelper {
  private static final String LOGGER_TAG = "Qualifications";
  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
  private static final Pattern COLOUR_PATTERN = Pattern.compile("^#[0-9a-fA-F]{6,6}$");

  private static String getString(InputStream stream) throws IOException {
    StringBuilder inputStringBuilder = new StringBuilder();
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
    String line = bufferedReader.readLine();
    while (line != null) {
      inputStringBuilder.append(line);
      inputStringBuilder.append('\n');
      line = bufferedReader.readLine();
    }
    return inputStringBuilder.toString();
  }

  public static List<Qualification> parseQualifications(InputStream jsonStream) throws IOException {
    List<Qualification> qualifications = new ArrayList<>();
    String json = getString(jsonStream);
    try {
      JSONArray qualificationsJson = (JSONArray) new JSONTokener(json).nextValue();
      for (int i = 0; i < qualificationsJson.length(); i++) {
        try {
          JSONObject qualificationJson = qualificationsJson.getJSONObject(i);
          qualifications.add(parseQualification(qualificationJson));
        } catch (JSONException e) {
          Log.w(LOGGER_TAG, e.getMessage());
        }
      }
    } catch (JSONException e) {
      Log.e(LOGGER_TAG, e.getMessage());
    }
    return qualifications;
  }

  private static Qualification parseQualification(JSONObject qualificationJson) throws JSONException {
    String id = qualificationJson.getString("id");
    String name = qualificationJson.getString("name");
    List<Subject> subjects = parseSubjects(qualificationJson.getJSONArray("subjects"));
    Qualification qualification = new Qualification(id, name, subjects);
    try {
      Date creationDate = DATE_FORMAT.parse(qualificationJson.getString("created_at"));
      qualification.setCreated(creationDate);
    } catch (ParseException | JSONException e) {
      Log.w(LOGGER_TAG, e.getMessage());
    }
    try {
      Date updatedAt = DATE_FORMAT.parse(qualificationJson.getString("updated_at"));
      qualification.setUpdated(updatedAt);
    } catch (JSONException | ParseException e) {
      Log.w(LOGGER_TAG, e.getMessage());
    }
    return qualification;
  }

  private static List<Subject> parseSubjects(JSONArray subjectsJson) throws JSONException{
    List<Subject> subjects = new ArrayList<>();
    for (int i = 0; i < subjectsJson.length(); i++) {
      try {
        JSONObject qualificationJson = subjectsJson.getJSONObject(i);
        subjects.add(parseSubject(qualificationJson));
      } catch (JSONException e) {
        Log.w(LOGGER_TAG, e.getMessage());
      }
    }
    return subjects;
  }

  private static Subject parseSubject(JSONObject subjectJson) throws JSONException {
    String id = subjectJson.getString("id");
    String title = subjectJson.getString("title");
    Subject subject = new Subject(id, title);

    try {
      String colour = subjectJson.getString("colour");
      if (colour != null && COLOUR_PATTERN.matcher(colour).matches()) {
        subject.setColour(colour);
      }
    } catch (JSONException e) {
      Log.w(LOGGER_TAG, e.getMessage());
    }
    return subject;
  }
}
