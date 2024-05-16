package pl.wielkopolan.flightpublisher.services;

import org.json.JSONObject;

public interface FlightConnectionInfoService {
    JSONObject readFlightInfoFromWeb(String packageId);
}
