package service;

import java.time.LocalDate;
import java.util.List;

import dto.DashboardReportDTO;
import dto.GSTSummaryDTO;
import dto.MonthlySalesDTO;
import model.Bill;

public interface ReportService {

    DashboardReportDTO getDashboardReport();

    GSTSummaryDTO getGSTSummary();

    List<Bill> getAllInvoices();

    List<Bill> getInvoicesBetweenDates(
            LocalDate start,
            LocalDate end);

    String generateAllInvoicesPdf()
            throws Exception;

    String generateInvoicesPdf(
            LocalDate start,
            LocalDate end)
            throws Exception;
    
    List<MonthlySalesDTO> getMonthlySalesReport();
    
    String generateExcelReport() throws Exception;

}