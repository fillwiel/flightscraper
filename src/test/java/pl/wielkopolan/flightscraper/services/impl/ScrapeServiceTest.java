package pl.wielkopolan.flightscraper.services.impl;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.wielkopolan.flightscraper.data.Flight;
import pl.wielkopolan.flightscraper.data.PriceHistory;
import pl.wielkopolan.flightscraper.data.rainbow.FlightInfoDto;
import pl.wielkopolan.flightscraper.data.rainbow.PromotionDto;
import pl.wielkopolan.flightscraper.data.rainbow.TicketDto;
import pl.wielkopolan.flightscraper.services.FlightConnectionInfoService;
import pl.wielkopolan.flightscraper.services.JsonConverterService;
import pl.wielkopolan.flightscraper.services.PromotionListService;
import pl.wielkopolan.flightscraper.services.RepositoryService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScrapeServiceTest {
    @Mock
    private PromotionListService promotionListService;
    @Mock
    private JsonConverterService jsonConverterService;
    @Mock
    private FlightConnectionInfoService flightConnectionInfoService;
    @Mock
    private RepositoryService repositoryService;

    @Spy
    @InjectMocks
    private FlightCreatorServiceImpl flightCreatorService;
    @InjectMocks
    private ScrapeService scrapeService;
    private List<PromotionDto> promotionDtos;
    private List<TicketDto> newTickets;
    private List<TicketDto> existingTickets;
    private final List<String> existingPackageIds = List.of("123_456", "789_1011", "1213_1415");
    private List<Flight> flightsMatchingExistingTickets;
    @BeforeEach
    void setUp() {
        scrapeService = new ScrapeService(promotionListService, jsonConverterService, repositoryService, flightCreatorService);

        flightsMatchingExistingTickets= createExistingFlights();
        newTickets = createNewTickets();
        existingTickets = createExistingTickets();
        promotionDtos = createPromotions();
    }

    @Test
    void should_FilterOutTickets_WhenPackagesFoundInRepo(){
        //given
        int numberOfNewPackages = newTickets.stream().map(TicketDto::packages).mapToInt(List::size).sum();
        //when
        when(jsonConverterService.createPromotionDto(any())).thenReturn(promotionDtos);
        when(repositoryService.findFlightsInDatabase(any())).thenReturn(flightsMatchingExistingTickets);
        when(flightConnectionInfoService.readFlightInfoFromWeb(any())).thenReturn(new JSONObject());
        when(jsonConverterService.isPriceChanged(any(), any())).thenReturn(true);
        when(jsonConverterService.createFlightFromJson(any(), any(), any(), any())).thenReturn(new Flight("packageId", "airport", "country", "arrivalCity", "departureCity", new Date(), List.of(new PriceHistory(1, 1, new Date()))));
        when(jsonConverterService.updateFlightPriceHistory(any(), any())).thenReturn(new Flight("packageId", "airport", "country", "arrivalCity", "departureCity", new Date(), List.of(new PriceHistory(1, 1, new Date()))));

        scrapeService.scrapePackages();

        //then
        verify(jsonConverterService, times(flightsMatchingExistingTickets.size())).isPriceChanged(any(), any());
        verify(jsonConverterService, times(numberOfNewPackages)).createFlightFromJson(any(), any(), any(), any());
    }
    private List<TicketDto> createNewTickets() {
        FlightInfoDto dummyFlightInfo = new FlightInfoDto("airport", "city", "country");
        TicketDto newTicket1 = new TicketDto(1, dummyFlightInfo, dummyFlightInfo, 1, List.of("1", "2", "3"));
        TicketDto newTicket2 = new TicketDto(2, dummyFlightInfo, dummyFlightInfo, 1, List.of("4", "5", "6"));
        TicketDto newTicket3 = new TicketDto(3, dummyFlightInfo, dummyFlightInfo, 1, List.of("7", "8", "9"));
        return List.of(newTicket1, newTicket2, newTicket3);
    }
    private List<TicketDto> createExistingTickets() {
        List<TicketDto> tickets = new ArrayList<>();
        flightsMatchingExistingTickets.forEach(flight -> {
            TicketDto ticket = new TicketDto(flight.hashCode(), new FlightInfoDto("airport", "city", "country"), new FlightInfoDto("airport", "city", "country"), 1, List.of(flight.packageId()));
            tickets.add(ticket);
        });
        if(!tickets.isEmpty()) {
            TicketDto firstTicket = tickets.getFirst();
            TicketDto firstTicketWithAppendedPackage = new TicketDto(firstTicket.id(), new FlightInfoDto("airport", "city", "country"), new FlightInfoDto("airport", "city", "country"), 1,
                    List.of(firstTicket.packages().getFirst(), existingPackageIds.getLast()));
            tickets.set(0, firstTicketWithAppendedPackage);
        }
        return tickets;
    }

    private List<Flight> createExistingFlights(){
        List<Flight> existingFlights = new ArrayList<>();
        PriceHistory promotionHistoryItem = new PriceHistory(1, 1, new Date() );
        for(String packageId: existingPackageIds){
            Flight flight = new Flight(packageId, "airport1", "country1", "arrivalCity1", "departureCity1", new Date(), List.of(promotionHistoryItem));
            existingFlights.add(flight);
        }
        return existingFlights;
    }

    private List<PromotionDto> createPromotions(){
        PromotionDto promotion1 = new PromotionDto(new Date(), 1, newTickets);
        PromotionDto promotion2 = new PromotionDto(new Date(), 1, existingTickets);
        return List.of(promotion1, promotion2);
    }
}