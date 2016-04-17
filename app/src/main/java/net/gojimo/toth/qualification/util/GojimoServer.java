package net.gojimo.toth.qualification.util;

import net.gojimo.toth.qualification.model.Qualification;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Singleton class to access Gojimo webservice
 */
public class GojimoServer {
  private static GojimoServer gojimoServer;
  private static final String LOGGER_TAG = "GojimoServer";
  private static final String SERVER_ROOT = "https://api.gojimo.net";
  private static final String QUALIFICATIONS_PATH = "/api/v4/qualifications";
  private Map<String, Qualification> qualificationMap = new HashMap<>();

  private GojimoServer() {}

  /**
   * Get singleton instance of this class
   *
   * @return singleton instance
   */
  public static GojimoServer getGojimoServer() {
    if (gojimoServer == null) {
      gojimoServer = new GojimoServer();
    }
    return gojimoServer;
  }

  /**
   * Get the list of qualifications available on the server
   *
   * This is a blocking network call, should be called from worker thread.
   * @return a list containing the qualifications
   */
  public List<Qualification> getQualifications() throws IOException {
    URL url = new URL(SERVER_ROOT + QUALIFICATIONS_PATH);
    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
    List<Qualification> qualifications = JsonHelper.parseQualifications(in);
    for (Qualification qualification : qualifications) {
      qualificationMap.put(qualification.getId(), qualification);
    }
    return qualifications;
  }

  public Qualification getQualification(String id) {
    return qualificationMap.get(id);
  }
}
