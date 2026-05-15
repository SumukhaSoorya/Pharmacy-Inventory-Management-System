package com.pims.pims.service;

import com.pims.pims.model.PurchaseOrder;
import com.pims.pims.model.Stock;
import com.pims.pims.model.Supplier;
import com.pims.pims.repository.PurchaseOrderRepository;
import com.pims.pims.repository.SupplierRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final SupplierRepository supplierRepository;

    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository,
                                SupplierRepository supplierRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.supplierRepository = supplierRepository;
    }

    public List<PurchaseOrder> getAll() {
        return purchaseOrderRepository.findAll();
    }

    public PurchaseOrder save(PurchaseOrder purchaseOrder) {
        return purchaseOrderRepository.save(purchaseOrder);
    }

    public Long countByStatus(String status) {
        Long count = purchaseOrderRepository.countByStatus(status);
        return count == null ? 0 : count;
    }

    public void createOrder(Long supplierId,
                            String medicineName,
                            int quantity,
                            double totalAmount) {

        Supplier supplier = supplierRepository.findById(supplierId).orElse(null);

        PurchaseOrder order = new PurchaseOrder();
        order.setSupplier(supplier);
        order.setMedicineName(medicineName);
        order.setQuantity(quantity);
        order.setTotalAmount(totalAmount);
        order.setStatus("Pending");

        purchaseOrderRepository.save(order);
    }

    public int autoCreateReorderDrafts(List<Stock> lowStockList) {

        List<Supplier> suppliers = supplierRepository.findAll();

        if (suppliers.isEmpty()) {
            return 0;
        }

        Supplier defaultSupplier = suppliers.get(0);

        int created = 0;

        for (Stock stock : lowStockList) {

            if (stock.getMedicine() == null) {
                continue;
            }

            String medicineName = stock.getMedicine().getName();

            boolean alreadyExists =
                    purchaseOrderRepository.existsByMedicineNameAndStatus(
                            medicineName,
                            "Pending"
                    );

            if (alreadyExists) {
                continue;
            }

            int reorderQty = Math.max(stock.getReorderLevel() * 2, 10);

            PurchaseOrder order = new PurchaseOrder();

            order.setSupplier(defaultSupplier);
            order.setMedicineName(medicineName);
            order.setQuantity(reorderQty);
            order.setTotalAmount(reorderQty * stock.getPurchasePrice());
            order.setStatus("Pending");
            order.setFactoryRemark("Auto-created from low stock alert");

            purchaseOrderRepository.save(order);

            created++;
        }

        return created;
    }

    public void updateStatusWithRemark(Long id,
                                       String status,
                                       String remark) {

        PurchaseOrder order =
                purchaseOrderRepository.findById(id).orElse(null);

        if (order == null) {
            throw new RuntimeException("Purchase order not found with ID: " + id);
        }

        order.setStatus(status);
        order.setFactoryRemark(remark);

        purchaseOrderRepository.save(order);
    }
}