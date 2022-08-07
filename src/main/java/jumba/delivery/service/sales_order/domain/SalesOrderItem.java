package jumba.delivery.service.sales_order.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SalesOrderItem {

    private String name;

    private String code;

    private String description;

    private boolean canBeSold;

    private int alertQuantity;

    private int availableQuantity;

    private int packageCount;

    private BigDecimal price;

    private BigDecimal discount;

    private BigDecimal taxAmount;

    private String unity;

    private String category;

    private String wereHouse;

    private String market;

}
