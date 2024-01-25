package pl.wielkopolan.flightscraper.services.impl;

import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class HomepagePromotionListServiceTest {

    @Autowired
    HomepagePromotionListService homepagePromotionListService = new HomepagePromotionListService();

    @Test
    void justDummyGetPromotionList() throws IOException {
        JSONArray promotionList = homepagePromotionListService.getPromotionList();
        System.out.println("promotionList = " + promotionList);
    }

}