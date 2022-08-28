package jumba.delivery.service.deliverynote.resource;


import jumba.delivery.service.deliverynote.domain.DeliveryNoteEntity;
import jumba.delivery.service.deliverynote.dto.DeliveryNoteDto;
import jumba.delivery.service.deliverynote.service.DeliveryNoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("delivery-service/api/v1/delivery-note")
@RequiredArgsConstructor
public class DeliveryNoteResource {
    private final DeliveryNoteService deliveryNoteService;

    @PostMapping
    public void postDeliveryNote(@RequestBody DeliveryNoteDto deliveryNoteDto) {
        deliveryNoteService.postDeliveryNote(deliveryNoteDto);
    }

    @PutMapping
    public void updateDeliveryNote(@RequestBody DeliveryNoteDto deliveryNoteDto) {
        deliveryNoteService.updateDeliveryNote(deliveryNoteDto);
    }

    @GetMapping("/{id}")
    public Mono<DeliveryNoteEntity> findDeliveryNoteById(final UUID id) {
        return deliveryNoteService.findDeliveryNoteById(id);
    }
}
