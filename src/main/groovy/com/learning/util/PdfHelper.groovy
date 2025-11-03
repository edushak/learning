package com.learning.util

import org.apache.pdfbox.Loader
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

class PdfHelper {

    static String read(File pdfFile) {
        try (PDDocument document = Loader.loadPDF(pdfFile)) {
            PDFTextStripper pdfStripper = new PDFTextStripper()
            return pdfStripper.getText(document)
        }
    }
}

