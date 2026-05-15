package com.pims.pims.repository;

import com.pims.pims.model.Stock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> {

    @Query("SELECT COALESCE(SUM(s.quantity), 0) FROM Stock s")
    Long getAvailableStock();

    @Query("SELECT COUNT(s) FROM Stock s WHERE s.expiryDate < :today")
    Long getExpiredCount(@Param("today") LocalDate today);

    @Query("SELECT COUNT(s) FROM Stock s WHERE s.expiryDate >= :today AND s.expiryDate <= :endDate")
    Long getExpiringSoonCount(@Param("today") LocalDate today,
                              @Param("endDate") LocalDate endDate);

    @Query("SELECT s FROM Stock s WHERE s.expiryDate < :today")
    List<Stock> findExpiredStock(@Param("today") LocalDate today);

    @Query("SELECT s FROM Stock s WHERE s.expiryDate >= :today AND s.expiryDate <= :endDate")
    List<Stock> findExpiringSoon(@Param("today") LocalDate today,
                                 @Param("endDate") LocalDate endDate);

    @Query("SELECT s FROM Stock s WHERE s.quantity <= s.reorderLevel")
    List<Stock> findLowStock();

    @Query("SELECT COUNT(s) FROM Stock s WHERE s.quantity <= s.reorderLevel")
    Long getLowStockCount();
}