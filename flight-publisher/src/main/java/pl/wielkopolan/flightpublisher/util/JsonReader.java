package pl.wielkopolan.flightpublisher.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public final class JsonReader {
    private static final Logger log = LoggerFactory.getLogger(JsonReader.class);
    public static Optional<JSONObject> readJsonFromUrl(String url) {
        String jsonText = null;
        try (InputStream is = URI.create(url).toURL().openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            jsonText = readAll(rd);
            return Optional.of(new JSONObject(jsonText));
        } catch (JSONException e) {
            if(jsonText != null) {
                log.error("Problem with reading json from: {} at {}", jsonText, url, e);
            } else {
                log.error("readAll() can't transform json.",e);
            }
        } catch (MalformedURLException e) {
            log.error("Malformed URL: {}", url);
        } catch (IOException e) {
            log.error("Cannot open stream for URL: {} - IOException: {}",url, e.getMessage());
        }
        return Optional.empty();
    }

    public static JSONArray readJsonArray(String inputString) throws JSONException {
        return new JSONArray(inputString);
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    private JsonReader() {
    }
}