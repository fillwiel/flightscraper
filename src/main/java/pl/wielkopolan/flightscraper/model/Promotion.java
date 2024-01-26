package pl.wielkopolan.flightscraper.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String destinationCity;
    private String country;
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


    public static final class PromotionBuilder {
        private Promotion promotion;

        private PromotionBuilder() {
            promotion = new Promotion();
        }

        public static PromotionBuilder aPromotion() {
            return new PromotionBuilder();
        }

        public PromotionBuilder withDestinationCity(String destinationCity) {
            promotion.setDestinationCity(destinationCity);
            return this;
        }

        public PromotionBuilder withCountry(String country) {
            promotion.setCountry(country);
            return this;
        }

        public PromotionBuilder withPrice(int price) {
            promotion.setPrice(price);
            return this;
        }

        public Promotion build() {
            return promotion;
        }
    }
}
