package pl.wielkopolan.flightscraper.services;

import org.json.JSONArray;

import java.io.IOException;

public interface PromotionListService {

    JSONArray getPromotionList () throws IOException;

}
