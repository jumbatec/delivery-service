package jumba.delivery.service.salesorder.service;

import jumba.delivery.service.exceptions.InternalServerErrorException;
import jumba.delivery.service.salesorder.domain.SalesOrderEntity;
import jumba.delivery.service.salesorder.dto.SalesOrderDto;
import org.json.JSONException;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface SalesOrderService {
    Mono<SalesOrderEntity> createSalesOrder(final String salesOrder) throws InternalServerErrorException, JSONException;
   void updateSalesOrder(final SalesOrderDto salesOrder);
    Mono<SalesOrderEntity> findSalesOrderById(final UUID id);

    void postSalesOrder(final SalesOrderDto salesOrder) ;
}
