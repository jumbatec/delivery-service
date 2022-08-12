package jumba.delivery.service.salesorder.service;

import jumba.delivery.service.exceptions.InternalServerErrorException;
import jumba.delivery.service.salesorder.domain.SalesOrderEntity;
import org.json.JSONException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface SalesOrderService {
    Mono<SalesOrderEntity> createSalesOrder(final String salesOrder) throws InternalServerErrorException, JSONException;
    Mono<SalesOrderEntity> updateSalesOrder(final SalesOrderEntity salesOrder);
    Mono<SalesOrderEntity> findSalesOrderById(final UUID id);

    Flux<SalesOrderEntity> findAllSalesOrders();
}
