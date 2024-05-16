package pl.wielkopolan.flightpublisher.services.impl;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.wielkopolan.flightpublisher.services.FlightConnectionInfoService;
import pl.wielkopolan.flightpublisher.util.JsonReader;

@Service
public class RainbowFlightConnectionInfoService implements FlightConnectionInfoService {
    @Value("${rainbow.search.api.url.connection.prefix}")
    private String connectionInfoUrl;

    @Override
    public JSONObject readFlightInfoFromWeb(String packageId) {
        return JsonReader.readJsonFromUrl(connectionInfoUrl + "?id=" + packageId).orElseThrow();
    }
}
