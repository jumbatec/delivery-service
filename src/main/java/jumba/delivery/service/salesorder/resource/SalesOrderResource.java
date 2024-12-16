package jumba.delivery.service.salesorder.resource;


import jumba.delivery.service.exceptions.EntityNotFoundException;
import jumba.delivery.service.salesorder.domain.SalesOrderEntity;
import jumba.delivery.service.salesorder.dto.SalesOrderDto;
import jumba.delivery.service.salesorder.service.SalesOrderService;
import jumba.delivery.service.security.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("delivery-service/api/v1/sales-order")
@RequiredArgsConstructor
public class SalesOrderResource {
    private final SalesOrderService salesOrderService;

    @PostMapping
    public void postSalesOrder(@RequestBody SalesOrderDto salesOrderDto) {
        salesOrderService.postSalesOrder(salesOrderDto);
    }

    @PutMapping
    public void updateSalesOrder(final UserContext userContext, @RequestBody SalesOrderDto salesOrderDto) {
        salesOrderService.updateSalesOrder(userContext,salesOrderDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalesOrderEntity> findDeliveryNoteById(final UUID id) throws EntityNotFoundException {
        return ResponseEntity.ok(salesOrderService.findSalesOrderById(id));
    }
}
