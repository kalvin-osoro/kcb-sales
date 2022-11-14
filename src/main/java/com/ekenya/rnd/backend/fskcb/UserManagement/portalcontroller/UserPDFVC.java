package com.ekenya.rnd.backend.fskcb.UserManagement.portalcontroller;

import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.UserAccount;
import com.ekenya.rnd.backend.fskcb.UserManagement.helper.UserPDFExporter;
import com.ekenya.rnd.backend.fskcb.UserManagement.services.ExcelService;
import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@RestController
@RequestMapping(path = "/api/v1")
public class UserPDFVC {
    @Autowired
    private ExcelService service;

    @GetMapping("/users-export/pdf")
    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<UserAccount> listUsers = service.listAll();

        UserPDFExporter exporter = new UserPDFExporter(listUsers);
        exporter.export(response);

    }
}
