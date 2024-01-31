package pl.wielkopolan.flightscraper.services.impl;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wielkopolan.flightscraper.data.Flight;
import pl.wielkopolan.flightscraper.data.rainbow.PromotionDto;
import pl.wielkopolan.flightscraper.data.rainbow.TicketDto;
import pl.wielkopolan.flightscraper.data.repository.FlightRepository;
import pl.wielkopolan.flightscraper.services.FlightConnectionInfoService;
import pl.wielkopolan.flightscraper.services.JsonConverterService;
import pl.wielkopolan.flightscraper.services.PromotionListService;

import java.util.Date;
import java.util.List;

@Service
public class ScrapeService {
    private static final Logger log = LoggerFactory.getLogger(ScrapeService.class);
    private final PromotionListService promotionListService;
    private final JsonConverterService jsonConverterService;
    private final FlightConnectionInfoService flightConnectionInfoService;
    private final FlightRepository flightRepository;

    public void scrapePackages() {
        processPromotions(jsonConverterService.createPromotionDto(promotionListService.getPromotionList()));
    }

    private void processPromotions(List<PromotionDto> promotionDtos) {
        promotionDtos.forEach(this::processSinglePromotion);
    }

    private void processSinglePromotion(PromotionDto promotionDto) {
        promotionDto.tickets().forEach(ticket -> processTicket(ticket, promotionDto.date()));
    }

    private void processTicket(TicketDto ticketDto, Date date) {
        ticketDto.packages().forEach(packageId -> processPackage(packageId, ticketDto, date));
    }

    private void processPackage(String packageId, TicketDto ticketDto, Date date) {
        final JSONObject infoAboutFlightConnection =
                flightConnectionInfoService.getInfoAboutFlightConnection(packageId);
        Flight flight = flightRepository.findByPackageId(packageId)
                .map(existingFlight ->
                        jsonConverterService.appendFlightPriceHistory(existingFlight, infoAboutFlightConnection))
                .orElse(jsonConverterService.createFlightFromJson(infoAboutFlightConnection, packageId, ticketDto,
                        date));
        log.info("Saving flight to repository: {}", flight);
        flightRepository.save(flight);
    }

    @Autowired
    public ScrapeService(PromotionListService promotionListService, JsonConverterService jsonConverterService,
                         FlightConnectionInfoService flightConnectionInfoService, FlightRepository flightRepository) {
        this.promotionListService = promotionListService;
        this.jsonConverterService = jsonConverterService;
        this.flightConnectionInfoService = flightConnectionInfoService;
        this.flightRepository = flightRepository;
    }
}
