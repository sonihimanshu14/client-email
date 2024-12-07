package com.example.springemailexample.services;


import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@RequestMapping("/ocr")
public class OcrService {



        @PostMapping("/recognize")
        public String recognizeLicensePlate(@RequestParam String imagePath) {
            Tesseract tesseract = new Tesseract();
            tesseract.setDatapath("path/to/tessdata"); // Your Tesseract data path

            try {
                String result = tesseract.doOCR(new File(imagePath));
                return result;
            } catch (TesseractException e) {
                e.printStackTrace();
                return "Error recognizing text.";
            }
        }
    }


