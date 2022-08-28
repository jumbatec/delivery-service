package jumba.delivery.service.mapper;

import jumba.delivery.service.deliverynote.domain.DeliveryNoteEntity;
import jumba.delivery.service.deliverynote.dto.DeliveryNoteDto;
import org.mapstruct.Mapper;

@Mapper
public interface DeliveryNoteMapper {

    DeliveryNoteEntity fromDTO(DeliveryNoteDto deliveryNoteDto);

    DeliveryNoteDto toDTO(DeliveryNoteEntity deliveryNoteEntity);
}
