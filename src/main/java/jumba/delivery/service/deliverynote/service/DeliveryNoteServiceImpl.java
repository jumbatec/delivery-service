package jumba.delivery.service.deliverynote.service;

import jumba.delivery.service.annotations.Auditable;
import jumba.delivery.service.deliverynote.domain.DeliveryNoteEntity;
import jumba.delivery.service.deliverynote.dto.DeliveryNoteDto;
import jumba.delivery.service.deliverynote.repository.DeliveryNoteRepository;
import jumba.delivery.service.exceptions.EntityNotFoundException;
import jumba.delivery.service.exceptions.InternalServerErrorException;
import jumba.delivery.service.generic.dao.AbstractBaseRepository;
import jumba.delivery.service.generic.service.AbstractQueryService;
import jumba.delivery.service.generic.service.AbstractService;
import jumba.delivery.service.generic.service.AbstractServiceImpl;
import jumba.delivery.service.mapper.DeliveryNoteMapper;
import jumba.delivery.service.security.UserContext;
import jumba.delivery.service.utils.AuditCodes;
import jumba.delivery.service.utils.BusinessConstants;
import jumba.delivery.service.utils.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class DeliveryNoteServiceImpl extends AbstractServiceImpl<DeliveryNoteEntity, UUID> implements DeliveryNoteService {

    private final DeliveryNoteMapper deliveryNoteMapper;
    private final KafkaTemplate<String, DeliveryNoteDto> kafkaTemplate;
    private final DeliveryNoteRepository deliveryNoteRepository;

    @Value("${kafka.delivery-note.topic}")
    private String deliveryNoteTopic;

    @Autowired
    protected DeliveryNoteServiceImpl(DeliveryNoteRepository deliveryNoteRepository, DeliveryNoteMapper deliveryNoteMapper, KafkaTemplate<String, DeliveryNoteDto> kafkaTemplate, DeliveryNoteRepository deliveryNoteRepository1) {
        super(deliveryNoteRepository);
        this.deliveryNoteMapper = deliveryNoteMapper;
        this.kafkaTemplate = kafkaTemplate;
        this.deliveryNoteRepository = deliveryNoteRepository1;
    }

    @Override
    @Auditable(AuditCodes.CREATE_DELIVERY_NOTE)
    @Transactional
    public DeliveryNoteEntity createDeliveryNote(final UserContext userContext,final String jsonString) throws InternalServerErrorException, JSONException {
        JSONObject jsonObject = JsonUtils.getJsonObject(jsonString);
        var data = jsonObject.getJSONObject("data").toString();
        DeliveryNoteDto deliveryNoteDto = (DeliveryNoteDto) JsonUtils.mapJsonToClass(data, DeliveryNoteDto.class);
        DeliveryNoteEntity deliveryNoteEntity = deliveryNoteMapper.fromDTO(deliveryNoteDto);
        deliveryNoteEntity.setCode(generateCode(deliveryNoteDto.getSaleOrderCode()));
        return super.create(userContext,deliveryNoteEntity);
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
    public void updateDeliveryNote(final UserContext userContext,final DeliveryNoteDto deliveryNote) {
         super.update(userContext,deliveryNoteMapper.fromDTO(deliveryNote));
    }

    @Override
    public DeliveryNoteEntity findDeliveryNoteById(UUID id) throws EntityNotFoundException {
        return deliveryNoteRepository.findById(id).orElseThrow(
                ()->new EntityNotFoundException("No delivery Note found by given Id"));
    }

    @Override
    public void postDeliveryNote(final UserContext userContext,DeliveryNoteDto deliveryNote) {
        log.info(String.format("Posting Delivery Note -> %s", deliveryNote));
        this.kafkaTemplate.send(deliveryNoteTopic, deliveryNote);
    }


}
