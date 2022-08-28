package jumba.delivery.service.salesorder.resource;


import jumba.delivery.service.salesorder.domain.SalesOrderEntity;
import jumba.delivery.service.salesorder.dto.SalesOrderDto;
import jumba.delivery.service.salesorder.service.SalesOrderService;
import lombok.RequiredArgsConstructor;
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
    public void updateSalesOrder(@RequestBody SalesOrderDto salesOrderDto) {
        salesOrderService.updateSalesOrder(salesOrderDto);
    }

    @GetMapping("/{id}")
    public Mono<SalesOrderEntity> findDeliveryNoteById(final UUID id) {
        return salesOrderService.findSalesOrderById(id);
    }
}
