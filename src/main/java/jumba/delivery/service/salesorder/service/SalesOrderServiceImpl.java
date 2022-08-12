package jumba.delivery.service.salesorder.service;

import jumba.delivery.service.annotations.Auditable;
import jumba.delivery.service.exceptions.InternalServerErrorException;
import jumba.delivery.service.generic.service.AbstractQueryService;
import jumba.delivery.service.generic.service.AbstractService;
import jumba.delivery.service.salesorder.domain.SalesOrderEntity;
import jumba.delivery.service.utils.AuditCodes;
import jumba.delivery.service.utils.JsonUtils;
import lombok.AllArgsConstructor;
import org.json.JSONException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.json.JSONObject;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SalesOrderServiceImpl implements SalesOrderService{
    private final AbstractService abstractService;
    private final AbstractQueryService abstractQueryService;

    @Override
    @Auditable(AuditCodes.CREATE_SALES_ORDER)
    @Transactional
    public Mono<SalesOrderEntity> createSalesOrder( final String jsonString) throws InternalServerErrorException, JSONException {
        JSONObject jsonObject = JsonUtils.getJsonObject(jsonString);
        var data = jsonObject.getJSONObject("data").toString();
        SalesOrderEntity salesOrder = (SalesOrderEntity) JsonUtils.mapJsonToClass(data, SalesOrderEntity.class);
        return abstractService.create(salesOrder);
    }

    @Override
    @Auditable(AuditCodes.UPDATE_SALES_ORDER)
    @Transactional
    public Mono<SalesOrderEntity> updateSalesOrder(final SalesOrderEntity salesOrder) {
        return abstractService.update(salesOrder);
    }

    @Override
    public Mono<SalesOrderEntity> findSalesOrderById(final UUID id) {
        return abstractQueryService.findById(id);
    }

    @Override
    public Flux<SalesOrderEntity> findAllSalesOrders() {
        return abstractQueryService.findAll();
    }
}
