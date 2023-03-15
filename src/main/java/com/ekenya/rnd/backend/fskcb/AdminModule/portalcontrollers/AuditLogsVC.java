package com.ekenya.rnd.backend.fskcb.AdminModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.AdminModule.models.reqs.LoadLogFileRequest;
import com.ekenya.rnd.backend.fskcb.AdminModule.models.resp.LogFileResponse;
import com.ekenya.rnd.backend.fskcb.AdminModule.services.IAppLogsService;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.SystemRoles;
import com.ekenya.rnd.backend.fskcb.UserManagement.models.reps.RoleDetailsRequest;
import com.ekenya.rnd.backend.fskcb.UserManagement.services.IRolesService;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.Api;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;

@CrossOrigin(origins = "*")
@Api(value = "System Logs")
@RestController
@RequestMapping(path = "/api/v1")
//@PreAuthorize("hasAuthority('"+ SystemRoles.SYS_ADMIN+"') or hasAuthority('"+SystemRoles.ADMIN+"')")
public class AuditLogsVC {


    @Autowired
    ObjectMapper mObjectMapper;

    @Autowired
    private IAppLogsService logsService;

    @PostMapping("/audit-all-log-files")
    public ResponseEntity<?> getAllLogFiles() {

        //INSIDE SERVICE
        ArrayNode resp = logsService.loadAvailableLogs();

        //Response
        if(resp != null){
            //Object

            return ResponseEntity.ok(new BaseAppResponse(1,resp,"Request Processed Successfully"));
        }
        //Response
        return ResponseEntity.ok(new BaseAppResponse(0,mObjectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));

    }


    @PostMapping("/audit-get-log-content")
    public ResponseEntity<?> getLogFileJSON(@RequestBody LoadLogFileRequest request, HttpServletResponse response) {

        //INSIDE SERVICE
        LogFileResponse resp = logsService.loadLogFile(request);

        //Response
        if(resp != null){
            //Object
//            response.setContentType("application/pdf");
//            response.setHeader("Content-Disposition", "attachment; filename=\"somefile.pdf\"");

            ObjectNode data = mObjectMapper.createObjectNode();
            data.put("fname", resp.getFileName());
            try {
                data.put("content", Files.readString(resp.getContent().toPath()));
                return ResponseEntity.ok(new BaseAppResponse(1,resp,"Request Processed Successfully"));
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.ok(new BaseAppResponse(0,resp,"Request could NOT be processed. "+e.getMessage()));
            }
        }
        //Response
        return ResponseEntity.ok(new BaseAppResponse(0,mObjectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));

    }

    @PostMapping("/audit-get-log-file")
    public ResponseEntity<?> getLogFile(@RequestBody LoadLogFileRequest request, HttpServletResponse response) {

        //INSIDE SERVICE
        LogFileResponse resp = logsService.loadLogFile(request);

        //Response
        if(resp != null){
            //Object
            try {
                HttpHeaders headers = new HttpHeaders();
                headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
                headers.add("Pragma", "no-cache");
                headers.add("Expires", "0");
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resp.getFileName() + "\"");
                //
                InputStreamResource resource = new InputStreamResource(new FileInputStream(resp.getContent()));
                //
                return ResponseEntity.ok()
                        .headers(headers)
                        .contentLength(resp.getContent().length())
                        .contentType(MediaType.TEXT_PLAIN)
                        .body(resource);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //Response
        return ResponseEntity.ok(new BaseAppResponse(0,mObjectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));

    }
}
