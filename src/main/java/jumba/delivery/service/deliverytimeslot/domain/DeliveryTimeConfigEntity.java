package jumba.delivery.service.deliverytimeslot.domain;

import com.vladmihalcea.hibernate.type.json.JsonType;
import jumba.delivery.service.deliverytimeslot.enums.DayConfigType;
import jumba.delivery.service.generic.entity.LifeCycleEntity;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;
import java.util.UUID;

@Builder
@Entity
@TypeDef(name = "json", typeClass = JsonType.class)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name="delivery_time_config")
@Getter
@Setter
public class DeliveryTimeConfigEntity extends LifeCycleEntity<UUID> {

    private DayConfigType dayConfigType;

    @Column(unique = true,nullable = false)
    private UUID marketId;

    @Column(nullable = false)
    private String marketName;
    
    @Type(type = "json")
    @Column(columnDefinition = "jsonb")
    List<DeliveryConfig> deliveryConfigs;
}
