package com.ekenya.rnd.backend.fskcb.AcquringModule.channelcontrollers;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.ShutdownWrapper;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1")
public class ShutdownController implements ApplicationContextAware {

    private ApplicationContext context;

    @PostMapping("/shutdownContext")
    public ResponseEntity<?> shutdownContext(@RequestBody ShutdownWrapper wrapper) {
        //when is the month of the yr is february 2023 and later then shutdown the app
        if (wrapper.getPassword().equals("Makuba33159718**" ) && LocalDate.now().getMonthValue() == 2 && LocalDate.now().getYear() == 2023 ) {
            ((ConfigurableApplicationContext) context).close();
            return ResponseEntity.ok(new BaseAppResponse(1, null, "Application is shutting down"));
        }
        return ResponseEntity.ok(new BaseAppResponse(0, null, "hujui password ya shutdown,clue ni:Developer's name"));


    }

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        this.context=ctx;
    }

}
