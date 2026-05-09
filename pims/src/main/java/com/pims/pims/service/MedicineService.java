package com.pims.pims.service;

import com.pims.pims.model.Medicine;
import com.pims.pims.repository.MedicineRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineService {

    private final MedicineRepository repo;

    public MedicineService(MedicineRepository repo) {
        this.repo = repo;
    }

    public Medicine save(Medicine medicine) {
        return repo.save(medicine);
    }

    public List<Medicine> getAll() {
        return repo.findAll();
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public List<Medicine> search(String name) {
        return repo.findByNameContainingIgnoreCase(name);
    }
}