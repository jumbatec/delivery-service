package jumba.delivery.service.deliverytimeslot.resource;


import jumba.delivery.service.deliverytimeslot.dto.DeliveryTimeConfigDto;
import jumba.delivery.service.deliverytimeslot.dto.DeliveryTimeDto;
import jumba.delivery.service.deliverytimeslot.service.DeliveryTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.UUID;

@RestController
@RequestMapping("delivery-service/api/v1/delivery-time")
@RequiredArgsConstructor
public class DeliveryTimeResource {
    private final DeliveryTimeService deliveryTimeService;

    @PostMapping
    public void createDeliveryTimeConfig(@RequestBody DeliveryTimeConfigDto deliveryTimeConfigDto){
        deliveryTimeService.createDeliveryTimeConfig(deliveryTimeConfigDto);
    }

    @PutMapping
    public void updateDeliveryTimeConfig(@RequestBody DeliveryTimeConfigDto deliveryTimeConfigDto){
        deliveryTimeService.updateDeliveryTimeConfig(deliveryTimeConfigDto);
    }

    @GetMapping("/delivery-configs")
    public Flux<DeliveryTimeConfigDto> findDeliveryTimeConfigsByMarketId(final UUID marketId,final  PageDto pageDto) {
        return deliveryTimeService.findDeliveryTimeConfigsByMarketId(marketId, pageDto);
    }

    @GetMapping("/available-slots")
    public Flux<DeliveryTimeDto> findAvailableTimeSlots(final UUID marketId, final  PageDto pageDto,final Integer daysInAdvance) {
        return deliveryTimeService.findDeliveryTimesByMarketId(marketId, pageDto,daysInAdvance);
    }
}
