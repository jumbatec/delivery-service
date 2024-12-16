package jumba.delivery.service.deliverytimeslot.market.domain;

import jumba.delivery.service.generic.entity.LifeCycleEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="market")
@Data
public class MarketEntity extends LifeCycleEntity<UUID> {
    private String name;
}
