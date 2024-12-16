package jumba.delivery.service.consumer;

import jumba.delivery.service.deliverynote.service.DeliveryNoteService;
import jumba.delivery.service.exceptions.InternalServerErrorException;
import jumba.delivery.service.security.UserContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class DeliveryNoteConsumer {
    private final DeliveryNoteService deliveryNoteService;

    @KafkaListener(topics = "${kafka.delivery-note.topic}", groupId = "${kafka.delivery-note.topic.consumer-group}")
    public void handleMessage(String deliveryNoteString) {
        log.debug("Received Delivery Note from Kafka ===== : {}", deliveryNoteString);
        try {
            deliveryNoteService.createDeliveryNote(UserContext.getDefaulUserContext(),deliveryNoteString);
        } catch (JSONException | InternalServerErrorException ex) {
            log.error("Could not decode Delivery Note");
        }
    }
}
