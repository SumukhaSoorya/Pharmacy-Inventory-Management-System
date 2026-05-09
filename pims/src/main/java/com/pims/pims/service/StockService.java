package com.pims.pims.service;

import com.pims.pims.model.Medicine;
import com.pims.pims.model.Stock;
import com.pims.pims.repository.StockRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StockService {

    private final StockRepository repo;

    // CONSTRUCTOR
    public StockService(StockRepository repo) {
        this.repo = repo;
    }

    // GET ALL STOCK
    public List<Stock> getAll() {
        return repo.findAll();
    }

    // SAVE STOCK
    public Stock save(Stock stock) {
        return repo.save(stock);
    }

    // EXPIRING SOON
    public List<Stock> getExpiringSoon() {
        return repo.findByExpiryDateBefore(
                LocalDate.now().plusDays(30)
        );
    }

    // LOW STOCK
    public List<Stock> getLowStock() {

        return repo.findAll()
                .stream()
                .filter(s -> s.getQuantity() <= s.getReorderLevel())
                .toList();
    }
    public boolean shouldReorder(Stock stock){

    return stock.getQuantity()
            <= stock.getReorderLevel();
}

    // REDUCE STOCK AFTER BILL
    public void reduceStock(Medicine medicine, int qty) {

        List<Stock> stocks = repo.findAll();

        for (Stock s : stocks) {

            if (s.getMedicine().getId()
                    .equals(medicine.getId())) {

                s.setQuantity(
                        s.getQuantity() - qty
                );

                repo.save(s);

                break;
            }
        }
    }
}