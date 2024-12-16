package jumba.delivery.service.deliverytimeslot.repository;

import jumba.delivery.service.deliverytimeslot.domain.DeliveryTimeEntity;
import jumba.delivery.service.generic.dao.AbstractBaseRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.Set;
import java.util.UUID;

public interface DeliveryTimeRepository extends AbstractBaseRepository<DeliveryTimeEntity
, UUID> {
    Set<DeliveryTimeEntity> findByMarketId(UUID marketId, Pageable pageable);
}
