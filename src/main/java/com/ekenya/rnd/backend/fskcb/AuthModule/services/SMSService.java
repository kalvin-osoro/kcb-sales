package com.ekenya.rnd.backend.fskcb.AuthModule.services;

import com.ekenya.rnd.backend.fskcb.AuthModule.datasource.entities.AuthCodeType;
import com.ekenya.rnd.backend.fskcb.AuthModule.datasource.entities.SecurityAuthCodeEntity;
import com.ekenya.rnd.backend.fskcb.AuthModule.datasource.repositories.ISecurityAuthCodesRepository;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.UserAccount;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.repositories.UserRepository;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@Slf4j
public class SMSService implements ISmsService{

    //
    @Value("ECLECTICS")
    private String SMS_SENDER_ID;
    @Value("5165")
    private String client_id;
    @Value("https://testgateway.ekenya.co.ke:8443/ServiceLayer/pgsms/send")
    private String SMS_GATEWAY_URL;
    @Value("fieldagent")
    private String SMS_USER_NAME;
    @Value("a7d11f3c635c69ca81d8bd5da9f4e7e97ceb2aa296bc6061e5f477db9d6b8b1b2d08ccdb701e94d9f0f097fbd0d43fac1b67218c427abadf2e6221bd74c3e301")
    private String SMS_PASSWORD;
    @Value("30")
    private String otpExpiresIn;

    @Autowired
    ISecurityAuthCodesRepository securityAuthCodesRepository;
    @Autowired
    private UserRepository userRepository;
    private final static Logger logger = Logger.getLogger(SMSService.class.getName());

    @Override
    @Transactional
    public boolean sendSecurityCode(String staffNo, AuthCodeType type) {

        //
        try {
            //
            UserAccount account = userRepository.findByStaffNo(staffNo).get();
            String code = createAndSaveCode(account.getStaffNo(),account.getId(),type);
            String message;
            //
            if(type == AuthCodeType.DEVICE_VERIFICATION) {
                message = "Hello " + staffNo + ", your phone number verification code is " + code + "\nThe will automatically pick it up";
            }else{
                message = "Hello " + staffNo + ", use this as your PIN to login " + code ;
            }
            //
            JsonObject smsResponse = attemptSendSMS(message, account.getPhoneNumber());
            if (smsResponse == null) {
                throw new RuntimeException("Unable to send sms");
            }
            int responseCode = smsResponse.get("ResultCode").getAsInt();
            if (responseCode != 0) {
                throw new RuntimeException(smsResponse.get("ResultDesc").getAsString());
            }

            return true;
        } catch (Exception e) {
            logger.log(Level.ALL,e.getMessage(),e);
        }
        return false;
    }

    @Override
    public boolean sendPasswordEmail(String receiverEmail, String name, String password) {

        try{

            //TODO Send email ..
        } catch (Exception e) {
            logger.log(Level.ALL,e.getMessage(),e);
        }

        return false;
    }

    @Scheduled(fixedRate = 1)

    private JsonObject attemptSendSMS(String message, String phoneNo) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
////        String url = environment.getProperty("sms_gateway.url");
//        String client_id=
//        String username =
//        String password =
        JsonObject jsonObjectBody = new JsonObject();
        jsonObjectBody.addProperty("to", phoneNo);
        jsonObjectBody.addProperty("message", message);
        jsonObjectBody.addProperty("from", SMS_SENDER_ID);
        jsonObjectBody.addProperty("transactionID", "ZHD839278X@");
        jsonObjectBody.addProperty("clientid", client_id);
        jsonObjectBody.addProperty("username", SMS_USER_NAME);
        jsonObjectBody.addProperty("password", SMS_PASSWORD);
        logger.info("Message body is: " + jsonObjectBody);
        HttpEntity<String> entity = new HttpEntity<>(jsonObjectBody.toString(), headers);
        ResponseEntity<String> responseEntity = null;
        try {
            logger.info("Sending sms");
            responseEntity = restTemplate.postForEntity(SMS_GATEWAY_URL,
                    entity, String.class);
            logger.info("After Sending sms");
            JsonObject payload = JsonParser.parseString(responseEntity.getBody()).getAsJsonObject();
            return payload;
        } catch (Exception e) {
            logger.info("Send SMS failed. " + e.getMessage());
        }
        return null;
    }


    private String createAndSaveCode(String staffNo,long uid,AuthCodeType type){
        String candidateChars = "1234567890";
        //
        Random random = new Random(System.currentTimeMillis());
        //
        StringBuilder sb = new StringBuilder();
        //
        int len = type == AuthCodeType.DEVICE_VERIFICATION ? 6 : 4;
        for (int i = 0; i < len; i++) {
            sb.append(candidateChars.charAt(random.nextInt(candidateChars
                    .length())));
        }
        //
        //
        SecurityAuthCodeEntity securityAuthCode = new SecurityAuthCodeEntity();
        securityAuthCode.setCode(sb.toString());
        securityAuthCode.setUserId(uid);
        securityAuthCode.setType(AuthCodeType.DEVICE_VERIFICATION);
        securityAuthCode.setDateIssued(Calendar.getInstance().getTime());
        securityAuthCode.setExpiresInMinutes(30);
        //
        for (SecurityAuthCodeEntity code : securityAuthCodesRepository.findAllByUserId(uid)){
            if(code.getType() == securityAuthCode.getType()){
                code.setExpired(true);
            }
        }
        //
        securityAuthCodesRepository.save(securityAuthCode);

        return securityAuthCode.getCode();
    }

    @Scheduled(fixedDelay = 5000)
    public void scanAndExpireSecCodes() {
        List<SecurityAuthCodeEntity> activeCodes = securityAuthCodesRepository.findAllByExpiredFalse();
        if (!activeCodes.isEmpty()) {
            activeCodes
                    .parallelStream()
                    .filter(otp -> otpExpiryValidator.test(otp.getDateIssued()))
                    .forEach(otp -> {
                        otp.setExpired(true);
                        securityAuthCodesRepository.save(otp);
                    });
        }
    }

    static Predicate<Date> otpExpiryValidator = date -> {
        long diffInMillies = Math.abs(new Date().getTime() - date.getTime());
        long diff = TimeUnit.SECONDS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        return diff > 1200;
    };
}
