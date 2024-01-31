package pl.wielkopolan.flightscraper.services.impl;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.wielkopolan.flightscraper.services.PromotionListService;
import pl.wielkopolan.flightscraper.util.DateUtils;
import pl.wielkopolan.flightscraper.util.JsonReader;
import pl.wielkopolan.flightscraper.util.UrlReader;

import java.text.SimpleDateFormat;

@Service
public class HomepagePromotionListService implements PromotionListService {
    private static final String DATE_MIN_PARAM = "&dataMin=";
    private static final String DATE_MAX_PARAM = "&dataMax=";

    @Value("${rainbow.search.api.url.promotions}")
    private String promotionUrl;

    @Override
    public JSONArray getPromotionList() throws JSONException {
        return JsonReader.readJsonArray((UrlReader.readStringFromUrl(prepareUrl())).orElseThrow());
    }

    private String prepareUrl() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String firstDateOfMonth= formatter.format(DateUtils.getFirstDayOfMonth());
        String lastDateOfMonth= formatter.format(DateUtils.getLastDayOfMonth());
        return promotionUrl + DATE_MIN_PARAM + firstDateOfMonth + DATE_MAX_PARAM + lastDateOfMonth;
    }
}
