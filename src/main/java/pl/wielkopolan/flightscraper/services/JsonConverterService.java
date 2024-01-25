package pl.wielkopolan.flightscraper.services;

import org.json.JSONArray;
import pl.wielkopolan.flightscraper.data.PromotionDto;

import java.util.List;

public interface JsonConverterService {
    List<PromotionDto> convertJsonToPromotionDto(JSONArray jsonArray);
}
