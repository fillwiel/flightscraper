package pl.wielkopolan.flightpublisher.scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.wielkopolan.flightpublisher.services.impl.ScrapeService;

import java.util.Date;

/**
 * Initiates flight scrape every hour
 */
@Component
public class ScheduledFlightScrape {
    private static final Logger log = LoggerFactory.getLogger(ScheduledFlightScrape.class);
    private final ScrapeService scrapeService;

    @Scheduled(fixedRate=60*60*1000)
    public void scrapeFlightsFromHomepage() {
        log.info("Running full scrape at: {}", new Date());
        scrapeService.scrapePackages();
        log.info("Scraping finished.");
    }

    public ScheduledFlightScrape(ScrapeService scrapeService) {
        this.scrapeService = scrapeService;
    }
}