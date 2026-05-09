package com.pims.pims.service;

import com.pims.pims.model.*;
import com.pims.pims.repository.BillRepository;
import org.springframework.stereotype.Service;

@Service
public class BillService {

    private final BillRepository repo;
    private final StockService stockService;

    public BillService(BillRepository repo, StockService stockService) {
        this.repo = repo;
        this.stockService = stockService;
    }

    public Bill save(Bill bill) {

        double total = 0;
        double gst = 0;

        for (BillItem item : bill.getItems()) {

            double itemTotal = item.getQuantity() * item.getUnitPrice();

            double itemGST = itemTotal * 0.12;

            item.setGst(itemGST);
            item.setTotalPrice(itemTotal + itemGST);

            total += itemTotal;
            gst += itemGST;

            // REDUCE STOCK
            stockService.reduceStock(item.getMedicine(), item.getQuantity());
        }

        bill.setTotalAmount(total);
        bill.setGstAmount(gst);

        // LOYALTY
        bill.setLoyaltyPointsEarned((int) total / 100);

        return repo.save(bill);
    }
}