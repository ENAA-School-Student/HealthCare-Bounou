package com.HealthCare.HealthCare.utils;

import com.HealthCare.HealthCare.dto.RendezVousDto;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelGeneratorUtil {

    public static ByteArrayInputStream generateRendezVousExcel(List<RendezVousDto> rdvList) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Rendez-vous");

            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.WHITE.getIndex());

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);
            headerCellStyle.setFillBackgroundColor(IndexedColors.ROYAL_BLUE.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            Row headerRow = sheet.createRow(0);
            String[] columns = {"ID", "Date/Heure", "Statut", "Patient ID", "Médecin ID"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerCellStyle);
            }

            int rowIdx = 1;
            for (RendezVousDto rdv : rdvList) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(rdv.getId());
                row.createCell(1).setCellValue(rdv.getDateRendezVous() != null ? rdv.getDateRendezVous().toString() : "");
                row.createCell(2).setCellValue(rdv.getStatus() != null ? rdv.getStatus().toString() : "");
                row.createCell(3).setCellValue(rdv.getPatientId());
                row.createCell(4).setCellValue(rdv.getMedecinId());
            }

            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Échec de la génération du fichier Excel", e);
        }
    }
}