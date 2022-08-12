package jumba.delivery.service.deliverytimeslot.domain;

import jumba.delivery.service.deliverytimeslot.enums.DayConfigType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@NoArgsConstructor
@Getter
@Setter
public class DeliveryConfigs {

    private LocalTime startTime;

    private LocalTime endTime;

    private LocalTime dispatchTime;

    private LocalTime leadTime;

    private DayConfigType dayConfigType;


}
