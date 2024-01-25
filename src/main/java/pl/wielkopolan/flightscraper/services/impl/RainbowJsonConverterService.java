package pl.wielkopolan.flightscraper.services.impl;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import pl.wielkopolan.flightscraper.data.FlightDto;
import pl.wielkopolan.flightscraper.data.PromotionDto;
import pl.wielkopolan.flightscraper.data.PromotionDtoOld;
import pl.wielkopolan.flightscraper.data.TicketDto;
import pl.wielkopolan.flightscraper.util.jsonconstants.RainbowConstants;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class JsonConverterService {
    public void convertJsonToPromotionDto(JSONArray jsonArray) {
        List<PromotionDtoOld> promotionDtoOldList = new ArrayList<>();
        jsonArray.iterator().forEachRemaining(element -> {
            JSONObject jsonObject = (JSONObject) element;
            String key = jsonObject.get(RainbowConstants.KLUCZ.getValue()).toString();
            String destinationCity = jsonObject.get(RainbowConstants.NAZWA.getValue()).toString();
            String country = jsonObject.get(RainbowConstants.PANSTWO.getValue()).toString();
            int price = (int) jsonObject.get(RainbowConstants.CENA.getValue());
            promotionDtoOldList.add(new PromotionDtoOld(key, destinationCity, country, price, ""));
        });


    }

    PromotionDto createPromotionDto(JSONObject jsonObject) {
        int price = (int) jsonObject.get(RainbowConstants.CENA.getValue());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString, formatter);

        // Convert LocalDate to Date (if needed)
        Date date = java.sql.Date.valueOf(localDate);
        JSONArray tickets = (JSONArray) jsonObject.get(RainbowConstants.BILETY.getValue());
        TicketDto[] ticketDtos = new TicketDto[tickets.length()];
        tickets.iterator().forEachRemaining(element -> {
            JSONObject ticket = (JSONObject) element;
            ticketDtos[tickets.length()] = createTicket(ticket);
        });
        return new PromotionDto(price, ticketDtos);
    }

    TicketDto createTicket(JSONObject jsonObject){
        int id = (int) jsonObject.get(RainbowConstants.ID.getValue());
        int price = (int) jsonObject.get(RainbowConstants.CENA.getValue());
        FlightDto inboundFlight = createFlight((JSONObject) jsonObject.get(RainbowConstants.PRZYLOT.getValue()));
        FlightDto outboundFlight = createFlight((JSONObject) jsonObject.get(RainbowConstants.WYLOT.getValue()));
        String[] packages = jsonObject.get(RainbowConstants.PAKIETY.getValue()).toString().split(",");
        return new TicketDto(id, outboundFlight, inboundFlight, price, packages);
    }
    FlightDto createFlight(JSONObject jsonObject) {
        String airport = jsonObject.get(RainbowConstants.IATA.getValue()).toString();
        String city = jsonObject.get(RainbowConstants.PRZYSTANEK.getValue()).toString();
        String country = jsonObject.get(RainbowConstants.PANSTWO.getValue()).toString();
        return new FlightDto(airport, city, country);
    }


}
