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

        for (BillItemRequest itemRequest : request.getItems()) {

            BillItem item = new BillItem();

            double itemSubtotal =
                    itemRequest.getUnitPrice()
                            * itemRequest.getQuantity();

            double gst =
                    itemSubtotal * 0.12;

            double itemTotal =
                    itemSubtotal + gst;

            item.setMedicineName(
                    itemRequest.getMedicineName()
            );

            item.setQuantity(
                    itemRequest.getQuantity()
            );

            item.setUnitPrice(
                    itemRequest.getUnitPrice()
            );

            item.setGst(gst);

            item.setTotalPrice(itemTotal);

            item.setBill(bill);

            bill.getItems().add(item);

            total += itemTotal;
            gstTotal += gst;
        }

        total =
                total - request.getDiscount();

        bill.setTotalAmount(total);

        bill.setGstAmount(gstTotal);

        bill.setLoyaltyPointsEarned(
                (int) (total / 100)
        );

        return billRepository.save(bill);
    }

    public Bill getBill(Long id) {
        return billRepository.findById(id).orElse(null);
    }
}