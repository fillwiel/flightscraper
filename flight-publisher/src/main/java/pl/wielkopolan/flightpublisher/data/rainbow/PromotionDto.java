package pl.wielkopolan.flightpublisher.data.rainbow;

import java.util.Date;
import java.util.List;

public record PromotionDto(Date date, int price, List<TicketDto> tickets) {
}
