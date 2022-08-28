package jumba.delivery.service.deliverynote.domain;

import jumba.delivery.service.deliverynote.domain.enums.DeliveryNoteState;
import jumba.delivery.service.generic.entity.LifeCycleEntity;
import jumba.delivery.service.salesorder.domain.SalesOrderEntity;
import jumba.delivery.service.salesorder.domain.SalesOrderItem;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import com.vladmihalcea.hibernate.type.json.JsonType;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@TypeDef(name = "json", typeClass = JsonType.class)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="delivery_note")
@Getter
@Setter
public class DeliveryNoteEntity extends LifeCycleEntity<UUID> {

    @Column
    UUID marketId;

    @Column( unique = true)
    String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_order_id", referencedColumnName = "id")
    SalesOrderEntity saleOrder;

    @Column(name = "sale_order_id", insertable = false,updatable = false)
    UUID saleOrderId;

    @Column(nullable = false)
    String customer;

    @Column(nullable = false)
    String company;

    @Column
    Instant postingTime;

    @Enumerated(EnumType.STRING)
    DeliveryNoteState deliveryNoteState = DeliveryNoteState.DRAFT;

    @Column
    String vehicle;

    @Type(type = "json")
    @Column(columnDefinition = "jsonb")
    @Builder.Default
    List<SalesOrderItem> items = new ArrayList<>();

    @Column
    private Instant createdDate;

    @Column
    private Instant completedDate;

    @Column
    private Double netAmount;

    @Column
    private Double baseTotal;

    @Column
    private Double discount;

    @Column
    private String currency;

    @Column
    private String customerMobile;

    @Column
    Boolean published;

    @Column
    boolean inTrip;
}
