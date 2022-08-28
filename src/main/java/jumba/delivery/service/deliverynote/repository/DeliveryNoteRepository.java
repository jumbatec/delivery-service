package jumba.delivery.service.deliverynote.repository;

import jumba.delivery.service.deliverynote.domain.DeliveryNoteEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface DeliveryNoteRepository extends ReactiveCrudRepository<DeliveryNoteEntity
, UUID>{
   Optional<DeliveryNoteEntity> findMaxByCodeStartWith(final String code);
}
