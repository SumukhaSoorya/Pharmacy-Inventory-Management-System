package com.pims.pims.repository;

import com.pims.pims.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BillRepository extends JpaRepository<Bill, Long> {

    @Query("SELECT SUM(b.totalAmount) FROM Bill b")
    Double getTotalSales();

    @Query("SELECT SUM(b.gstAmount) FROM Bill b")
    Double getTotalGST();

    @Query("SELECT COUNT(b) FROM Bill b")
    Long getTotalBills();
}