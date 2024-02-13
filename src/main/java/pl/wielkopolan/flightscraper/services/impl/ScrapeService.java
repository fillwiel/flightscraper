package pl.wielkopolan.flightscraper.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wielkopolan.flightscraper.data.Flight;
import pl.wielkopolan.flightscraper.data.rainbow.PromotionDto;
import pl.wielkopolan.flightscraper.data.rainbow.TicketDto;
import pl.wielkopolan.flightscraper.services.FlightCreatorService;
import pl.wielkopolan.flightscraper.services.JsonConverterService;
import pl.wielkopolan.flightscraper.services.PromotionListService;
import pl.wielkopolan.flightscraper.services.RepositoryService;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ScrapeService {
    private static final Logger log = LoggerFactory.getLogger(ScrapeService.class);
    private final PromotionListService promotionListService;
    private final JsonConverterService jsonConverterService;
    private final RepositoryService repositoryService;
    private final FlightCreatorService flightCreatorService;

    public void scrapePackages() {
        processPromotions(jsonConverterService.createPromotionDto(promotionListService.getPromotionList()));
    }

    private void processPromotions(final List<PromotionDto> promotionDtos) {
        log.info("Found {} promotions to process.", promotionDtos.size());
        List<TicketDto> tickets = promotionDtos.stream().flatMap(promotionDto -> promotionDto.tickets().stream()).toList();
        List<String> promotionPackages = tickets.stream().flatMap(ticketDto -> ticketDto.packages().stream()).toList();
        List<Flight> recordedFlights = repositoryService.findFlightsInDatabase(promotionPackages);
        List<TicketDto> newTickets = filterNewTickets(recordedFlights, tickets);
        processExistingFlights(recordedFlights);
        processNewTickets(promotionDtos, newTickets, recordedFlights);
    }

    private void processNewTickets(final List<PromotionDto> promotionDtos, final List<TicketDto> newTickets,
                                   final List<Flight> recordedFlights) {
        if(newTickets.isEmpty()) {
            log.info("No new tickets found.");
            return;
        }
        Map<TicketDto, Date> ticketDateMap = createTicketDateOfFlightMap(promotionDtos, newTickets);
        List<Flight> newFlights = flightCreatorService.createNewFlights(ticketDateMap);
        List<Flight> flightsCheckedForNotMatchingExistingFlights = newFlights.stream().filter(flight ->
                !recordedFlights.stream().map(Flight::packageId).toList().contains(flight.packageId())).toList();
        log.info("Saving {} flights.", flightsCheckedForNotMatchingExistingFlights.size());
        repositoryService.saveFlights(flightsCheckedForNotMatchingExistingFlights);
    }
    private void processExistingFlights(List<Flight> flights) {
        List<Flight> flightsToBeUpdated = flightCreatorService.createUpdatedFlightsIfPriceChanged(flights);
        log.info("Updating {} flights.", flightsToBeUpdated.size());
        repositoryService.saveFlights(flightsToBeUpdated);
    }

    private List<TicketDto> filterNewTickets(List<Flight> recordedFlights, List<TicketDto> allTickets) {
        return allTickets.stream()
                .filter(ticket -> ticket.packages().stream()
                        .noneMatch(packageId -> recordedFlights.stream()
                                .anyMatch(flight -> flight.packageId().equals(packageId)))).toList();
    }
    /**
     * Creates a map of tickets and their date of flight.
     * Necessary since date of flight is stored in promotionDto, not in ticket.
     * @param promotionDtos provides info about date of flight
     * @param tickets list of tickets
     * @return map of tickets and their date of flight
     */
    private Map<TicketDto, Date> createTicketDateOfFlightMap(List<PromotionDto> promotionDtos, List<TicketDto> tickets) {
        return promotionDtos.stream()
                .flatMap(promotionDto -> promotionDto.tickets().stream()
                        .filter(tickets::contains)
                        .map(ticket -> Map.entry(ticket, promotionDto.date())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Autowired
    public ScrapeService(PromotionListService promotionListService, JsonConverterService jsonConverterService,
                         RepositoryService repositoryService, FlightCreatorService flightCreatorService) {
        this.promotionListService = promotionListService;
        this.jsonConverterService = jsonConverterService;
        this.flightCreatorService = flightCreatorService;
        this.repositoryService = repositoryService;
    }
}
