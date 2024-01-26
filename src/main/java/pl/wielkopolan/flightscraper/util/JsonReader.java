package pl.wielkopolan.flightscraper.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class JsonReader {
    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        try (InputStream is = URI.create(url).toURL().openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String jsonText = readAll(rd);
            return new JSONObject(jsonText);
        }
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