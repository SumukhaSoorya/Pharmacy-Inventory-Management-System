package com.pims.pims.service;

import com.pims.pims.model.Supplier;
import com.pims.pims.repository.SupplierRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public Supplier save(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public List<Supplier> getAll() {
        return supplierRepository.findAll();
    }

    public Supplier getById(Long id) {
        return supplierRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        supplierRepository.deleteById(id);
    }
}