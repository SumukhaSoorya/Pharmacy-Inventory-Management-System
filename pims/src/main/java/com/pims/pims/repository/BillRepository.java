package com.pims.pims.repository;

import com.pims.pims.model.Bill;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

    @Query("SELECT COALESCE(SUM(b.totalAmount), 0) FROM Bill b")
    Double getTotalSales();

    @Query("SELECT COALESCE(SUM(b.gstAmount), 0) FROM Bill b")
    Double getTotalGST();

    @Query("SELECT COUNT(b) FROM Bill b")
    Long getTotalBills();

    @Query("SELECT COUNT(b) FROM Bill b WHERE b.paymentMode = 'Cash'")
    Long getCashBills();

    @Query("SELECT COUNT(b) FROM Bill b WHERE b.paymentMode = 'Card'")
    Long getCardBills();

    @Query("SELECT COUNT(b) FROM Bill b WHERE b.paymentMode = 'UPI'")
    Long getUpiBills();
}