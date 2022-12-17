package com.ekenya.rnd.backend.fskcb.AdminModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.AdminModule.models.reqs.LoadLogFileRequest;
import com.ekenya.rnd.backend.fskcb.AdminModule.services.IAppLogsService;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.SystemRoles;
import com.ekenya.rnd.backend.fskcb.UserManagement.models.reps.RoleDetailsRequest;
import com.ekenya.rnd.backend.fskcb.UserManagement.services.IRolesService;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "*")
@Api(value = "System Logs")
@RestController
@RequestMapping(path = "/api/v1")
@PreAuthorize("hasAuthority('"+ SystemRoles.SYS_ADMIN+"') or hasAuthority('"+SystemRoles.ADMIN+"')")
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


    @PostMapping("/audit-get-log-file")
    public ResponseEntity<?> getLogFile(@RequestBody LoadLogFileRequest request, HttpServletResponse response) {

        //INSIDE SERVICE
        ObjectNode resp = logsService.loadLogFile(request);

        //Response
        if(resp != null){
            //Object
//            response.setContentType("application/pdf");
//            response.setHeader("Content-Disposition", "attachment; filename=\"somefile.pdf\"");

            return ResponseEntity.ok(new BaseAppResponse(1,resp,"Request Processed Successfully"));
        }
        //Response
        return ResponseEntity.ok(new BaseAppResponse(0,mObjectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));

    }
}
