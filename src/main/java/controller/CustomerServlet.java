package controller;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import dto.CustomerRequest;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Customer;
import service.CustomerService;
import service.CustomerServiceImpl;
import util.ApiResponse;
import util.JsonUtil;

@WebServlet("/api/customers/*")
public class CustomerServlet extends HttpServlet {

    private final CustomerService customerService =
            new CustomerServiceImpl();

    private final ObjectMapper mapper =
            JsonUtil.getMapper();

    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException,
            IOException {

        try {

            CustomerRequest request =
                    mapper.readValue(
                            req.getInputStream(),
                            CustomerRequest.class);

            Customer customer =
                    customerService
                    .createCustomer(request);

            resp.setContentType(
                    "application/json");

            mapper.writeValue(
                    resp.getOutputStream(),
                    new ApiResponse(
                            true,
                            "Customer created",
                            customer));

        }
        catch(Exception e) {

            resp.setStatus(400);

            mapper.writeValue(
                    resp.getOutputStream(),
                    new ApiResponse(
                            false,
                            e.getMessage(),
                            null));
        }
    }

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException,
            IOException {

        String path =
                req.getPathInfo();

        try {
        	
        	if("/all".equals(path)) {

        	    List<Customer> customers =
        	            customerService
        	            .getAllCustomers();
        	    
        	    resp.setContentType("application/json");

        	    mapper.writeValue(
        	            resp.getOutputStream(),
        	            customers);

        	    return;
        	}

            if(path.startsWith(
                    "/search")) {

                String keyword =
                        req.getParameter(
                                "keyword");

                List<Customer> customers =
                        customerService
                        .searchCustomers(
                                keyword);

                mapper.writeValue(
                        resp.getOutputStream(),
                        customers);

                return;
            }

            Integer id =
                    Integer.parseInt(
                            path.substring(1));

            Customer customer =
                    customerService
                    .getCustomer(id);

            mapper.writeValue(
                    resp.getOutputStream(),
                    customer);

        }
        catch(Exception e) {

            resp.setStatus(400);

            mapper.writeValue(
                    resp.getOutputStream(),
                    new ApiResponse(
                            false,
                            e.getMessage(),
                            null));
        }
    }
    
    @Override
    protected void doPut(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws IOException {

        try {

            Integer id =
                    Integer.parseInt(
                            req.getPathInfo()
                            .substring(1));

            CustomerRequest request =
                    mapper.readValue(
                            req.getInputStream(),
                            CustomerRequest.class);

            Customer customer =
                    customerService
                    .updateCustomer(
                            id,
                            request);

            mapper.writeValue(
                    resp.getOutputStream(),
                    customer);

        }
        catch(Exception e) {

            resp.setStatus(400);

            mapper.writeValue(
                    resp.getOutputStream(),
                    new ApiResponse(
                            false,
                            e.getMessage(),
                            null));
        }
    }
    
    @Override
    protected void doDelete(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws IOException {

        try {

            Integer id =
                    Integer.parseInt(
                            req.getPathInfo()
                            .substring(1));

            customerService
                    .deleteCustomer(id);

            mapper.writeValue(
                    resp.getOutputStream(),
                    new ApiResponse(
                            true,
                            "Customer Deleted",
                            null));

        }
        catch(Exception e) {

            resp.setStatus(400);

            mapper.writeValue(
                    resp.getOutputStream(),
                    new ApiResponse(
                            false,
                            e.getMessage(),
                            null));
        }
    }
}