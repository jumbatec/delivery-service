package jumba.delivery.service.salesorder.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class SalesOrderItem implements Serializable {
    public static Long serialVersionId = 1L;
    private String name;

    private String itemCode;

    private String description;

    private double qty;

    private double itemHeight;

    private double itemLength;

    private double itemWidth;

    private double conversionFactor;

    private double weightPerUnit;

    private double baseNateRete;

    private double amount;

    private double rate;
}
