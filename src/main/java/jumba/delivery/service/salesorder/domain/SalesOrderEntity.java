package jumba.delivery.service.salesorder.domain;

import jumba.delivery.service.generic.entity.LifeCycleEntity;
import jumba.delivery.service.salesorder.domain.enums.SalesOrderState;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import com.vladmihalcea.hibernate.type.json.JsonType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@TypeDef(name = "json", typeClass = JsonType.class)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(name="sales_order")
@Getter
@Setter
public class SalesOrderEntity  extends LifeCycleEntity<UUID> {

    @Column(unique = true,nullable = false)
    private String code;

    @Column(nullable = false)
    private UUID clientId;

    @Column(nullable = false)
    private BigDecimal orderedAmount;

    @Column(nullable = false)
    private BigDecimal orderedNetAmount;

    @Column(nullable = false)
    private BigDecimal tax;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private SalesOrderState sate;

    @Type(type = "json")
    @Column(columnDefinition = "jsonb")
    @Builder.Default
    List<SalesOrderItem> items = new ArrayList<>();
}
