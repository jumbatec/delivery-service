package jumba.delivery.service.consumer;

import jumba.delivery.service.exceptions.InternalServerErrorException;
import jumba.delivery.service.salesorder.service.SalesOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class SalesOrderConsumer {

  private final SalesOrderService salesOrderService;


  @KafkaListener(topics = "${kafka.sales-order.topic}", groupId = "${kafka.sales-order.topic.consumer-group}")
  public void handleMessage(String saleOrderString)  {
    log.debug("Received Sales Order from Kafka ===== : {}", saleOrderString);
    try{
      salesOrderService.createSalesOrder(saleOrderString);
    }
    catch (JSONException|InternalServerErrorException ex){
      log.error("Could not decode sales order");
    }
  }
}
