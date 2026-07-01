package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import dto.CreateInvoiceRequest;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Bill;
import service.BillService;
import service.BillServiceImpl;
import service.PDFService;
import service.PDFServiceImpl;
import util.ApiResponse;
import util.JsonUtil;

@WebServlet("/api/bills/*")
public class BillServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final BillService billService = new BillServiceImpl();

	private final ObjectMapper mapper = JsonUtil.getMapper();
	
	private final PDFService pdfService = new PDFServiceImpl();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("application/json");

		try {

			CreateInvoiceRequest request = mapper.readValue(req.getInputStream(), CreateInvoiceRequest.class);

			Bill bill = billService.createInvoice(request);

			mapper.writeValue(resp.getOutputStream(), new ApiResponse(true, "Invoice Created Successfully", bill));

		} catch (Exception e) {

			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

			mapper.writeValue(resp.getOutputStream(), new ApiResponse(false, e.getMessage(), null));
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String path = req.getPathInfo();

		System.out.println("BillServlet HIT");
		System.out.println("Path = " + req.getPathInfo());

		resp.setContentType("application/json");

		try {

			if (path == null || "/".equals(path)) {

				mapper.writeValue(resp.getOutputStream(), billService.getAllBills());

				return;
			}

			// GET /api/bills/next-bill-no
			if ("/next-bill-no".equals(path)) {

				Map<String, Integer> response = new HashMap<>();

				response.put("billNo", billService.getNextBillNumber());

				mapper.writeValue(resp.getOutputStream(), response);

				return;
			}

			// GET /api/bills/all
			if ("/all".equals(path)) {

				mapper.writeValue(resp.getOutputStream(), billService.getAllBills());

				return;
			}

			// GET /api/bills/bill-no/101
			if (path.startsWith("/bill-no/")) {

				Integer billNo = Integer.parseInt(path.replace("/bill-no/", ""));

				mapper.writeValue(resp.getOutputStream(), billService.getBillByNumber(billNo));

				return;
			}

			// GET /api/bills/5/pdf
			if (path.endsWith("/pdf")) {

				Integer billId = Integer.parseInt(path.replace("/pdf", "").replace("/", ""));

				Bill bill = billService.getBill(billId);

				if (bill == null) {

					resp.setStatus(HttpServletResponse.SC_NOT_FOUND);

					return;
				}

				File file;

				// If pdfPath exists, use it
				if (bill.getPdfPath() != null && !bill.getPdfPath().isBlank() && new File(bill.getPdfPath()).exists()) {

					file = new File(bill.getPdfPath());

				} else {

					// Otherwise regenerate the PDF
					file = pdfService.generateInvoicePdf(bill);


				}

				resp.reset();

				resp.setContentType("application/pdf");

				resp.setContentLengthLong(file.length());

				resp.setHeader("Content-Disposition", "attachment; filename=Invoice_" + bill.getBillNo() + ".pdf");

				Files.copy(file.toPath(), resp.getOutputStream());

				resp.getOutputStream().flush();

				return;
			}

			// GET /api/bills/5

			Integer id = Integer.parseInt(path.substring(1));

			Bill bill = billService.getBill(id);

			if (bill == null) {

				resp.setStatus(HttpServletResponse.SC_NOT_FOUND);

				mapper.writeValue(resp.getOutputStream(), new ApiResponse(false, "Bill Not Found", null));

				return;
			}

			mapper.writeValue(resp.getOutputStream(), bill);

		} catch (Exception e) {

			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

			mapper.writeValue(resp.getOutputStream(), new ApiResponse(false, e.getMessage(), null));
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		resp.setContentType("application/json");

		try {

			Integer id = Integer.parseInt(req.getPathInfo().substring(1));

			CreateInvoiceRequest request = mapper.readValue(req.getInputStream(), CreateInvoiceRequest.class);

			Bill updatedBill = billService.updateInvoice(id, request);

			mapper.writeValue(resp.getOutputStream(),
					new ApiResponse(true, "Invoice Updated Successfully", updatedBill));

		} catch (Exception e) {

			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

			mapper.writeValue(resp.getOutputStream(), new ApiResponse(false, e.getMessage(), null));
		}
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		resp.setContentType("application/json");

		try {

			Integer id = Integer.parseInt(req.getPathInfo().substring(1));

			billService.deleteBill(id);

			mapper.writeValue(resp.getOutputStream(), new ApiResponse(true, "Invoice Deleted Successfully", null));

		} catch (Exception e) {

			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

			mapper.writeValue(resp.getOutputStream(), new ApiResponse(false, e.getMessage(), null));
		}
	}
}