package jumba.delivery.service.consumer;

import jumba.delivery.service.exceptions.InternalServerErrorException;
import jumba.delivery.service.sales_order.service.SalesOrderService;
import jumba.delivery.service.security.UserContext;
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


  @KafkaListener(topics = "${io.confluent.developer.config.topics.orders.name}", groupId = "${consumer.delivery-service.consumer-group}")
  public void handleMessage(String saleOrderString)  {
    log.debug("Received Sales Order from Kafka ===== : {}", saleOrderString);
    //TODO: Authenticate Kafka Service and create userContext
    UserContext userContext=new UserContext();
    try{
      salesOrderService.createSalesOrder(userContext,saleOrderString);
    }
    catch (JSONException|InternalServerErrorException ex){
      log.error("Could not decode sales order");
    }
  }
}
