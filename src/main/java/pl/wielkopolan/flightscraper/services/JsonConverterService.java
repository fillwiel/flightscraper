package pl.wielkopolan.flightscraper.services;

import org.json.JSONArray;
import org.json.JSONObject;
import pl.wielkopolan.flightscraper.data.Flight;
import pl.wielkopolan.flightscraper.data.rainbow.PromotionDto;
import pl.wielkopolan.flightscraper.data.rainbow.TicketDto;

import java.util.Date;
import java.util.List;

public interface JsonConverterService {
    List<PromotionDto> createPromotionDto(JSONArray jsonArray);

    Flight createFlightFromJson(JSONObject currentFlightInfo, String packageId, TicketDto ticketDto, Date date);

    Flight updateFlightPriceHistory(Flight existingFlight, JSONObject infoAboutFlightConnection);
}
