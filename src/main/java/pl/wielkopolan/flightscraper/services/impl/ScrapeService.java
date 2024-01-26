package pl.wielkopolan.flightscraper.services.impl;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wielkopolan.flightscraper.data.Flight;
import pl.wielkopolan.flightscraper.data.rainbow.PromotionDto;
import pl.wielkopolan.flightscraper.data.rainbow.TicketDto;
import pl.wielkopolan.flightscraper.data.repository.FlightRepository;
import pl.wielkopolan.flightscraper.services.FlightConnectionInfoService;
import pl.wielkopolan.flightscraper.services.JsonConverterService;
import pl.wielkopolan.flightscraper.services.PromotionListService;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class ScrapeService {
    @Autowired
    private PromotionListService promotionListService;
    @Autowired
    private JsonConverterService jsonConverterService;
    @Autowired
    private FlightConnectionInfoService flightConnectionInfoService;
    @Autowired
    private FlightRepository flightRepository;
    public void scrapePackages() throws IOException {
        List<PromotionDto> promotionDtos = jsonConverterService.createPromotionDto(promotionListService.getPromotionList());
        processPromotions(promotionDtos);
    }
    private void processPromotions(List<PromotionDto> promotionDtos){
        promotionDtos.forEach(this::processSinglePromotion);
    }
    private void processSinglePromotion(PromotionDto promotionDto){
        promotionDto.tickets().forEach(ticket -> processTicket(ticket, promotionDto.date()));
    }
    private void processTicket(TicketDto ticketDto, Date date){
        ticketDto.packages().forEach(packageId -> {
            try {
                processPackage(packageId, ticketDto, date);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }
    private void processPackage(String packageId, TicketDto ticketDto, Date date) throws IOException {
        JSONObject infoAboutFlightConnection = flightConnectionInfoService.getInfoAboutFlightConnection(packageId);
        Flight flight = flightRepository.findByPackageId(packageId)
                .map(existingFlight ->
                        jsonConverterService.appendFlightPriceHistory(existingFlight, infoAboutFlightConnection))
                .orElse(jsonConverterService.createFlightFromJson(infoAboutFlightConnection, packageId, ticketDto, date));
        flightRepository.save(flight);
    }
}
