package jumba.delivery.service.deliverytimeslot.service;

import jumba.delivery.service.deliverytimeslot.domain.DeliveryTimeConfigEntity;
import jumba.delivery.service.deliverytimeslot.domain.DeliveryTimeEntity;
import jumba.delivery.service.deliverytimeslot.dto.DeliveryTimeConfigDto;
import jumba.delivery.service.deliverytimeslot.dto.DeliveryTimeDto;
import jumba.delivery.service.deliverytimeslot.resource.PageDto;
import jumba.delivery.service.exceptions.InternalServerErrorException;
import org.json.JSONException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.awt.print.Pageable;
import java.util.UUID;

public interface DeliveryTimeService {

   void createDeliveryTimeConfig(final DeliveryTimeConfigDto deliveryTimeConfigDto);

   void updateDeliveryTimeConfig(final DeliveryTimeConfigDto deliveryTimeConfigDto);

    Flux<DeliveryTimeDto> findDeliveryTimesByMarketId(UUID marketId, PageDto pageDto);

    Flux<DeliveryTimeConfigDto> findDeliveryTimeConfigsByMarketId(UUID marketId, PageDto pageDto);
}
