package com.pims.pims.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pims.pims.model.PurchaseOrder;
import com.pims.pims.repository.PurchaseOrderRepository;

@Service
public class PurchaseOrderService {

    private final PurchaseOrderRepository repo;

    public PurchaseOrderService(PurchaseOrderRepository repo) {
        this.repo = repo;
    }

    public PurchaseOrder save(PurchaseOrder po) {

        if (po.getStatus() == null || po.getStatus().isEmpty()) {
            po.setStatus("Pending");
        }

        return repo.save(po);
    }

    public List<PurchaseOrder> getAll() {
        return repo.findAll();
    }

    public PurchaseOrder getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void updateStatus(Long id, String status) {

        PurchaseOrder order = repo.findById(id).orElse(null);

        if (order != null) {
            order.setStatus(status);
            repo.save(order);
        }
    }

    public void updateStatusWithRemark(Long id, String status, String remark) {

        PurchaseOrder order = repo.findById(id).orElse(null);

        if (order != null) {
            order.setStatus(status);
            order.setFactoryRemark(remark);
            repo.save(order);
        }
    }
}