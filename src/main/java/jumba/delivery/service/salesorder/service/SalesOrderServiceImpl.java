package jumba.delivery.service.salesorder.service;

import jumba.delivery.service.annotations.Auditable;
import jumba.delivery.service.exceptions.EntityNotFoundException;
import jumba.delivery.service.exceptions.InternalServerErrorException;
import jumba.delivery.service.generic.dao.AbstractBaseRepository;
import jumba.delivery.service.generic.service.AbstractServiceImpl;
import jumba.delivery.service.mapper.SaleOrderMapper;
import jumba.delivery.service.salesorder.domain.SalesOrderEntity;
import jumba.delivery.service.salesorder.dto.SalesOrderDto;
import jumba.delivery.service.salesorder.repository.SalesOrderRepository;
import jumba.delivery.service.security.UserContext;
import jumba.delivery.service.utils.AuditCodes;
import jumba.delivery.service.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@Slf4j
public class SalesOrderServiceImpl extends AbstractServiceImpl<SalesOrderEntity, UUID> implements SalesOrderService {


    private final SaleOrderMapper saleOrderMapper;

    private final KafkaTemplate<String, SalesOrderDto> kafkaTemplate;

    private final SalesOrderRepository  salesOrderRepository;

    @Value("${kafka.sales-order.topic}")
    private String salesOrderTopic;

    protected SalesOrderServiceImpl(AbstractBaseRepository<SalesOrderEntity, UUID> repository, SaleOrderMapper saleOrderMapper, KafkaTemplate<String, SalesOrderDto> kafkaTemplate, SalesOrderRepository salesOrderRepository) {
        super(repository);
        this.saleOrderMapper = saleOrderMapper;
        this.kafkaTemplate = kafkaTemplate;
        this.salesOrderRepository = salesOrderRepository;
    }


    @Override
    @Auditable(AuditCodes.CREATE_SALES_ORDER)
    @Transactional
    public SalesOrderEntity createSalesOrder(final UserContext  userContext,final String jsonString) throws InternalServerErrorException, JSONException {
        JSONObject jsonObject = JsonUtils.getJsonObject(jsonString);
        var data = jsonObject.getJSONObject("data").toString();
        SalesOrderDto salesOrder = (SalesOrderDto) JsonUtils.mapJsonToClass(data, SalesOrderDto.class);
        SalesOrderEntity salesOrderEntity=saleOrderMapper.fromDTO(salesOrder);
        salesOrderEntity.setCode(generateCode());
        return super.create(userContext,salesOrderEntity);
    }

    //TODO: implement logic to generate sales order code
    private String generateCode() {

        return"";
    }

    @Override
    @Auditable(AuditCodes.UPDATE_SALES_ORDER)
    @Transactional
    public void updateSalesOrder(final UserContext userContext, final SalesOrderDto salesOrder) {
        SalesOrderEntity salesOrderEntity = saleOrderMapper.fromDTO(salesOrder);
        super.update(userContext,salesOrderEntity);
    }

    @Override
    public SalesOrderEntity findSalesOrderById(final UUID id) throws EntityNotFoundException {
        return salesOrderRepository.findById(id).orElseThrow(
                ()->new EntityNotFoundException("No sales Order found by the given Id"));
    }

    @Override
    public void postSalesOrder(SalesOrderDto salesOrder) {
        log.info(String.format("Posting Sales Order -> %s", salesOrder));
        this.kafkaTemplate.send(salesOrderTopic, salesOrder);
    }

}
