package com.example.market_apple.Controller;

import com.example.market_apple.annotation.NoAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.market_apple.Service.ExportUsersToExcel;
import com.example.market_apple.Service.importExcellService;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private ExportUsersToExcel excelExportService;

    @GetMapping("/export")
    @NoAuth
    public ResponseEntity<Resource> exportUsersToExcel() {
        ByteArrayInputStream in = excelExportService.exportUsersToExcel();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=users.xlsx");
        headers.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
        headers.add(HttpHeaders.PRAGMA, "no-cache");
        headers.add(HttpHeaders.EXPIRES, "0");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(in));
    }

    @PostMapping("/import")
    @NoAuth
    public ResponseEntity<String> importUsersFromExcel (@RequestParam("file") MultipartFile  file){
        try {
            importExcellService.importUsersFromExcel(file);
            return ResponseEntity.ok("Import thành công!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Import thất bại: " + e.getMessage());
        }
    }
}
