package com.pims.pims.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import org.springframework.stereotype.Service;

import java.nio.file.FileSystems;
import java.nio.file.Path;

@Service
public class QrService {

    public void generateQR(String text) {

        try {

            QRCodeWriter writer =
                    new QRCodeWriter();

            var matrix = writer.encode(
                    text,
                    BarcodeFormat.QR_CODE,
                    250,
                    250
            );

            Path path =
                    FileSystems.getDefault()
                    .getPath("invoiceQR.png");

            MatrixToImageWriter.writeToPath(
                    matrix,
                    "PNG",
                    path
            );

            System.out.println("QR Generated!");

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
