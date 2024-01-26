package pl.wielkopolan.flightscraper.services.impl;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.wielkopolan.flightscraper.services.FlightConnectionInfoService;
import pl.wielkopolan.flightscraper.util.JsonReader;

import java.io.IOException;

@Service
public class RainbowFlightConnectionInfoService implements FlightConnectionInfoService {

    @Value("${rainbow.search.api.url.connection.prefix}")
    private String connectionInfoUrl;

    @Override
    public JSONObject getInfoAboutFlightConnection(int idOutboundFlight, int idReturnFlight) throws IOException {
            return JsonReader.readJsonFromUrl(createConnectionUrl(idOutboundFlight, idReturnFlight));
    }
    @Override
    public JSONObject getInfoAboutFlightConnection(String packageId) throws IOException {
            return JsonReader.readJsonFromUrl(connectionInfoUrl + "?id=" + packageId);
    }

    private String createConnectionUrl(int idOutboundFlight, int idReturnFlight) {
        return connectionInfoUrl + "?id=" + idOutboundFlight + "_" + idReturnFlight;
    }

}
