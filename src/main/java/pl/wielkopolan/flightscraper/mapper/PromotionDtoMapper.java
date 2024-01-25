package pl.wielkopolan.flightscraper.mapper;

import pl.wielkopolan.flightscraper.data.PromotionDto;
import pl.wielkopolan.flightscraper.model.Promotion;

public class PromotionDtoMapper {
    public static Promotion mapToPromotion(long id, PromotionDto promotionDto) {
        return Promotion.PromotionBuilder.aPromotion()
                .withDestinationCity(promotionDto.destinationCity())
                .withCountry(promotionDto.country())
                .withPrice(promotionDto.price())
                .build();
    }
}
