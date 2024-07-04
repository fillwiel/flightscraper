package pl.wielkopolan.flightpublisher.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wielkopolan.flightpublisher.data.rainbow.PromotionDto;
import pl.wielkopolan.flightpublisher.services.JsonConverterService;
import pl.wielkopolan.flightpublisher.services.PromotionListService;
import pl.wielkopolan.flightpublisher.services.PromotionProcessingService;

import java.util.List;

@Slf4j
@Service
public class ScrapeService {
    private final PromotionListService promotionListService;
    private final JsonConverterService jsonConverterService;
    private final PromotionProcessingService promotionProcessingService;

    public void scrapePackages() {
        List<PromotionDto> promotionDtoList = jsonConverterService.createPromotionDto(promotionListService.getPromotionList());
        promotionProcessingService.publishFlightsFoundInPromotions(promotionDtoList);
    }

    public ScrapeService(PromotionListService promotionListService, JsonConverterService jsonConverterService,
                         PromotionProcessingService promotionProcessingService) {
        this.promotionListService = promotionListService;
        this.jsonConverterService = jsonConverterService;
        this.promotionProcessingService = promotionProcessingService;
    }
}