package jumba.delivery.service.mapper;

import jumba.delivery.service.salesorder.domain.SalesOrderEntity;
import jumba.delivery.service.salesorder.dto.SalesOrderDto;
import org.mapstruct.Mapper;

@Mapper
public interface SaleOrderMapper {

    SalesOrderEntity fromDTO(SalesOrderDto salesOrderDto);

    SalesOrderDto toDTO(SalesOrderEntity salesOrderEntity);
}
