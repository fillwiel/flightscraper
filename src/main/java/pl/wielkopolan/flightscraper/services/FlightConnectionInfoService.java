package pl.wielkopolan.flightscraper.services;

import org.json.JSONObject;

public interface FlightConnectionInfoService {
    JSONObject readFlightInfoFromWeb(String packageId);
}
