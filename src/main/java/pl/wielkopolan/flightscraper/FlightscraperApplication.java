package pl.wielkopolan.flightscraper;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableMongoRepositories
@EnableScheduling
@SpringBootApplication
public class FlightscraperApplication implements CommandLineRunner{
	public static void main(String[] args) {
		SpringApplication.run(FlightscraperApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//empty for now - not sure if can be removed.
		//app is run by scheduler
	}

}
