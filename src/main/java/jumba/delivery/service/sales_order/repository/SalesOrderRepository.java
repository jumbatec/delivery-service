package jumba.delivery.service.sales_order.repository;

import jumba.delivery.service.sales_order.domain.SalesOrderEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;


import java.util.UUID;

public interface SalesOrderRepository extends ReactiveCrudRepository<SalesOrderEntity
, UUID>{

}
