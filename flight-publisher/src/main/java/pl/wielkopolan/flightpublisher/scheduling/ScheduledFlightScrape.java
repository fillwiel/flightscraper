package pl.wielkopolan.flightpublisher.scheduling;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.wielkopolan.flightpublisher.services.impl.ScrapeService;

import java.util.Date;

/**
 * Initiates flight scrape every hour
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduledFlightScrape {
    private final ScrapeService scrapeService;

    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void scrapeFlightsFromHomepage() {
        log.info("Running full scrape at: {}", new Date());
        scrapeService.scrapePackages();
        log.info("Scraping finished.");
    }
}