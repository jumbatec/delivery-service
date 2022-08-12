package jumba.delivery.service.mapper;

import jumba.delivery.service.deliverytimeslot.domain.DeliveryTimeConfigEntity;
import jumba.delivery.service.deliverytimeslot.dto.DeliveryTimeConfigDto;
import org.mapstruct.Mapper;

@Mapper
public interface DeliveryTimeConfigMapper {

    DeliveryTimeConfigEntity fromDTO(DeliveryTimeConfigDto deliveryTimeConfigDto);

    DeliveryTimeConfigDto  toDTO(DeliveryTimeConfigEntity deliveryTimeConfig);
}
