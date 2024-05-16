package pl.wielkopolan.flightpublisher.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wielkopolan.flightpublisher.data.Flight;
import pl.wielkopolan.flightpublisher.data.rainbow.PromotionDto;
import pl.wielkopolan.flightpublisher.data.rainbow.TicketDto;
import pl.wielkopolan.flightpublisher.services.*;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ScrapeService {
    private final PromotionListService promotionListService;
    private final JsonConverterService jsonConverterService;
    private final RepositoryService repositoryService;
    private final FlightCreatorService flightCreatorService;
    private final FlightPublisherService flightPublisherService;

    public void scrapePackages() {
        processPromotions(jsonConverterService.createPromotionDto(promotionListService.getPromotionList()));
    }

    private void processPromotions(final List<PromotionDto> promotionDtos) {
        log.info("Found {} promotions to process.", promotionDtos.size());
        final List<TicketDto> tickets = promotionDtos.stream().flatMap(promotionDto -> promotionDto.tickets().stream()).toList();
        final Set<String> promotionPackages =
                tickets.stream().flatMap(ticketDto -> ticketDto.packages().stream()).collect(Collectors.toSet());
        final List<Flight> recordedFlights = repositoryService.findFlightsByPromotionPackages(new ArrayList<>(promotionPackages));
        final Set<String> recordedFlightIds = mapRecorderFlightsToFlightIds(recordedFlights);
        final Set<TicketDto> newTickets = filterNewTickets(recordedFlightIds, tickets);
        publishExistingFlights(recordedFlights);
        publishNewTickets(promotionDtos, newTickets, recordedFlightIds);
    }

    private void publishNewTickets(final List<PromotionDto> promotionDtos, final Set<TicketDto> newTickets,
                                   final Set<String> recordedFlightIds) {
        if (newTickets.isEmpty()) {
            log.info("No new tickets found.");
            return;
        }
        Map<TicketDto, Date> ticketDateMap = createTicketDateOfFlightMap(promotionDtos, newTickets);
        flightCreatorService.createNewFlights(ticketDateMap)
                .filter(flight -> !recordedFlightIds.contains(flight.packageId()))
                .forEach(flightPublisherService::publish);
    }

    private void publishExistingFlights(final List<Flight> flights) {
        flights.stream()
                .map(flightCreatorService::createUpdatedFlightsIfPriceChanged)
                .forEach(flightPublisherService::publish);
    }

    private Set<TicketDto> filterNewTickets(Set<String> recordedFlightIds, List<TicketDto> allTickets) {
        return allTickets.stream()
                .filter(ticket -> ticket.packages().stream()
                        .noneMatch(recordedFlightIds::contains)).collect(Collectors.toSet());
    }

    /**
     * Maps recorded flights to their package id.
     * Used to avoid additional call to repository.
     *
     * @param recordedFlights list of Flights
     * @return set of packageIds for corresponding Flights
     */
    private static Set<String> mapRecorderFlightsToFlightIds(List<Flight> recordedFlights) {
        return recordedFlights.stream().map(Flight::packageId).collect(Collectors.toSet());
    }

    /**
     * Creates a map of tickets and their date of flight.
     * Necessary since date of flight is stored in PromotionDto, not in TicketDto.
     *
     * @param promotionDtos provides info about date of flight
     * @param tickets       list of tickets
     * @return map of tickets and their date of flight
     */
    private Map<TicketDto, Date> createTicketDateOfFlightMap(List<PromotionDto> promotionDtos, Set<TicketDto> tickets) {
        return promotionDtos.stream()
                .flatMap(promotionDto -> promotionDto.tickets().stream()
                        .filter(tickets::contains)
                        .map(ticket -> Map.entry(ticket, promotionDto.date())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public ScrapeService(PromotionListService promotionListService, JsonConverterService jsonConverterService,
                         RepositoryService repositoryService, FlightCreatorService flightCreatorService,
                         FlightPublisherService flightPublisherService) {
        this.promotionListService = promotionListService;
        this.jsonConverterService = jsonConverterService;
        this.flightCreatorService = flightCreatorService;
        this.repositoryService = repositoryService;
        this.flightPublisherService = flightPublisherService;
    }
}