package com.example.springemailexample.services;


import gnu.cajo.utils.Multicast;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

@RestController
@RequestMapping("/ocr")
public class OcrService {

    private static final Logger logger = Logger.getLogger(OcrService.class.getName());


    @PostMapping("/recognize")
        public String recognizeLicensePlate(@RequestParam String imagePath) {
            Tesseract tesseract = new Tesseract();
            tesseract.setDatapath("D:\\myProj\\tessdata"); // Your Tesseract data path

            try {
                String result = tesseract.doOCR(new File(imagePath));
                return result;
            } catch (TesseractException e) {
                e.printStackTrace();
                return "Error recognizing text.";
            }
        }


    public String extractTextFromImage(MultipartFile imageFile) throws TesseractException, IOException {
        // Extract tessdata folder from resources

        File tempFile = File.createTempFile("upload-", imageFile.getOriginalFilename());
        imageFile.transferTo(tempFile);


        File tessDataDir = extractTessData();

        if (tessDataDir == null) {
            throw new RuntimeException("Failed to extract tessdata directory!");
        }

        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath(tessDataDir.getAbsolutePath());  // Set datapath dynamically
        tesseract.setLanguage("eng");

        // Perform OCR on the image file
        return tesseract.doOCR(tempFile);

    }

    private File extractTessData() throws IOException {
        // Access tessdata directory from classpath
        ClassPathResource tessdataResource = new ClassPathResource("tessdata/eng.traineddata");

        if (!tessdataResource.exists()) {
            logger.info("eng.traineddata file not found in classpath.");
            throw new RuntimeException("eng.traineddata file is missing in resources!");
        }

        // Create a temporary directory to copy tessdata files
        Path tempDir = Files.createTempDirectory("tessdata1");
        File tessDataDir = tempDir.toFile();

        logger.info("Temporary tessdata directory created at: " + tessDataDir.getAbsolutePath());

        // Copy .traineddata files to the temporary directory
        try (InputStream inputStream = tessdataResource.getInputStream()) {
            Files.copy(inputStream, tempDir.resolve("eng.traineddata"));
            logger.info("Copied eng.traineddata to: " + tempDir.resolve("eng.traineddata"));
        } catch (IOException e) {
            logger.severe("Error copying eng.traineddata: " + e.getMessage());
            throw new RuntimeException("Error copying eng.traineddata to temporary directory", e);
        }

        // Return the extracted directory
        return tessDataDir;
    }





    }


