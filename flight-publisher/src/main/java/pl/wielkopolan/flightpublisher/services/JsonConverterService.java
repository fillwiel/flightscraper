package pl.wielkopolan.flightpublisher.services;

import org.json.JSONArray;
import org.json.JSONObject;
import pl.wielkopolan.flightpublisher.data.Flight;
import pl.wielkopolan.flightpublisher.data.PriceHistory;
import pl.wielkopolan.flightpublisher.data.rainbow.PromotionDto;
import pl.wielkopolan.flightpublisher.data.rainbow.TicketDto;

import java.util.Date;
import java.util.List;

public interface JsonConverterService {
    List<PromotionDto> createPromotionDto(JSONArray jsonArray);

    Flight createFlightFromJson(JSONObject currentFlightInfo, String packageId, TicketDto ticketDto, Date date);

    Flight updateFlightPriceHistory(Flight existingFlight, JSONObject infoAboutFlightConnection);

    PriceHistory createPriceHistoryItem(JSONObject currentFlightInfo);
}
