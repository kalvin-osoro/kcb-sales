package com.ekenya.rnd.backend.fskcb.AdminModule.services;

import com.ekenya.rnd.backend.fskcb.AdminModule.models.reqs.LoadLogFileRequest;
import com.ekenya.rnd.backend.fskcb.AdminModule.models.resp.LogFileResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

import static com.ekenya.rnd.backend.fskcb.SpringBootKcbRestApiApplication.BASE_LOGS_DIR;

@Slf4j
@Service
public class AppLogsService implements IAppLogsService{

    @Autowired
    ObjectMapper mObjectMapper;
    @Override
    public ArrayNode loadAvailableLogs() {

        try{
            ArrayNode node = mObjectMapper.createArrayNode();

            Path file = Paths.get(BASE_LOGS_DIR);
            File[] directoryListing = file.toFile().listFiles();
            if (directoryListing != null) {
                for (File child : directoryListing) {
                    //logback-app.2022-12-07 12.0.log
                    String[] metadata = child.getName().split(" ");

                    ObjectNode fobject = mObjectMapper.createObjectNode();
                    fobject.put("date",metadata[0].split(Pattern.quote("."))[1]);
                    fobject.put("hour",metadata[1].split(Pattern.quote("."))[0]);
                    fobject.put("index",metadata[1].split(Pattern.quote("."))[1]);
                    node.add(fobject);
                }
            }
            return  node;
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }
        return null;
    }

    @Override
    public LogFileResponse loadLogFile(LoadLogFileRequest model) {


        try{

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            String fileName = "logback-app."+df.format(model.getDate())+" "+model.getHour()+"."+model.getIndex()+".log";

            Path file = Paths.get(BASE_LOGS_DIR);
            File[] directoryListing = file.toFile().listFiles();
            if (directoryListing != null) {
                for (File child : directoryListing) {
                    //logback-app.2022-12-07 12.0.log
                    if(child.getName().equalsIgnoreCase(fileName)) {
                        LogFileResponse resp = new LogFileResponse();
                        resp.setFileName(fileName);
                        resp.setContent(child);
                        return resp;
                    }
                }
            }
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }
        return null;
    }
}
