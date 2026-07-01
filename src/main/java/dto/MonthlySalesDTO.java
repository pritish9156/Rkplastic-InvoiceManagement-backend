package dto;

import java.math.BigDecimal;

public class MonthlySalesDTO {

    private String month;

    private BigDecimal amount;

    public MonthlySalesDTO() {
    }

    public MonthlySalesDTO(String month,
                           BigDecimal amount) {
        this.month = month;
        this.amount = amount;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}