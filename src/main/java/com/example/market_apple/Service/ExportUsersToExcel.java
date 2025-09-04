package com.example.market_apple.Service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.market_apple.Repository.UserRepository;
import com.example.market_apple.Entity.User;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.Instant;
import java.util.List;

@Service

public class ExportUsersToExcel {
    @Autowired
    private UserRepository userRepository;

    public ByteArrayInputStream exportUsersToExcel() {
        List<User> users = userRepository.findAll();

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Users");

            // Tạo header row
            Row headerRow = sheet.createRow(0);
            String[] columns = {"ID", "Name", "Email", "Role", "Invited At", "Last Login", "Created At"};
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
            }

            // Tạo data rows
            CellStyle dateStyle = workbook.createCellStyle();
            CreationHelper createHelper = workbook.getCreationHelper();
            dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));

            int rowNum = 1;
            for (User user : users) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(user.getId());
                row.createCell(1).setCellValue(user.getUsername());
                row.createCell(2).setCellValue(user.getEmail());
                row.createCell(3).setCellValue(user.getRole());
                if (user.getStatus() != null) {
                    row.createCell(2).setCellValue(user.getStatus().name());

                } else {
                    row.createCell(2).setCellValue("N/A"); // hoặc "UNKNOWN"
                }
//                setInstantCellValue(row.createCell(4), user.getInvitedAt(), dateStyle);
//                setInstantCellValue(row.createCell(5), user.getLastLogin(), dateStyle);
//                setInstantCellValue(row.createCell(6), user.getCreatedAt(), dateStyle);
            }

            // Auto-size columns
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Viết workbook ra output stream
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Failed to export users to Excel: " + e.getMessage());
        }
    }

//    private void setInstantCellValue(Cell cell, Instant instant, CellStyle dateStyle) {
//        if (instant != null) {
//            cell.setCellValue(Date.from(instant));
//            cell.setCellStyle(dateStyle);
//        } else {
//            cell.setCellValue("");
//        }
//    }

}
