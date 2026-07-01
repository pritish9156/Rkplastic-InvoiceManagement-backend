package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import dto.DashboardReportDTO;
import dto.GSTSummaryDTO;
import dto.MonthlySalesDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Bill;
import service.ReportService;
import service.ReportServiceImpl;
import util.ApiResponse;
import util.JsonUtil;

@WebServlet("/api/reports/*")
public class ReportServlet extends HttpServlet {

    private final ReportService reportService =
            new ReportServiceImpl();

    private final ObjectMapper mapper =
            JsonUtil.getMapper();

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        String path = req.getPathInfo();

        if(path == null)
            path = "/";

        try {

            switch(path) {

            case "/dashboard":

                DashboardReportDTO dashboard =
                        reportService.getDashboardReport();

                resp.setContentType("application/json");

                mapper.writeValue(
                        resp.getOutputStream(),
                        dashboard);

                return;

            case "/gst":

                GSTSummaryDTO gst =
                        reportService.getGSTSummary();

                resp.setContentType("application/json");

                mapper.writeValue(
                        resp.getOutputStream(),
                        gst);

                return;

            case "/all":

                List<Bill> bills =
                        reportService.getAllInvoices();

                resp.setContentType("application/json");

                mapper.writeValue(
                        resp.getOutputStream(),
                        bills);

                return;

            case "/date-range":

                LocalDate start =
                        LocalDate.parse(
                                req.getParameter("start"));

                LocalDate end =
                        LocalDate.parse(
                                req.getParameter("end"));

                List<Bill> reportBills =
                        reportService
                        .getInvoicesBetweenDates(
                                start,
                                end);

                resp.setContentType("application/json");

                mapper.writeValue(
                        resp.getOutputStream(),
                        reportBills);

                return;

            case "/download/all-pdf":

                downloadAllPdf(resp);

                return;

            case "/download/date-pdf":

                downloadDatePdf(req, resp);

                return;
                
            case "/monthly":

                List<MonthlySalesDTO> monthly =

                        reportService
                        .getMonthlySalesReport();

                resp.setContentType("application/json");

                mapper.writeValue(

                        resp.getOutputStream(),

                        monthly);

                return;
                
            case "/download/excel":

                downloadExcel(resp);

                return;

            default:

                resp.setStatus(404);

                mapper.writeValue(
                        resp.getOutputStream(),

                        new ApiResponse(

                                false,

                                "Invalid API",

                                null));

            }

        }
        catch(Exception e) {

            e.printStackTrace();

            resp.setStatus(500);

            mapper.writeValue(

                    resp.getOutputStream(),

                    new ApiResponse(

                            false,

                            e.getMessage(),

                            null));

        }

    }

    private void downloadAllPdf(

            HttpServletResponse resp)

            throws Exception {

        String pdfPath =
                reportService
                .generateAllInvoicesPdf();

        sendPdf(

                pdfPath,

                "AllInvoices.pdf",

                resp);

    }

    private void downloadDatePdf(

            HttpServletRequest req,

            HttpServletResponse resp)

            throws Exception {

        LocalDate start =
                LocalDate.parse(
                        req.getParameter("start"));

        LocalDate end =
                LocalDate.parse(
                        req.getParameter("end"));

        String pdfPath =

                reportService

                .generateInvoicesPdf(

                        start,

                        end);

        sendPdf(

                pdfPath,

                "Invoices_" + start + "_" + end + ".pdf",

                resp);

    }

    private void sendPdf(

            String pdfPath,

            String fileName,

            HttpServletResponse resp)

            throws Exception {

        File file =
                new File(pdfPath);

        resp.setContentType("application/pdf");

        resp.setContentLengthLong(
                file.length());

        resp.setHeader(

                "Content-Disposition",

                "attachment; filename=\"" + fileName + "\"");

        Files.copy(

                file.toPath(),

                resp.getOutputStream());

        resp.getOutputStream().flush();

    }
    
    private void downloadExcel(
            HttpServletResponse resp)
            throws Exception {

        String excelPath =
                reportService
                .generateExcelReport();

        File file =
                new File(excelPath);

        resp.setContentType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        resp.setContentLengthLong(
                file.length());

        resp.setHeader(

                "Content-Disposition",

                "attachment; filename=\"Invoice_Report.xlsx\"");

        Files.copy(

                file.toPath(),

                resp.getOutputStream());

        resp.getOutputStream().flush();

    }

}