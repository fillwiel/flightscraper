package pl.wielkopolan.flightpersistence.services;

import org.json.JSONObject;

public interface FlightConnectionInfoService {
    JSONObject readFlightInfoFromWeb(String packageId);
}
