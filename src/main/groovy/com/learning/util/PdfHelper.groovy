package com.learning.util

import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

class PdfHelper {

    static String read(File pdfFile) {
        PDDocument document = PDDocument.load(pdfFile);
        try {
            if (!document.isEncrypted()) {
                PDFTextStripper stripper = new PDFTextStripper();
                String text = stripper.getText(document);
                // System.out.println("Text:" + text);
                return text
            } else {
                System.out.println("PDF document $pdfFile is encrypted. Cannot read!");
            }
        } finally {
            // document?.close();
        }
        return null
    }
}

