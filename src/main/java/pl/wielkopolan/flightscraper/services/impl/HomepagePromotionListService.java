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
import java.time.LocalDate;
import java.util.Calendar;

@Service
public class HomepagePromotionListService implements PromotionListService {
    private static final String DATE_MIN_PARAM = "&dataMin=";
    private static final String DATE_MAX_PARAM = "&dataMax=";

    @Value("${rainbow.search.api.url.promotions}")
    private String promotionUrl;
    @Value("${scraper.search.months.forward}")
    private int monthsToAdd;

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
