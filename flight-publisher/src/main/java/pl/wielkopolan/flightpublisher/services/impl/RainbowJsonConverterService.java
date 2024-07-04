package pl.wielkopolan.flightpublisher.services.impl;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import pl.wielkopolan.flightpublisher.data.rainbow.FlightDto;
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
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public List<PromotionDto> createPromotionDto(JSONArray jsonArray) {
        List<PromotionDto> promotionDtoList = new ArrayList<>();
        jsonArray.forEach(element -> promotionDtoList.add(createPromotionDto((JSONObject) element)));
        return promotionDtoList;
    }

    @Override
    public FlightDto createFlightFromJson(JSONObject currentFlightInfo, String packageId, TicketDto ticketDto, Date date) {
        return createFlight(currentFlightInfo, packageId, ticketDto, date);
    }

    private FlightDto createFlight(JSONObject currentFlightInfo, String packageId, TicketDto ticketDto, Date date) {
        String airport = ticketDto.arrivalInfo().airport();
        String country = ticketDto.arrivalInfo().country();
        String arrivalCity = ticketDto.arrivalInfo().city();
        String departureCity = ticketDto.departureInfo().city();
        int currentPrice = (int) currentFlightInfo.get(RainbowConstants.PRICE.getValue());
        return new FlightDto(packageId, airport, country, arrivalCity, departureCity, date, currentPrice);
    }

    private PromotionDto createPromotionDto(JSONObject jsonObject) {
        int price = (int) jsonObject.get(RainbowConstants.CENA.getValue());
        LocalDate localDate = LocalDate.parse(jsonObject.get(RainbowConstants.DATA.getValue()).toString(), DATE_FORMATTER);
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
