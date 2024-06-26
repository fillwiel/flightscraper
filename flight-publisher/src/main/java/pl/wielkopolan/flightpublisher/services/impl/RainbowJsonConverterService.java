package pl.wielkopolan.flightpublisher.services.impl;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import pl.wielkopolan.flightpublisher.data.Flight;
import pl.wielkopolan.flightpublisher.data.PriceHistory;
import pl.wielkopolan.flightpublisher.data.rainbow.FlightInfoDto;
import pl.wielkopolan.flightpublisher.data.rainbow.PromotionDto;
import pl.wielkopolan.flightpublisher.data.rainbow.TicketDto;
import pl.wielkopolan.flightpublisher.services.JsonConverterService;
import pl.wielkopolan.flightpublisher.util.jsonconstants.RainbowConstants;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RainbowJsonConverterService implements JsonConverterService {

    @Override
    public List<PromotionDto> createPromotionDto(JSONArray jsonArray) {
        List<PromotionDto> promotionDtoList = new ArrayList<>();
        jsonArray.iterator().forEachRemaining(element -> promotionDtoList.add(createPromotionDto((JSONObject) element)));
        return promotionDtoList;
    }

    @Override
    public Flight createFlightFromJson(JSONObject currentFlightInfo, String packageId, TicketDto ticketDto, Date date) {
        return createFlight(currentFlightInfo, packageId, ticketDto, date);
    }

    @Override
    public Flight updateFlightPriceHistory(Flight existingFlight, JSONObject currentFlightInfo) {
        return appendPriceHistory(existingFlight, currentFlightInfo);
    }

    private Flight appendPriceHistory(Flight flight, JSONObject currentFlightInfo) {
        PriceHistory currentPrice = createPriceHistoryItem(currentFlightInfo);
        flight.priceHistory().addLast(currentPrice);
        return flight;
    }

    private Flight createFlight(JSONObject currentFlightInfo, String packageId, TicketDto ticketDto, Date date) {
        String airport = ticketDto.arrivalInfo().airport();
        String country = ticketDto.arrivalInfo().country();
        String arrivalCity = ticketDto.arrivalInfo().city();
        String departureCity = ticketDto.departureInfo().city();
        PriceHistory priceHistoryItem = createPriceHistoryItem(currentFlightInfo);
        return new Flight(packageId, airport, country, arrivalCity, departureCity, date, List.of(priceHistoryItem));
    }

    @Override
    public PriceHistory createPriceHistoryItem(JSONObject currentFlightInfo) {
        int price = (int) currentFlightInfo.get(RainbowConstants.PRICE.getValue());
        int returnFlightId = (int) currentFlightInfo.get(RainbowConstants.ID.getValue());
        return new PriceHistory(returnFlightId, price, new Date());
    }

    private PromotionDto createPromotionDto(JSONObject jsonObject) {
        int price = (int) jsonObject.get(RainbowConstants.CENA.getValue());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(jsonObject.get(RainbowConstants.DATA.getValue()).toString(), formatter);
        Date date = java.sql.Date.valueOf(localDate);
        JSONArray jsonTickets = (JSONArray) jsonObject.get(RainbowConstants.BILETY.getValue());
        List<TicketDto> tickets = new ArrayList<>();
        jsonTickets.iterator().forEachRemaining(element -> {
            JSONObject jsonTicket = (JSONObject) element;
            tickets.add(createTicket(jsonTicket));

        });
        return new PromotionDto(date, price, tickets);
    }

    private TicketDto createTicket(JSONObject jsonObject) {
        int id = Integer.parseInt(jsonObject.get(RainbowConstants.ID_CAMELCASE.getValue()).toString());
        int price = (int) jsonObject.get(RainbowConstants.CENA.getValue());
        FlightInfoDto inboundFlight =
                createFlightInfo((JSONObject) jsonObject.get(RainbowConstants.PRZYLOT.getValue()));
        FlightInfoDto outboundFlight = createFlightInfo((JSONObject) jsonObject.get(RainbowConstants.WYLOT.getValue()));
        String packageIdArray = jsonObject.get(RainbowConstants.PAKIETY.getValue()).toString();
        List<String> packages = createPackageIdList(packageIdArray);
        return new TicketDto(id, outboundFlight, inboundFlight, price, packages);
    }

    private List<String> createPackageIdList(String packageIdArray) {
        String packageIds = "";
        if (packageIdArray.length() >= 2) {
            // Remove the first and last characters, remove quotation marks
            packageIds = packageIdArray.substring(1, packageIdArray.length() - 1).replace("\"", "");
        }
        return List.of(packageIds.split(","));
    }

    private FlightInfoDto createFlightInfo(JSONObject jsonObject) {
        String airport = jsonObject.get(RainbowConstants.IATA.getValue()).toString();
        String city = jsonObject.get(RainbowConstants.PRZYSTANEK.getValue()).toString();
        String country = jsonObject.get(RainbowConstants.PANSTWO.getValue()).toString();
        return new FlightInfoDto(airport, city, country);
    }


}
