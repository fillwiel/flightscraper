package pl.wielkopolan.flightscraper.scheduling;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.wielkopolan.flightscraper.services.impl.ScrapeService;

import java.util.Date;

/**
 * Initiates flight scrape every day
 */
@Component
public class ScheduledFlightScrape {

    private static final Logger log = LoggerFactory.getLogger(ScheduledFlightScrape.class);
    private final ScrapeService scrapeService;

    @Scheduled(cron = "0 * * * * *")
    public void scrapeFlightsFromHomepage() {
        log.info("Running full scrape at: {}", new Date());
        scrapeService.scrapePackages();
        log.info("Scraping finished.");
    }

    @PostConstruct
    public void onStartup() {
        scrapeFlightsFromHomepage();
    }
    @Autowired
    public ScheduledFlightScrape(ScrapeService scrapeService) {
        this.scrapeService = scrapeService;
    }
}