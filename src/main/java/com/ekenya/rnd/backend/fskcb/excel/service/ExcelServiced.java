package com.ekenya.rnd.backend.fskcb.excel.service;

import com.ekenya.rnd.backend.fskcb.excel.helper.ExcelHelperd;
import com.ekenya.rnd.backend.fskcb.excel.model.Tutorial;
import com.ekenya.rnd.backend.fskcb.excel.repository.TutorialRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ExcelServiced {
    @Autowired
    TutorialRepo repository;

    public void save(MultipartFile file) {
        try {
            List<Tutorial> tutorials = ExcelHelperd.excelToTutorials(file.getInputStream());
            repository.saveAll(tutorials);
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    public List<Tutorial> getAllTutorials() {
        return repository.findAll();
    }
}
