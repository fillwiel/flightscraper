package pl.wielkopolan.flightpublisher.services.impl;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.wielkopolan.flightpublisher.util.DateUtils;
import pl.wielkopolan.flightpublisher.util.JsonReader;
import pl.wielkopolan.flightpublisher.util.UrlReader;
import pl.wielkopolan.flightpublisher.services.PromotionListService;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

@Service
public class HomepagePromotionListService implements PromotionListService {
    private static final String DATE_MIN_PARAM = "&dataMin=";
    private static final String DATE_MAX_PARAM = "&dataMax=";
    private final String promotionUrl;
    private final int monthsToAdd;

    public HomepagePromotionListService(@Value("${rainbow.search.api.url.promotions}") String promotionUrl,
                                        @Value("${scraper.search.months.forward}") int monthsToAdd) {
        this.promotionUrl = promotionUrl;
        this.monthsToAdd = monthsToAdd;
    }

    @Override
    public JSONArray getPromotionList() throws JSONException {
        return JsonReader.readJsonArray((UrlReader.readStringFromUrl(prepareUrl())).orElseThrow());
    }

    private String prepareUrl() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String today = formatter.format(Calendar.getInstance().getTime());
        String lastDateOfMonth = formatter.format(DateUtils.getLastDayOfMonth(LocalDate.now().plusMonths(monthsToAdd)));
        return promotionUrl + DATE_MIN_PARAM + today + DATE_MAX_PARAM + lastDateOfMonth;
    }
}
