package jumba.delivery.service.deliverynote.service;

import jumba.delivery.service.deliverynote.domain.DeliveryNoteEntity;
import jumba.delivery.service.deliverynote.dto.DeliveryNoteDto;
import jumba.delivery.service.exceptions.EntityNotFoundException;
import jumba.delivery.service.exceptions.InternalServerErrorException;
import jumba.delivery.service.security.UserContext;
import org.json.JSONException;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

public interface DeliveryNoteService {
    DeliveryNoteEntity createDeliveryNote(final UserContext userContext,final String deliveryNote) throws InternalServerErrorException, JSONException;
   void updateDeliveryNote(final UserContext userContext,final DeliveryNoteDto deliveryNote);
    DeliveryNoteEntity findDeliveryNoteById(final UUID id) throws EntityNotFoundException;
    void postDeliveryNote(final UserContext userContext,final DeliveryNoteDto deliveryNote) ;

}
