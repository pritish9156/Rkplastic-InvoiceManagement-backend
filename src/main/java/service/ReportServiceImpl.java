package service;

import java.time.LocalDate;
import java.util.List;

import DAO.ReportDAO;
import dto.DashboardReportDTO;
import dto.GSTSummaryDTO;
import dto.MonthlySalesDTO;
import model.Bill;
import report.ExcelReportGenerator;
import report.ReportPdfGenerator;

public class ReportServiceImpl
        implements ReportService {

    private final ReportDAO reportDAO =
            new ReportDAO();

    private final ReportPdfGenerator pdfGenerator =
            new ReportPdfGenerator();

    @Override
    public DashboardReportDTO getDashboardReport() {

        DashboardReportDTO dto =
                new DashboardReportDTO();

        dto.setTotalInvoices(
                reportDAO.getTotalInvoices());

        dto.setTodaySales(
                reportDAO.getTodaySales());

        dto.setMonthlySales(
                reportDAO.getMonthlySales());

        dto.setGstCollected(
                reportDAO.getTotalGST());

        return dto;
    }

    @Override
    public GSTSummaryDTO getGSTSummary() {

        GSTSummaryDTO dto =
                new GSTSummaryDTO();

        dto.setCgst(
                reportDAO.getCGST());

        dto.setSgst(
                reportDAO.getSGST());

        dto.setIgst(
                reportDAO.getIGST());

        dto.setTotalGST(

                dto.getCgst()

                .add(dto.getSgst())

                .add(dto.getIgst())

        );

        return dto;
    }

    @Override
    public List<Bill> getInvoicesBetweenDates(
            LocalDate start,
            LocalDate end) {

        return reportDAO.getInvoicesBetweenDates(
                start,
                end);

    }

    @Override
    public List<Bill> getAllInvoices() {

        return reportDAO.getAllInvoices();

    }

    @Override
    public String generateAllInvoicesPdf()
            throws Exception {

        List<Bill> bills =
                reportDAO.getAllInvoices();

        return pdfGenerator.generateAll(
                bills);

    }

    @Override
    public String generateInvoicesPdf(
            LocalDate start,
            LocalDate end)
            throws Exception {

        List<Bill> bills =
                reportDAO.getInvoicesBetweenDates(
                        start,
                        end);

        return pdfGenerator.generateByDate(

                bills,

                start,

                end

        );

    }
    
    @Override
    public List<MonthlySalesDTO> getMonthlySalesReport() {

        return reportDAO
                .getMonthlySalesReport();

    }
    
    @Override
    public String generateExcelReport() throws Exception {

        return ExcelReportGenerator.generate(
                reportDAO.getAllInvoices());
    }

}