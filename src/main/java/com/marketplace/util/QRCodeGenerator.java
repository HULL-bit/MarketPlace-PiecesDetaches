package com.marketplace.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class QRCodeGenerator {

    private static final Logger log = LoggerFactory.getLogger(QRCodeGenerator.class);
    
    @Value("${upload.dir:/opt/marketplace/uploads/}")
    private String uploadDir;
    
    @Value("${gqrcode.size:300}")
    private int qrCodeSize;
    
    public String generateQRCode(String content, int size) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            
            BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, size, size, hints);
            
            String fileName = UUID.randomUUID().toString() + ".png";
            Path qrDir = Paths.get(uploadDir, "qrcodes");
            
            if (!Files.exists(qrDir)) {
                Files.createDirectories(qrDir);
            }
            
            Path filePath = qrDir.resolve(fileName);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", filePath);
            
            return "/uploads/qrcodes/" + fileName;
        } catch (Exception e) {
            log.error("Erreur generation QR Code", e);
            return null;
        }
    }
    
    public String generateQRCode(String content) {
        return generateQRCode(content, qrCodeSize);
    }
}
