package com.pims.pims.service;

import com.pims.pims.model.Medicine;
import com.pims.pims.model.Stock;
import com.pims.pims.repository.MedicineRepository;
import com.pims.pims.repository.StockRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private final MedicineRepository medicineRepository;

    public StockService(StockRepository stockRepository,
                        MedicineRepository medicineRepository) {
        this.stockRepository = stockRepository;
        this.medicineRepository = medicineRepository;
    }

    public List<Stock> getAll() {
        return stockRepository.findAll();
    }

    public Stock saveStock(Long medicineId,
                           String batchNo,
                           int quantity,
                           int reorderLevel,
                           double purchasePrice,
                           double sellingPrice,
                           LocalDate expiryDate) {

        Medicine medicine = medicineRepository.findById(medicineId).orElse(null);

        if (medicine == null) {
            throw new RuntimeException("Medicine not found with ID: " + medicineId);
        }

        Stock stock = new Stock();

        stock.setMedicine(medicine);
        stock.setBatchNo(batchNo);
        stock.setQuantity(quantity);
        stock.setReorderLevel(reorderLevel);
        stock.setPurchasePrice(purchasePrice);
        stock.setSellingPrice(sellingPrice);
        stock.setExpiryDate(expiryDate);

        return stockRepository.save(stock);
    }

    public Stock save(Stock stock) {
        return stockRepository.save(stock);
    }

    public Long getAvailableStock() {
        Long value = stockRepository.getAvailableStock();
        return value == null ? 0 : value;
    }

    public Long getExpiredCount() {
        Long value = stockRepository.getExpiredCount(LocalDate.now());
        return value == null ? 0 : value;
    }

    public Long getExpiringSoonCount() {
        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusDays(30);

        Long value = stockRepository.getExpiringSoonCount(today, endDate);
        return value == null ? 0 : value;
    }

    public List<Stock> getExpiredStock() {
        return stockRepository.findExpiredStock(LocalDate.now());
    }

    public List<Stock> getExpiringSoon() {
        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusDays(30);

        return stockRepository.findExpiringSoon(today, endDate);
    }

    public List<Stock> getLowStock() {
        return stockRepository.findLowStock();
    }

    public Long getLowStockCount() {
        Long value = stockRepository.getLowStockCount();
        return value == null ? 0 : value;
    }
}