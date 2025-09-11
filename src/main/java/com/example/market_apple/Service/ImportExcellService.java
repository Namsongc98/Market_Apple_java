package com.example.market_apple.Service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;

import com.example.market_apple.Repository.UserRepository;
import com.example.market_apple.Entity.User;

@Service
public class ImportExcellService {
    @Autowired
    private UserRepository userRepository;
    public void importUsersFromExcel(MultipartFile file) throws IOException {
        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0); // sheet đầu tiên
            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // bỏ dòng tiêu đề
                Row row = sheet.getRow(i);
                if (row == null) continue;

                String username = row.getCell(1).getStringCellValue();
                String email = row.getCell(2).getStringCellValue();
                String role = row.getCell(3).getStringCellValue();
                String password = row.getCell(4).getStringCellValue();


                if (userRepository.existsByUsername(username)) {
                    throw new IllegalArgumentException("Username already exists");
                }
                if (userRepository.existsByEmail(email)) {
                    throw new IllegalArgumentException("Email already exists");
                }

                User user = new User();
                user.setUsername(username);
                user.setEmail(email);
                user.setRole(role);
                user.setPassword(new BCryptPasswordEncoder().encode(password));
                userRepository.save(user);
            }
        }
    }
}
