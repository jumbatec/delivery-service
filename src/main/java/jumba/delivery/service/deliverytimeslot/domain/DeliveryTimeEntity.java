package jumba.delivery.service.deliverytimeslot.domain;

import jumba.delivery.service.generic.entity.LifeCycleEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name="delivery_time")
@Getter
@Setter
@Builder
public class DeliveryTimeEntity extends LifeCycleEntity<UUID> {

    @Column(unique = true,nullable = false)
    private LocalDate deliveryDate;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Column(nullable = false)
    private LocalTime dispatchTime;

    @Column(nullable = false)
    private LocalTime leadTime;

    private UUID deliveryConfigId;

    @Column(unique = true,nullable = false)
    private UUID marketId;

    @Column(nullable = false)
    boolean available;
}
