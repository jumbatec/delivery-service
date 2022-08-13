package jumba.delivery.service.deliverytimeslot.dto;

import jumba.delivery.service.deliverytimeslot.domain.DeliveryConfig;
import lombok.Data;


import javax.persistence.Column;
import java.util.List;
import java.util.UUID;

@Data
public class DeliveryTimeConfigDto {

    @Column(unique = true,nullable = false)
    private UUID marketId;

    @Column(nullable = false)
    private String marketName;

    List<DeliveryConfig> deliveryConfigs;
}
