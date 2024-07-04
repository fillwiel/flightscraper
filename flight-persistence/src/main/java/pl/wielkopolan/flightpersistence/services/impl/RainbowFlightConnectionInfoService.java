package pl.wielkopolan.flightpersistence.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.wielkopolan.flightpersistence.services.FlightConnectionInfoService;
import pl.wielkopolan.flightpersistence.util.JsonReader;

@Slf4j
@Service
public class RainbowFlightConnectionInfoService implements FlightConnectionInfoService {
    private final String connectionInfoUrl;

    public RainbowFlightConnectionInfoService(@Value("${rainbow.search.api.url.connection.prefix}")String connectionInfoUrl) {
        this.connectionInfoUrl = connectionInfoUrl;
    }

    @Override
    public JSONObject readFlightInfoFromWeb(String packageId) {
        try {
            JSONObject flightInfo = JsonReader.readJsonFromUrl(connectionInfoUrl + "?id=" + packageId).orElseThrow(RuntimeException::new);
            log.debug("Successfully read flight info from web for packageId: {}", packageId);
            return flightInfo;
        } catch (Exception e) {
            log.error("Error occurred while reading flight info from web for packageId: {}", packageId, e);
            throw e;
        }
    }
}
