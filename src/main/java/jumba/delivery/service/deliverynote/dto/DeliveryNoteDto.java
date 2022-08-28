package jumba.delivery.service.deliverynote.dto;

import jumba.delivery.service.salesorder.domain.SalesOrderItem;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class DeliveryNoteDto implements Serializable {
    public static Long serialVersionId = 1L;
    private UUID marketId;

    private String saleOrderCode;

    private String customer;

    private String company;

    private String vehicle;

    private List<SalesOrderItem> items = new ArrayList<>();

    private Double netAmount;

    private Double baseTotal;

    private Double discount;

    private String currency;

    private String customerMobile;

}
