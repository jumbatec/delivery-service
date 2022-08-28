package jumba.delivery.service.salesorder.repository;

import jumba.delivery.service.deliverynote.domain.DeliveryNoteEntity;
import jumba.delivery.service.salesorder.domain.SalesOrderEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface SalesOrderRepository extends ReactiveCrudRepository<SalesOrderEntity
, UUID>{
   Optional<SalesOrderEntity> findMaxByCodeStartWith(final String code);
}
