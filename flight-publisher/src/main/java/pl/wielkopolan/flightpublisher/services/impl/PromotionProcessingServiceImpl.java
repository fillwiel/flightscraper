package pl.wielkopolan.flightpublisher.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wielkopolan.flightpublisher.data.rainbow.FlightDto;
import pl.wielkopolan.flightpublisher.data.rainbow.PromotionDto;
import pl.wielkopolan.flightpublisher.data.rainbow.TicketDto;
import pl.wielkopolan.flightpublisher.services.FlightCreatorService;
import pl.wielkopolan.flightpublisher.services.PromotionProcessingService;
import pl.wielkopolan.flightpublisher.services.PublisherService;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PromotionProcessingServiceImpl implements PromotionProcessingService {
    private final FlightCreatorService flightCreatorService;
    private final PublisherService<FlightDto> publisherService;

    @Override
    public void publishFlightsFoundInPromotions(final List<PromotionDto> promotionDtos) {
        log.info("Found {} promotions to process.", promotionDtos.size());
        final Set<TicketDto> tickets = promotionDtos.stream()
                .flatMap(promotionDto -> promotionDto.tickets().stream()).collect(Collectors.toSet());
        Map<TicketDto, Date> ticketDateMap = createTicketDateOfFlightMap(promotionDtos, tickets);
        publishFlights(ticketDateMap);
    }

    private void publishFlights(final Map<TicketDto, Date> ticketDateMap) {
        flightCreatorService.createFlights(ticketDateMap)
                .forEach(publisherService::publish);
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
}