package com.pims.pims.service;

import com.pims.pims.model.PurchaseOrder;
import com.pims.pims.repository.PurchaseOrderRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;

    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    public List<PurchaseOrder> getAll() {
        return purchaseOrderRepository.findAll();
    }

    public PurchaseOrder save(PurchaseOrder purchaseOrder) {
        return purchaseOrderRepository.save(purchaseOrder);
    }

    public void updateStatus(Long id, String status) {

        PurchaseOrder order = purchaseOrderRepository.findById(id)
                .orElse(null);

        if (order != null) {
            order.setStatus(status);
            purchaseOrderRepository.save(order);
        }
    }

    public long countByStatus(String status) {
        return purchaseOrderRepository.countByStatus(status);
    }

    public void deleteById(Long id) {
        purchaseOrderRepository.deleteById(id);
    }
}