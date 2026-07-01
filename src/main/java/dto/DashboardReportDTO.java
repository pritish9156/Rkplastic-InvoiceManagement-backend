package dto;

import java.math.BigDecimal;

public class DashboardReportDTO {

    private Long totalInvoices;

    private BigDecimal todaySales;

    private BigDecimal monthlySales;

    private BigDecimal gstCollected;

    public DashboardReportDTO() {
    }

    public DashboardReportDTO(Long totalInvoices,
                              BigDecimal todaySales,
                              BigDecimal monthlySales,
                              BigDecimal gstCollected) {
        this.totalInvoices = totalInvoices;
        this.todaySales = todaySales;
        this.monthlySales = monthlySales;
        this.gstCollected = gstCollected;
    }

    public Long getTotalInvoices() {
        return totalInvoices;
    }

    public void setTotalInvoices(Long totalInvoices) {
        this.totalInvoices = totalInvoices;
    }

    public BigDecimal getTodaySales() {
        return todaySales;
    }

    public void setTodaySales(BigDecimal todaySales) {
        this.todaySales = todaySales;
    }

    public BigDecimal getMonthlySales() {
        return monthlySales;
    }

    public void setMonthlySales(BigDecimal monthlySales) {
        this.monthlySales = monthlySales;
    }

    public BigDecimal getGstCollected() {
        return gstCollected;
    }

    public void setGstCollected(BigDecimal gstCollected) {
        this.gstCollected = gstCollected;
    }
}