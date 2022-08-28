package jumba.delivery.service.salesorder.dto;

import jumba.delivery.service.salesorder.domain.SalesOrderItem;
import jumba.delivery.service.salesorder.domain.enums.SalesOrderState;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class SalesOrderDto implements Serializable {
    public static Long serialVersionId=1L;
    List<SalesOrderItem> items = new ArrayList<>();
    private String code;
    private BigDecimal orderedAmount;
    private BigDecimal orderedNetAmount;
    private BigDecimal tax;
    private int quantity;
    private SalesOrderState sate;
}
