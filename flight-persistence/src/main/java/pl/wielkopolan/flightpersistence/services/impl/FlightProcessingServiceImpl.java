package pl.wielkopolan.flightpersistence.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wielkopolan.flightpersistence.data.FlightDto;
import pl.wielkopolan.flightpersistence.domain.Flight;
import pl.wielkopolan.flightpersistence.services.FlightProcessingService;
import pl.wielkopolan.flightpersistence.services.PriceHistoryService;
import pl.wielkopolan.flightpersistence.services.RepositoryService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FlightProcessingServiceImpl implements FlightProcessingService {
    private final PriceHistoryService priceHistoryService;
    private final RepositoryService repositoryService;

    /**
     * Saves flight to repository
     * If flight already exists, updates it
     *
     * @param flightDto Flight from Kafka topic
     */
    @Override
    public void processFlight(FlightDto flightDto) {
        repositoryService.findFlightByPackageId(flightDto.packageId())
                .ifPresentOrElse(
                        existingFlight -> updateExistingFlight(existingFlight, flightDto),
                        () -> createNewFlight(flightDto));
    }

    private void updateExistingFlight(Flight existingFlight, FlightDto flightDto) {
        log.debug("Trying to update existing flight with packageId: {}", flightDto.packageId());
        boolean isUpdated = priceHistoryService.updateFlightPriceHistoryIfChanged(existingFlight);
        if (isUpdated) {
            repositoryService.save(existingFlight);
        }
    }

    private void createNewFlight(FlightDto flightDto) {
        log.debug("Creating new flight with packageId: {}", flightDto.packageId());
        Flight newFlight = new Flight(flightDto.packageId(), flightDto.airport(), flightDto.country(),
                flightDto.arrivalCity(), flightDto.departureCity(), flightDto.date(),
                List.of(priceHistoryService.createPriceHistoryItem(flightDto.currentPrice())));
        repositoryService.save(newFlight);
    }
}
