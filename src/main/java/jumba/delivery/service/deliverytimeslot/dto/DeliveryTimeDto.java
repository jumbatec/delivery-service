package jumba.delivery.service.deliverytimeslot.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
public class DeliveryTimeDto {

    private LocalDate deliveryDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private LocalTime dispatchTime;

    private LocalTime leadTime;

    private UUID deliveryConfigId;

    private UUID marketId;

    private String marketName;

    boolean available;
}
