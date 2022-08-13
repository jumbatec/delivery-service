package jumba.delivery.service.deliverytimeslot.service;

import jumba.delivery.service.deliverytimeslot.dto.DeliveryTimeConfigDto;
import jumba.delivery.service.deliverytimeslot.dto.DeliveryTimeDto;
import jumba.delivery.service.deliverytimeslot.resource.PageDto;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;

public interface DeliveryTimeService {

   void createDeliveryTimeConfig(final DeliveryTimeConfigDto deliveryTimeConfigDto);

   void updateDeliveryTimeConfig(final DeliveryTimeConfigDto deliveryTimeConfigDto);

    Flux<DeliveryTimeDto> findDeliveryTimesByMarketId(UUID marketId, PageDto pageDto, Integer daysInAdvance);

    Flux<DeliveryTimeConfigDto> findDeliveryTimeConfigsByMarketId(UUID marketId, PageDto pageDto);
}
