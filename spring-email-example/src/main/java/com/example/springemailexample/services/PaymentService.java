package com.example.springemailexample.services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.WriterException;
import org.springframework.web.bind.annotation.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
@RestController
@RequestMapping("/payment")
public class PaymentService {

        @PostMapping("/generateQRCode")
        public String generateQRCode(@RequestParam String paymentId) throws WriterException, IOException {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BufferedImage qrImage = qrCodeWriter.encode(paymentId, BarcodeFormat.QR_CODE, 200, 200);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(qrImage, "PNG", baos);
            byte[] qrCodeBytes = baos.toByteArray();
            // Return QR code as Base64 encoded string
            return new String(qrCodeBytes);
        }
    }

}
