package com.pims.pims.service;

import com.pims.pims.model.Supplier;
import com.pims.pims.repository.SupplierRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {

    private final SupplierRepository repo;

    public SupplierService(SupplierRepository repo) {
        this.repo = repo;
    }

    // SAVE
    public Supplier save(Supplier supplier) {
        return repo.save(supplier);
    }

    // GET ALL
    public List<Supplier> getAll() {
        return repo.findAll();
    }
}