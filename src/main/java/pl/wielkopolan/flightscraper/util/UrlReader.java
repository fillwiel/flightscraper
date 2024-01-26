package pl.wielkopolan.flightscraper.util;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

public final class UrlReader {
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
    public static String readStringFromUrl(String url) throws IOException {

        try (InputStream is = new URI(url).toURL().openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            return readAll(rd);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    private UrlReader() {
    }
}
