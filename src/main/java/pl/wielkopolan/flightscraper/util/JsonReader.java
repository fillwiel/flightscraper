package pl.wielkopolan.flightscraper.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class JsonReader {

    private static final Logger log = LoggerFactory.getLogger(JsonReader.class);
    public static Optional<JSONObject> readJsonFromUrl(String url) throws JSONException {
        try (InputStream is = URI.create(url).toURL().openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String jsonText = readAll(rd);
            return Optional.of(new JSONObject(jsonText));
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

    public static List<JSONObject> readJsonObjectArray(String inputString) throws JSONException {
        String commaSeparatedText = inputString.replace("[", "").replace("]", "");
        List<String> stringList = Arrays.asList(commaSeparatedText.split(","));
        return stringList.stream().map(JSONObject::new).toList();
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;/*w w  w .j  ava2 s .co  m*/
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    private JsonReader() {
    }
}