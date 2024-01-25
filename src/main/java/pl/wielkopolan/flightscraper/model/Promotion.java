package pl.wielkopolan.flightscraper.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Promotion {

    @JsonProperty("Nazwa")
    private String destinationCity;

    @JsonProperty("Panstwo")
    private String country;

    @JsonProperty("Cena")
    private int price;

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
