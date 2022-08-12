package jumba.delivery.service.mapper;

import jumba.delivery.service.deliverytimeslot.domain.DeliveryTimeEntity;
import jumba.delivery.service.deliverytimeslot.dto.DeliveryTimeDto;
import org.mapstruct.Mapper;

@Mapper
public interface DeliveryTimeMapper {

    DeliveryTimeEntity fromDTO(DeliveryTimeDto deliveryTimeDto);

    DeliveryTimeDto  toDTO(DeliveryTimeEntity deliveryTimeEntity);
}
