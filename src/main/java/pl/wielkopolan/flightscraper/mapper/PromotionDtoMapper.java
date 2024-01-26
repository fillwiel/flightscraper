package pl.wielkopolan.flightscraper.mapper;

import org.json.JSONObject;
import pl.wielkopolan.flightscraper.data.temporarytobedeleted.PromotionDtoOld;
import pl.wielkopolan.flightscraper.util.jsonconstants.RainbowConstants;

public class PromotionDtoMapper {
    public static PromotionDtoOld mapToPromotion(JSONObject jsonObject) {
        return new PromotionDtoOld(jsonObject.get(RainbowConstants.KLUCZ.getValue()).toString(),
                jsonObject.get(RainbowConstants.NAZWA.getValue()).toString(),
                jsonObject.get(RainbowConstants.PANSTWO.getValue()).toString(),
                (int) jsonObject.get(RainbowConstants.CENA.getValue()), "");
    }

    private PromotionDtoMapper() {
        //empty
    }
}
