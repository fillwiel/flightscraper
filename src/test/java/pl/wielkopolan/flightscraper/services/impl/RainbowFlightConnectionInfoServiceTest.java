package pl.wielkopolan.flightscraper.services.impl;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RainbowFlightConnectionInfoServiceTest {
    //magic numbers - represent real live data from Rainbow Tours API , must be updated manually
    public static final int ID_OUTBOUND_FLIGHT = 170765;
    public static final int ID_RETURN_FLIGHT = 305293;
    @Autowired
    RainbowFlightConnectionInfoService rainbowFlightConnectionInfoService = new RainbowFlightConnectionInfoService();

    /**
     * This test has to be updated with correct ID values,
     * since they are tested over life environment and quickly become outdated.
     */

    void shouldReturnJsonForWorkingIds() throws IOException {
        //given
        int idOutboundFlight = ID_OUTBOUND_FLIGHT;
        int idReturnFlight = ID_RETURN_FLIGHT;
        //when
        JSONObject infoAboutFlightConnection = rainbowFlightConnectionInfoService.getInfoAboutFlightConnection(idOutboundFlight, idReturnFlight);
        //then
        System.out.println("FOUND infoAboutFlightConnection = " + infoAboutFlightConnection);
        assertNotNull(infoAboutFlightConnection);
    }
}