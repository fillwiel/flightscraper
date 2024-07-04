package pl.wielkopolan.flightpersistence.util;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
public final class JsonReader {

    public static Optional<JSONObject> readJsonFromUrl(String url) {
        try (InputStream is = URI.create(url).toURL().openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            return Optional.of(new JSONObject(readAll(rd)));
        } catch (JSONException e) {
            log.error("Problem with reading json from: {}", url, e);
        } catch (MalformedURLException e) {
            log.error("Malformed URL: {}", url);
        } catch (IOException e) {
            log.error("Cannot open stream for URL: {} - IOException: {}", url, e.getMessage());
        }
        return Optional.empty();
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
        //Util class - private constructor
    }
}