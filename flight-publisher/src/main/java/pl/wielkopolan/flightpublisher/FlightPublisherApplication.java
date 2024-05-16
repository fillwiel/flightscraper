package pl.wielkopolan.flightpublisher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
@EnableScheduling
@SpringBootApplication
public class FlightPublisherApplication {
    public static void main(String[] args) {
        SpringApplication.run(FlightPublisherApplication.class, args);
    }

}
