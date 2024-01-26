package pl.wielkopolan.flightscraper.services;

import org.json.JSONObject;

import java.io.IOException;

public interface FlightConnectionInfoService {
    JSONObject getInfoAboutFlightConnection(int idOutboundFlight, int idReturnFlight) throws IOException;

    JSONObject getInfoAboutFlightConnection(String packageId) throws IOException;
}
