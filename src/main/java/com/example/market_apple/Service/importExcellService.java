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
import com.example.market_apple.Enum.LoginStatus;

@Service
public class importExcellService {
    @Autowired
    private static UserRepository userRepository;

    public static void importUsersFromExcel(MultipartFile file) throws IOException {
        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0); // sheet đầu tiên
            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // bỏ dòng tiêu đề
                Row row = sheet.getRow(i);
                if (row == null) continue;

                User user = new User();
                user.setUsername(row.getCell(1).getStringCellValue());
                System.out.println("1" + row.getCell(1).getStringCellValue());
                user.setEmail(row.getCell(2).getStringCellValue());
                System.out.println("2" + row.getCell(2).getStringCellValue());
                user.setRole(row.getCell(3).getStringCellValue());
                System.out.println("3" + row.getCell(3).getStringCellValue());

                user.setPassword(new BCryptPasswordEncoder().encode(row.getCell(4).getStringCellValue()));
                System.out.println("4" + row.getCell(4).getStringCellValue());

                System.out.println("5" + user);




//                String statusStr = row.getCell(4).getStringCellValue().toUpperCase();
               // user.setStatus(LoginStatus.valueOf(statusStr));

                userRepository.save(user);
            }
        }
    }
}
