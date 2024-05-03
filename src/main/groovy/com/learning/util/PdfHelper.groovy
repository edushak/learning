package com.learning.util

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

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
        } catch(Exception ex) {
            System.err.println("Something bad happened!");
            System.err.println(ex.toString());
        } finally {
            // document?.close();
        }
        return null
    }
}

