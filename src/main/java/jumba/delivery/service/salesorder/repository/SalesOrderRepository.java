package jumba.delivery.service.salesorder.repository;

import jumba.delivery.service.generic.dao.AbstractBaseRepository;
import jumba.delivery.service.salesorder.domain.SalesOrderEntity;

import java.util.Optional;
import java.util.UUID;

public interface SalesOrderRepository extends AbstractBaseRepository<SalesOrderEntity
, UUID> {
   Optional<SalesOrderEntity> findMaxByCodeStartWith(final String code);
}
