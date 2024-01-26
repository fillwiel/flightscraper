package pl.wielkopolan.flightscraper.mapper;

import pl.wielkopolan.flightscraper.data.temporarytobedeleted.PromotionDtoOld;
import pl.wielkopolan.flightscraper.model.Promotion;

public class PromotionMapper {
    public static Promotion mapToPromotion(PromotionDtoOld promotionDtoOld) {
        return Promotion.PromotionBuilder.aPromotion()
                .withDestinationCity(promotionDtoOld.destinationCity())
                .withCountry(promotionDtoOld.country())
                .withPrice(promotionDtoOld.price())
                .build();
    }

    private PromotionMapper() {
        //empty
    }
}
