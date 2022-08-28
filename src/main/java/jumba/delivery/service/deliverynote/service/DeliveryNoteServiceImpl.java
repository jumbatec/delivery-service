package jumba.delivery.service.deliverynote.service;

import jumba.delivery.service.annotations.Auditable;
import jumba.delivery.service.deliverynote.domain.DeliveryNoteEntity;
import jumba.delivery.service.deliverynote.dto.DeliveryNoteDto;
import jumba.delivery.service.deliverynote.repository.DeliveryNoteRepository;
import jumba.delivery.service.exceptions.InternalServerErrorException;
import jumba.delivery.service.generic.service.AbstractQueryService;
import jumba.delivery.service.generic.service.AbstractService;
import jumba.delivery.service.mapper.DeliveryNoteMapper;
import jumba.delivery.service.utils.AuditCodes;
import jumba.delivery.service.utils.BusinessConstants;
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
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class DeliveryNoteServiceImpl implements DeliveryNoteService {
    private final AbstractService abstractService;
    private final AbstractQueryService abstractQueryService;
    private final DeliveryNoteMapper deliveryNoteMapper;
    private final KafkaTemplate<String, DeliveryNoteDto> kafkaTemplate;

    private final DeliveryNoteRepository deliveryNoteRepository;

    @Value("${kafka.delivery-note.topic}")
    private String deliveryNoteTopic;

    @Override
    @Auditable(AuditCodes.CREATE_DELIVERY_NOTE)
    @Transactional
    public Mono<DeliveryNoteEntity> createDeliveryNote(final String jsonString) throws InternalServerErrorException, JSONException {
        JSONObject jsonObject = JsonUtils.getJsonObject(jsonString);
        var data = jsonObject.getJSONObject("data").toString();
        DeliveryNoteDto deliveryNoteDto = (DeliveryNoteDto) JsonUtils.mapJsonToClass(data, DeliveryNoteDto.class);
        DeliveryNoteEntity deliveryNoteEntity = deliveryNoteMapper.fromDTO(deliveryNoteDto);
        deliveryNoteEntity.setCode(generateCode(deliveryNoteDto.getSaleOrderCode()));
        return abstractService.create(deliveryNoteEntity);
    }

    private String generateCode(String saleOrderCode) {
        Optional<DeliveryNoteEntity> deliveryNoteOptional = deliveryNoteRepository
                .findMaxByCodeStartWith(BusinessConstants.DELIVERY_NOTE_PREFIX);
//  TODO:Logic to generate code to be added
//   Long
//        String code =
//                "DN-" +saleOrderCode.split("-")[2] +
//                        "-" +;
//        return deliveryNoteRepository.findByKyoskCode(code).isEmpty() ? code : generateCode(territory, saleOrderCode, dispatchTime);
//
        return "";
    }

    @Override
    @Auditable(AuditCodes.UPDATE_DELIVERY_NOTE)
    @Transactional
    public void updateDeliveryNote(final DeliveryNoteDto deliveryNote) {
         abstractService.update(deliveryNoteMapper.fromDTO(deliveryNote));
    }

    @Override
    public Mono<DeliveryNoteEntity> findDeliveryNoteById(UUID id) {
        return abstractQueryService.findById(id);
    }

    @Override
    public void postDeliveryNote(DeliveryNoteDto deliveryNote) {
        log.info(String.format("Posting Delivery Note -> %s", deliveryNote));
        this.kafkaTemplate.send(deliveryNoteTopic, deliveryNote);
    }


}
