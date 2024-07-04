package pl.wielkopolan.flightpublisher.services;

import pl.wielkopolan.flightpublisher.data.rainbow.PromotionDto;

import java.util.List;

public interface PromotionProcessingService {
    void publishFlightsFoundInPromotions(List<PromotionDto> promotionDtos);
}
