package report;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.Bill;

public class ExcelReportGenerator {

    private static final String REPORT_FOLDER =
            System.getProperty("user.home")
            + File.separator
            + "RKPlasticInvoices";

    public static String generate(
            List<Bill> bills)
            throws Exception {

        File folder =
                new File(REPORT_FOLDER);

        if(!folder.exists())
            folder.mkdirs();

        String fileName =
                REPORT_FOLDER
                + File.separator
                + "Invoice_Report.xlsx";

        Workbook workbook =
                new XSSFWorkbook();

        Sheet sheet =
                workbook.createSheet(
                        "Invoices");

        Font headerFont =
                workbook.createFont();

        headerFont.setBold(true);

        headerFont.setFontHeightInPoints(
                (short)12);

        CellStyle headerStyle =
                workbook.createCellStyle();

        headerStyle.setFont(
                headerFont);

        Row header =
                sheet.createRow(0);

        String[] columns = {

                "Bill No",

                "Date",

                "Customer",

                "GSTIN",

                "Subtotal",

                "CGST",

                "SGST",

                "IGST",

                "Grand Total"

        };

        for(int i=0;i<columns.length;i++){

            Cell cell =
                    header.createCell(i);

            cell.setCellValue(
                    columns[i]);

            cell.setCellStyle(
                    headerStyle);

        }

        int rowNum = 1;

        for(Bill bill : bills){

            Row row =
                    sheet.createRow(
                            rowNum++);

            row.createCell(0)
                    .setCellValue(
                            bill.getBillNo());

            row.createCell(1)
                    .setCellValue(
                            bill.getBillDate().toString());

            row.createCell(2)
                    .setCellValue(
                            bill.getCustomer().getName());

            row.createCell(3)
                    .setCellValue(
                            bill.getCustomer().getGstin());

            row.createCell(4)
                    .setCellValue(
                            bill.getSubtotal().doubleValue());

            row.createCell(5)
                    .setCellValue(
                            bill.getCgst().doubleValue());

            row.createCell(6)
                    .setCellValue(
                            bill.getSgst().doubleValue());

            row.createCell(7)
                    .setCellValue(
                            bill.getIgst().doubleValue());

            row.createCell(8)
                    .setCellValue(
                            bill.getGrandTotal().doubleValue());

        }

        for(int i=0;i<columns.length;i++){

            sheet.autoSizeColumn(i);

        }

        FileOutputStream fos =
                new FileOutputStream(
                        fileName);

        workbook.write(fos);

        fos.close();

        workbook.close();

        return fileName;

    }

}