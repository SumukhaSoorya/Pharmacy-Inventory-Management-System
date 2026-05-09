package com.pims.pims.repository;

import com.pims.pims.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> {

    List<Stock> findByExpiryDateBefore(LocalDate date);

    List<Stock> findByQuantityLessThanEqual(int level);
}