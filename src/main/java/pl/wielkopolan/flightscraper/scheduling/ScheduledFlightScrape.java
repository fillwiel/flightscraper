package pl.wielkopolan.flightscraper.scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.wielkopolan.flightscraper.services.impl.ScrapeService;

import java.io.IOException;
import java.util.Date;

/**
 * Initiates flight scrape every day
 */
@Component
public class ScheduledDailyFlightScrape {

  private static final Logger log = LoggerFactory.getLogger(ScheduledDailyFlightScrape.class);

  @Autowired
  private ScrapeService scrapeService;

  @Scheduled(cron = "0/20 0 0 ? * * *")
  public void scrapeFlightsFromHomepage() throws IOException {
    log.info("Running full scrape at:{}", new Date());
    scrapeService.scrapePackages();
  }
}