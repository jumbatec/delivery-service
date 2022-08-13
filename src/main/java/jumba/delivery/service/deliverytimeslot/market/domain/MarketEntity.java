package jumba.delivery.service.deliverytimeslot.market.domain;

import jumba.delivery.service.generic.entity.LifeCycleEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name="market")
@Data
public class MarketEntity extends LifeCycleEntity<UUID> {
    private String name;
}
