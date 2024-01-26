package pl.wielkopolan.flightscraper.services.impl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.wielkopolan.flightscraper.data.temporarytobedeleted.PromotionDtoOld;
import pl.wielkopolan.flightscraper.services.PromotionListService;
import pl.wielkopolan.flightscraper.util.DateUtils;
import pl.wielkopolan.flightscraper.util.JsonReader;
import pl.wielkopolan.flightscraper.util.UrlReader;
import pl.wielkopolan.flightscraper.util.jsonconstants.RainbowConstants;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class HomepagePromotionListService implements PromotionListService {

    private static final String DATE_MIN_PARAM = "&dataMin=";
    private static final String DATE_MAX_PARAM = "&dataMax=";

    @Value("${rainbow.search.api.url.promtotions}")
    private String promotionUrl;

    @Override
    public JSONArray getPromotionList() throws IOException, JSONException {
        return JsonReader.readJsonArray(UrlReader.readStringFromUrl(prepareUrl()));
    }

    private String prepareUrl() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String firstDateOfMonth= formatter.format(DateUtils.getFirstDayOfMonth());
        String lastDateOfMonth= formatter.format(DateUtils.getLastDayOfMonth());
        return promotionUrl + DATE_MIN_PARAM + firstDateOfMonth + DATE_MAX_PARAM + lastDateOfMonth;
    }

    public List<PromotionDtoOld> oldgetPromotionList() throws IOException, JSONException {
        JSONArray jsonArray = JsonReader.readJsonArray(UrlReader.readStringFromUrl(promotionUrl));
        List<PromotionDtoOld> promotionDtoOldList = new ArrayList<>();
        jsonArray.iterator().forEachRemaining(element -> {
            JSONObject jsonObject = (JSONObject) element;
            String key = jsonObject.get(RainbowConstants.KLUCZ.getValue()).toString();
            String destinationCity = jsonObject.get(RainbowConstants.NAZWA.getValue()).toString();
            String country = jsonObject.get(RainbowConstants.PANSTWO.getValue()).toString();
            int price = (int) jsonObject.get(RainbowConstants.CENA.getValue());
            promotionDtoOldList.add(new PromotionDtoOld(key, destinationCity, country, price, ""));
        });
        return promotionDtoOldList;
    }
}
