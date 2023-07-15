package com.example.quickprep.readers;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Slf4j
public class Reader {
    public static String readPdf(String path) {
        try {
            File file = new File(path);
            PDDocument pdfDocument = PDDocument.load(file);
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            String pdfText = pdfTextStripper.getText(pdfDocument);
            pdfDocument.close();
            return pdfText;
        } catch (IOException e) {
            log.error("Error while reading PDF: ", e);
            return null;
        }
    }

    public static String readTxt(String path) {
        try {
            FileReader fileReader = new FileReader(path);
            StringBuilder sb = new StringBuilder();
            int i;
            while ((i = fileReader.read()) != -1) {
                sb.append((char) i);
            }
            return sb.toString();
        } catch (IOException e) {
            log.error("Error while reading txt file: ", e);
            return null;
        }
    }
}
