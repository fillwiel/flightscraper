package pl.wielkopolan.flightpublisher.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import pl.wielkopolan.flightpublisher.data.rainbow.FlightDto;
import pl.wielkopolan.flightpublisher.data.rainbow.TicketDto;
import pl.wielkopolan.flightpublisher.services.FlightConnectionInfoService;
import pl.wielkopolan.flightpublisher.services.FlightCreatorService;
import pl.wielkopolan.flightpublisher.services.JsonConverterService;

import java.util.Date;
import java.util.Map;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class FlightCreatorServiceImpl implements FlightCreatorService {
    private final JsonConverterService jsonConverterService;
    private final FlightConnectionInfoService flightConnectionInfoService;
    @Override
    public Stream<FlightDto> createFlights(Map<TicketDto, Date> ticketDateOfFlightMap) {
        return ticketDateOfFlightMap.entrySet().stream()
            .flatMap(ticket -> ticket.getKey().packages().stream()
                .map(packageId -> {
                    final JSONObject currentFlightInfo = flightConnectionInfoService.readFlightInfoFromWeb(packageId);
                    return jsonConverterService.createFlightFromJson(currentFlightInfo, packageId, ticket.getKey(), ticket.getValue());
                }));
    }
}
