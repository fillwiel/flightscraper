package pl.wielkopolan.flightpersistence.util;

import lombok.Getter;

/**
 * Constants used for identifying JSON keys.
 */
@Getter
public enum RainbowConstants {
    ID("id"),
    ID_CAMELCASE("Id"),
    DATA("Data"),
    CENA("Cena"),
    PRICE("price"),
    GODZINA("Godzina"),
    BILETY("Bilety"),
    WYLOT("Wylot"),
    PRZYLOT("Przylot"),
    PRZYSTANEK("Przystanek"),
    PAKIETY("Pakiety"),
    REGION("Region"),
    IATA("Iata"),
    WYBRANA("Wybrana"),
    KLUCZ("Klucz"),
    NAZWA("Nazwa"),
    CATEGORY("category"),
    PANSTWO("Panstwo");

    private final String value;

    RainbowConstants(String value) {
        this.value = value;
    }

}
