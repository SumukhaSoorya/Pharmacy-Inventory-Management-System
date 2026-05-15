package com.pims.pims.repository;

import com.pims.pims.model.PurchaseOrder;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {

    Long countByStatus(String status);

    List<PurchaseOrder> findByStatus(String status);

    boolean existsByMedicineNameAndStatus(String medicineName, String status);
}