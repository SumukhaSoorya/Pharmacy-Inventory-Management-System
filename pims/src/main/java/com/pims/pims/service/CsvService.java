package com.pims.pims.service;

import com.opencsv.CSVWriter;
import com.pims.pims.model.Medicine;
import com.pims.pims.repository.MedicineRepository;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@Service
public class CsvService {

    private final MedicineRepository medicineRepository;

    public CsvService(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    public void exportMedicines(
            HttpServletResponse response
    ) throws IOException {

        response.setContentType("text/csv");

        response.setHeader(
                "Content-Disposition",
                "attachment; filename=medicines.csv"
        );

        CSVWriter writer =
                new CSVWriter(
                        response.getWriter()
                );

        // HEADER
        String[] header = {
                "ID",
                "Name",
                "Category",
                "Manufacturer",
                "Barcode"
        };

        writer.writeNext(header);

        // DATA
        List<Medicine> medicines =
                medicineRepository.findAll();

        for(Medicine m : medicines){

            String[] data = {

                    String.valueOf(m.getId()),

                    m.getName(),

                    m.getCategory(),

                    m.getManufacturer(),

                    m.getBarcode()
            };

            writer.writeNext(data);
        }

        writer.close();
    }
}