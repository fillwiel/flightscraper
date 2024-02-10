package pl.wielkopolan.flightscraper.services.impl;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.UncategorizedMongoDbException;
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
import java.util.Optional;

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
        log.info("Found {} promotions to process.", promotionDtos.size());
        promotionDtos.forEach(this::processSinglePromotion);
    }

    private void processSinglePromotion(PromotionDto promotionDto) {
        promotionDto.tickets().forEach(ticket -> processTicket(ticket, promotionDto.date()));
    }

    //TODO: rename date to dateOfFlight for clarity - but verify before changing var if this is really the correct date
    private void processTicket(TicketDto ticketDto, Date date) {
        ticketDto.packages().forEach(packageId -> processPackage(packageId, ticketDto, date));
    }

    //TODO: rename this method to inform about saving to repository
    private void processPackage(String packageId, TicketDto ticketDto, Date date) {
        final JSONObject currentFlightInfo = flightConnectionInfoService.readFlightInfoFromWeb(packageId);
        Optional<Flight> optionalFlight = Optional.empty();
        try {
            optionalFlight = flightRepository.findByPackageId(packageId);
        } catch (UncategorizedMongoDbException e) {
            log.error("Error looking up flight in repository with packageId: {}", packageId, e);
        }
        if (optionalFlight.isPresent()) {
            updateFlightPriceHistory(optionalFlight.get(), currentFlightInfo);
        } else {
            saveNewFlight(currentFlightInfo, packageId, ticketDto, date);
        }
    }

    private void updateFlightPriceHistory(Flight existingFlight, JSONObject flightInfo) {
        //TODO: rewrite either this method or the converterService not to create so many PriceHistory objects
        if (jsonConverterService.isPriceChanged(existingFlight, flightInfo)) {
            try {
                flightRepository.save(jsonConverterService.updateFlightPriceHistory(existingFlight, flightInfo));
            } catch (UncategorizedMongoDbException e) {
                log.error("Error updating existingFlight : {}", existingFlight.packageId(), e);
            }
        }
    }

    private void saveNewFlight(JSONObject currentFlightInfo, String packageId, TicketDto ticketDto, Date date) {
        try {
            flightRepository.save(jsonConverterService.createFlightFromJson(currentFlightInfo, packageId, ticketDto,
                    date));
        } catch (UncategorizedMongoDbException e) {
            log.error("Error saving flight : {}", packageId, e);
        }
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
