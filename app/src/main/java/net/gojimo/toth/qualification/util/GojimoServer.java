package net.gojimo.toth.qualification.util;

import net.gojimo.toth.qualification.model.Qualification;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Singleton class to access Gojimo webservice
 */
public class GojimoServer {
  private static GojimoServer gojimoServer;
  private static final String LOGGER_TAG = "GojimoServer";
  private static final String SERVER_ROOT = "https://api.gojimo.net";
  private static final String QUALIFICATIONS_PATH = "/api/v4/qualifications";

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
    List<Qualification> qualifications = new ArrayList<>();
    URL url = new URL(SERVER_ROOT + QUALIFICATIONS_PATH);
    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
    qualifications.addAll(JsonHelper.parseQualifications(in));
    return qualifications;
  }
}
