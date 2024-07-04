package pl.wielkopolan.flightpublisher.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public final class UrlReader {
    private static final Logger log = LoggerFactory.getLogger(UrlReader.class);
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
    public static Optional<String> readStringFromUrl(String url) {
        log.info("Reading url: {}", url);
        try (InputStream is = new URI(url).toURL().openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            return Optional.of(readAll(rd));
        } catch (URISyntaxException e) {
            log.error("URISyntaxException while reading url: {}", url);
        } catch (MalformedURLException e) {
            log.error("MalformedURLException while reading url: {}", url);
        } catch (IOException e) {
            log.error("IOException while reading url: {}", url);
        }
        return Optional.empty();
    }
    private UrlReader() {
        // Private constructor - util class
    }
}
