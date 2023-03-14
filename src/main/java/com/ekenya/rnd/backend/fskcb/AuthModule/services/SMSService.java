package com.ekenya.rnd.backend.fskcb.AuthModule.services;

import com.ekenya.rnd.backend.fskcb.AuthModule.datasource.entities.AuthCodeType;
import com.ekenya.rnd.backend.fskcb.AuthModule.datasource.entities.SecurityAuthCodeEntity;
import com.ekenya.rnd.backend.fskcb.AuthModule.datasource.repositories.ISecurityAuthCodesRepository;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRAccountEntity;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.repositories.IDSRAccountsRepository;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.repositories.IUserAccountsRepository;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@Slf4j
public class SMSService implements ISmsService{

    //  @Value("CMCOM")
//    @Value("ECLECTICS")
    //SMS_SENDER_ID is CMCOM or ECLECTICS based on time of the day
//    @Value("${SMS_SENDER_ID}")
//    private String SMS_SENDER_ID;
    //@Value is CMCOM or ECLECTICS based on time of the day
    @Value("CMCOM")
    private String SMS_SENDER_ID;
    //
    @Value("ECLECTICS")
    private String SMS_SENDER_ID1;
    @Value("5094")
    private String client_id;
    @Value("https://testgateway.ekenya.co.ke:8443/ServiceLayer/pgsms/send")
    private String SMS_GATEWAY_URL;
    @Value("janty")
    private String SMS_USER_NAME;
    @Value("b0c95e2144bdd4c86b94501a814f9bbd9d025651d8497df04b7b7f318fe5172088c491906756a67727f6ea964e9caf1c034bf9bb267b821e6b43cb3dcc569d0f")
    private String SMS_PASSWORD;
    @Value("30")
    private String otpExpiresIn;

    @Autowired
    ISecurityAuthCodesRepository securityAuthCodesRepository;
    @Autowired
    private IUserAccountsRepository IUserAccountsRepository;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    IDSRAccountsRepository dsrAccountsRepository;
    private final static Logger logger = Logger.getLogger(SMSService.class.getName());

    @Override
    @Transactional
    public String sendSecurityCode(String staffNo, AuthCodeType type) {

        //
        try {
            //
            DSRAccountEntity account = dsrAccountsRepository.findByStaffNo(staffNo).get();
            String code = createAndSaveCode(account.getStaffNo(),account.getId(),type);
            String message;
            //
            if(type == AuthCodeType.DEVICE_VERIFICATION) {
                message = "Hello " + staffNo + ", your phone number verification code is " + code + "\nThe Field Sales App will automatically pick it up";
            }else{
                message = "Hello " + staffNo + ", use this as your PIN to login " + code ;
            }
            //
            JsonObject smsResponse = attemptSendSMS(message, account.getPhoneNo());
            if (smsResponse == null) {
                throw new RuntimeException("Unable to send sms");
            }
            int responseCode = smsResponse.get("ResultCode").getAsInt();
            if (responseCode != 0) {
                log.error("Send CODE failed. => "+smsResponse.get("ResultDesc").getAsString());
                //throw new RuntimeException(smsResponse.get("ResultDesc").getAsString());
            }

            return code;
        } catch (Exception e) {
            logger.log(Level.SEVERE,e.getMessage(),e);
        }
        return null;
    }
    ////Hello {}
    // You have been enrolled to participate in the KCB Sales Effectiveness Tool PoCUse the links below to access toolBack Office Admin Portal => http://kcb-ses-poc-frontend-kcb-ses-poc.apps.dev.aro.kcbgroup.com/Android Mobile Application => Your username is {}, and your first time password is {}Powered by Eclectics.io

    @Override
    public boolean sendPasswordEmail(String receiverEmail, String fullName, String password,String staffNumber) {
        try {

            String message = "Hello " + fullName + " you have been enrolled to participate in the KCB Sales Effectiveness Tool PoC.\n" +
                    " Use the links below to access  Back Office Admin Portal\n" +
                    " http://kcb-ses-poc-frontend-kcb-ses-poc.apps.dev.aro.kcbgroup.com/ \n" +
                    " Your username is " + staffNumber + ", and your first time password is " + password + "\n"+
                    " Powered by Eclectics.io";
            JsonObject emailResponse = sendEmail(message, receiverEmail);
            if (emailResponse == null) {
                throw new RuntimeException("Unable to send email");
            }
            int responseCode = emailResponse.get("ResultCode").getAsInt();
            if (responseCode != 0) {
                log.error("Send CODE failed. => "+emailResponse.get("ResultDesc").getAsString());
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE,e.getMessage(),e);
        }
        return false;
    }

    private JsonObject sendEmail(String message, String receiverEmail) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false);
            helper.setTo(receiverEmail);
            helper.setSubject("Account Created");
            helper.setText(message);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public boolean sendPasswordSMS(String phoneNo,String fullName, String password,String staffNo) {

        try{
            //
            String message = "Hello " + fullName + "\n your staffNo is : " + staffNo + " and your password is " + password +
                    ". Please use these credentials to log in to the portal. \n" +
                    "Please use this link to access the back office portal:\n" +
                    "https://kcb-ses-poc-frontend-kcb-ses-poc.apps.dev.aro.kcbgroup.com\n" +
                    "Thank you.\n" +
                    "Powered by Eclectics.io";

            //Please use these credentials to log in to the portal. We highly recommend that you change your password immediately after logging in, for added security.
            //
            JsonObject emailResponse = attemptSendSMS(message, phoneNo);
            if (emailResponse == null) {
                throw new RuntimeException("Unable to send sms");
            }
            int responseCode = emailResponse.get("ResultCode").getAsInt();
            if (responseCode != 0) {
                log.error("Send CODE failed. => "+emailResponse.get("ResultDesc").getAsString());
                //throw new RuntimeException(smsResponse.get("ResultDesc").getAsString());
            }
            return true;
        } catch (Exception e) {
            logger.log(Level.ALL,e.getMessage(),e);
        }

        return false;
    }

    @Override
    public boolean sendDSRCreatedSMS(String phoneNo, String fullName) {
        try{
            //

            String message = "Hello " + fullName + ", your account has been created successfully \nPlease use this link to install the app https://play.google.com/apps/internaltest/4701657927919684045";


            //
            JsonObject emailResponse = attemptSendSMS(message, phoneNo);
            if (emailResponse == null) {
                throw new RuntimeException("Unable to send sms");
            }
            int responseCode = emailResponse.get("ResultCode").getAsInt();
            if (responseCode != 0) {
                log.error("Send CODE failed. => "+emailResponse.get("ResultDesc").getAsString());
                //throw new RuntimeException(smsResponse.get("ResultDesc").getAsString());
            }
            return true;
        } catch (Exception e) {
            logger.log(Level.ALL,e.getMessage(),e);
        }
        return false;
    }

    @Override
    public boolean sendDsrCreatedEmail(String receiverEmail, String fullName) {
        try{
            String message1= " Dear " + fullName + ",\n" +
                    "\n" +
                    "Your account has been created successfully.\n" +
                    "\n" +
                    "Please use the following Link to download the app\n" +
                    "\n" +
                    "https://play.google.com/apps/internaltest/4701657927919684045" +

                    "Regards,\n" +
                    "KCB Team";
            JsonObject emailResponse = sendEmail(receiverEmail,message1);
            if (emailResponse == null) {
                throw new RuntimeException("Unable to send sms");
            }
            int responseCode = emailResponse.get("ResultCode").getAsInt();
            if (responseCode != 0) {
                log.error("Send CODE failed. => "+emailResponse.get("ResultDesc").getAsString());
            }
            return true;
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
        long seed = System.currentTimeMillis();
        Random random = new Random(seed);
        int max = 99999999, min = 10000000;
        JsonObject jsonObjectBody = new JsonObject();
        jsonObjectBody.addProperty("to", phoneNo);
        jsonObjectBody.addProperty("message", message);
        if (LocalTime.now().isBefore(LocalTime.of(18, 0))) {
            jsonObjectBody.addProperty("from", SMS_SENDER_ID1);
        } else {
            jsonObjectBody.addProperty("from", SMS_SENDER_ID);
        }
//        jsonObjectBody.addProperty("from", SMS_SENDER_ID);
        jsonObjectBody.addProperty("transactionID", "FS"+(random.nextInt((max + 1)-min)+min));
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
        for (SecurityAuthCodeEntity code : securityAuthCodesRepository.findAllByUserId(uid)){
            if(code.getType() == type && !code.getExpired()){
                code.setExpired(true);
            }
        }
        //
        SecurityAuthCodeEntity securityAuthCode = new SecurityAuthCodeEntity();
        securityAuthCode.setCode(sb.toString());
        securityAuthCode.setUserId(uid);
        securityAuthCode.setType(AuthCodeType.DEVICE_VERIFICATION);
        securityAuthCode.setDateIssued(Calendar.getInstance().getTime());
        securityAuthCode.setExpiresInMinutes(30);
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
