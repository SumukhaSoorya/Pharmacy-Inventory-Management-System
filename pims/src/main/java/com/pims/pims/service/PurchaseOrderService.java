package com.pims.pims.service;

import com.pims.pims.model.PurchaseOrder;
import com.pims.pims.repository.PurchaseOrderRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseOrderService {

    private final PurchaseOrderRepository repo;

    public PurchaseOrderService(
            PurchaseOrderRepository repo
    ) {
        this.repo = repo;
    }

    // SAVE
    public PurchaseOrder save(
            PurchaseOrder po
    ) {

        return repo.save(po);
    }

    // GET ALL
    public List<PurchaseOrder> getAll() {

        return repo.findAll();
    }
    public void updateStatus(Long id, String status) {

    PurchaseOrder order =
            repo.findById(id).orElse(null);

    if (order != null) {

        order.setStatus(status);

        repo.save(order);
    }
}
}