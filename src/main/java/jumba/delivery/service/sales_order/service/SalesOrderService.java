package jumba.delivery.service.sales_order.service;

import jumba.delivery.service.exceptions.InternalServerErrorException;
import jumba.delivery.service.sales_order.domain.SalesOrderEntity;
import jumba.delivery.service.security.UserContext;
import org.json.JSONException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface SalesOrderService {
    Mono<SalesOrderEntity> createSalesOrder(final UserContext userContext,final String salesOrder) throws InternalServerErrorException, JSONException;
    Mono<SalesOrderEntity> updateSalesOrder(final UserContext userContext,final SalesOrderEntity salesOrder);
    Mono<SalesOrderEntity> findSalesOrderById(final UUID id);

    Flux<SalesOrderEntity> findAllSalesOrders();
}
