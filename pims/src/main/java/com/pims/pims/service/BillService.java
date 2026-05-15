package com.pims.pims.service;

import org.springframework.stereotype.Service;

import com.pims.pims.dto.BillItemRequest;
import com.pims.pims.dto.BillRequest;
import com.pims.pims.model.Bill;
import com.pims.pims.model.BillItem;
import com.pims.pims.repository.BillRepository;

@Service
public class BillService {

    private final BillRepository billRepository;

    public BillService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    public Bill createBill(BillRequest request) {

        Bill bill = new Bill();

        bill.setCustomerName(request.getCustomerName());
        bill.setPaymentMode(request.getPaymentMode());
        bill.setDiscount(request.getDiscount());

        double total = 0;
        double gstTotal = 0;

        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new RuntimeException("Bill items are empty");
        }

        for (BillItemRequest itemRequest : request.getItems()) {

            BillItem item = new BillItem();

            double baseAmount = itemRequest.getUnitPrice() * itemRequest.getQuantity();
            double gst = baseAmount * 0.12;
            double itemTotal = baseAmount + gst;

            item.setMedicineName(itemRequest.getMedicineName());
            item.setQuantity(itemRequest.getQuantity());
            item.setUnitPrice(itemRequest.getUnitPrice());
            item.setGst(gst);
            item.setTotalPrice(itemTotal);
            item.setBill(bill);

            bill.getItems().add(item);

            total += itemTotal;
            gstTotal += gst;
        }

        total = total - request.getDiscount();

        if (total < 0) {
            total = 0;
        }

        bill.setTotalAmount(total);
        bill.setGstAmount(gstTotal);

        return billRepository.save(bill);
    }

    public Bill getBill(Long id) {
        return billRepository.findById(id).orElse(null);
    }

    public Double getTotalSales() {
        Double total = billRepository.getTotalSales();
        return total == null ? 0.0 : total;
    }

    public Long getTotalBills() {
        Long count = billRepository.getTotalBills();
        return count == null ? 0 : count;
    }
    public Double getTotalGST() {
    Double gst = billRepository.getTotalGST();
    return gst == null ? 0.0 : gst;
}
}