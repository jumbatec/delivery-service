package jumba.delivery.service.deliverynote.service;

import jumba.delivery.service.deliverynote.domain.DeliveryNoteEntity;
import jumba.delivery.service.deliverynote.dto.DeliveryNoteDto;
import jumba.delivery.service.exceptions.InternalServerErrorException;
import org.json.JSONException;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface DeliveryNoteService {
    Mono<DeliveryNoteEntity> createDeliveryNote(final String deliveryNote) throws InternalServerErrorException, JSONException;
   void updateDeliveryNote(final DeliveryNoteDto deliveryNote);
    Mono<DeliveryNoteEntity> findDeliveryNoteById(final UUID id);
    void postDeliveryNote(final DeliveryNoteDto deliveryNote) ;

}
