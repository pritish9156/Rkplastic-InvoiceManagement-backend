package util;

import java.util.stream.Collectors;

import dto.*;
import model.*;

public class InvoiceMapper {

    public static InvoiceResponse toResponse(
            Bill bill) {

        InvoiceResponse response =
                new InvoiceResponse();

        response.setId(
                bill.getId());

        response.setBillNo(
                bill.getBillNo());

        response.setBillDate(
                bill.getBillDate());

        response.setSubtotal(
                bill.getSubtotal());

        response.setCgst(
                bill.getCgst());

        response.setSgst(
                bill.getSgst());

        response.setIgst(
                bill.getIgst());

        response.setGrandTotal(
                bill.getGrandTotal());

        response.setAmountInWords(
                bill.getAmountInWords());

        Customer customer =
                bill.getCustomer();

        CustomerResponse customerResponse =
                new CustomerResponse();

        customerResponse.setId(
                customer.getId());

        customerResponse.setName(
                customer.getName());

        customerResponse.setPhone(
                customer.getPhone());

        customerResponse.setAddress(
                customer.getAddress());

        customerResponse.setGstin(
                customer.getGstin());

        response.setCustomer(
                customerResponse);

        response.setItems(
                bill.getItems()
                .stream()
                .map(item -> {

                    InvoiceItemResponse r =
                            new InvoiceItemResponse();

                    r.setDescription(
                            item.getDescription());

                    r.setHsnSac(
                            item.getHsnSac());

                    r.setQty(
                            item.getQty());

                    r.setRate(
                            item.getRate());

                    r.setAmount(
                            item.getAmount());

                    return r;

                })
                .collect(
                        Collectors.toList()));

        return response;
    }
}