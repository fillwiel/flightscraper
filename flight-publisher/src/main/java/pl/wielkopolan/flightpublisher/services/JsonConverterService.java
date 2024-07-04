package pl.wielkopolan.flightpublisher.services;

import org.json.JSONArray;
import org.json.JSONObject;
import pl.wielkopolan.flightpublisher.data.rainbow.FlightDto;
import pl.wielkopolan.flightpublisher.data.rainbow.PromotionDto;
import pl.wielkopolan.flightpublisher.data.rainbow.TicketDto;

import java.util.Date;
import java.util.List;

public interface JsonConverterService {
    List<PromotionDto> createPromotionDto(JSONArray jsonArray);

    FlightDto createFlightFromJson(JSONObject currentFlightInfo, String packageId, TicketDto ticketDto, Date date);
}
