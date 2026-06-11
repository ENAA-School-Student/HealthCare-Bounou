package com.HealthCare.HealthCare.utils;

import com.HealthCare.HealthCare.dto.DossierMedicalDto;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class PdfGeneratorUtil {

    public static ByteArrayInputStream generateDossierPdf(DossierMedicalDto dto, String reportTitle) {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22, Color.DARK_GRAY);
            Paragraph title = new Paragraph(reportTitle, titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10);

            addTableCell(table, "ID Dossier", String.valueOf(dto.getId()));
            addTableCell(table, "Patient ID", String.valueOf(dto.getPatientId()));
            addTableCell(table, "Médecin ID", String.valueOf(dto.getMedecinId()));
            addTableCell(table, "Date de Création", dto.getDateCreation().toString());
            addTableCell(table, "Diagnostic", dto.getDiagnostic() != null ? dto.getDiagnostic() : "Aucun");
            addTableCell(table, "Observation", dto.getObservation() != null ? dto.getObservation() : "Aucune");

            document.add(table);
            document.close();

        } catch (DocumentException ex) {
            ex.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    private static void addTableCell(PdfPTable table, String label, String value) {
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.WHITE);
        Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Color.BLACK);

        PdfPCell cellLabel = new PdfPCell(new Phrase(label, headerFont));
        cellLabel.setBackgroundColor(new Color(0, 122, 255));
        cellLabel.setPadding(8);

        PdfPCell cellValue = new PdfPCell(new Phrase(value, cellFont));
        cellValue.setPadding(8);

        table.addCell(cellLabel);
        table.addCell(cellValue);
    }
}