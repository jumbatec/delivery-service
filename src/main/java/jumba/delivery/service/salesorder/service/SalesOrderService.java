package jumba.delivery.service.salesorder.service;

import jumba.delivery.service.exceptions.EntityNotFoundException;
import jumba.delivery.service.exceptions.InternalServerErrorException;
import jumba.delivery.service.salesorder.domain.SalesOrderEntity;
import jumba.delivery.service.salesorder.dto.SalesOrderDto;
import jumba.delivery.service.security.UserContext;
import org.json.JSONException;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface SalesOrderService {

    SalesOrderEntity createSalesOrder(final UserContext  userContext,final String salesOrder) throws InternalServerErrorException, JSONException;

 void updateSalesOrder(final UserContext  userContext,final SalesOrderDto salesOrder);

  SalesOrderEntity findSalesOrderById(final UUID id) throws EntityNotFoundException;

    void postSalesOrder(final SalesOrderDto salesOrder) ;
}
