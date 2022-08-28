package jumba.delivery.service.salesorder.service;

import jumba.delivery.service.annotations.Auditable;
import jumba.delivery.service.exceptions.InternalServerErrorException;
import jumba.delivery.service.generic.service.AbstractQueryService;
import jumba.delivery.service.generic.service.AbstractService;
import jumba.delivery.service.mapper.SaleOrderMapper;
import jumba.delivery.service.salesorder.domain.SalesOrderEntity;
import jumba.delivery.service.salesorder.dto.SalesOrderDto;
import jumba.delivery.service.utils.AuditCodes;
import jumba.delivery.service.utils.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class SalesOrderServiceImpl implements SalesOrderService {
    private final AbstractService abstractService;
    private final AbstractQueryService abstractQueryService;

    private final SaleOrderMapper saleOrderMapper;

    private final KafkaTemplate<String, SalesOrderDto> kafkaTemplate;

    @Value("${kafka.sales-order.topic}")
    private String salesOrderTopic;

    @Override
    @Auditable(AuditCodes.CREATE_SALES_ORDER)
    @Transactional
    public Mono<SalesOrderEntity> createSalesOrder(final String jsonString) throws InternalServerErrorException, JSONException {
        JSONObject jsonObject = JsonUtils.getJsonObject(jsonString);
        var data = jsonObject.getJSONObject("data").toString();
        SalesOrderDto salesOrder = (SalesOrderDto) JsonUtils.mapJsonToClass(data, SalesOrderDto.class);
     SalesOrderEntity salesOrderEntity=saleOrderMapper.fromDTO(salesOrder);
        salesOrderEntity.setCode(generateCode());
        return abstractService.create(salesOrderEntity);
    }

    //TODO: implement logic to generate sales order code
    private String generateCode() {

        return"";
    }

    @Override
    @Auditable(AuditCodes.UPDATE_SALES_ORDER)
    @Transactional
    public void updateSalesOrder(final SalesOrderDto salesOrder) {
        SalesOrderEntity salesOrderEntity = saleOrderMapper.fromDTO(salesOrder);
        abstractService.update(salesOrderEntity);
    }

    @Override
    public Mono<SalesOrderEntity> findSalesOrderById(final UUID id) {
        return abstractQueryService.findById(id);
    }

    @Override
    public void postSalesOrder(SalesOrderDto salesOrder) {
        log.info(String.format("Posting Sales Order -> %s", salesOrder));
        this.kafkaTemplate.send(salesOrderTopic, salesOrder);
    }

}
