package com.deltacode.kcb.service.impl;
import com.deltacode.kcb.UserManagement.entity.UserApp;
import com.deltacode.kcb.UserManagement.repository.UserRepository;
import com.deltacode.kcb.entity.OTP;
import com.deltacode.kcb.exception.MessageResponse;
import com.deltacode.kcb.payload.FirstTimeLoginRequest;
import com.deltacode.kcb.payload.ValidateOTPRequest;
import com.deltacode.kcb.repository.OTPRepository;
import com.deltacode.kcb.service.SmsService;
import com.deltacode.kcb.utils.Utility;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.logging.Logger;

@Service
public class SmsServiceImpl implements SmsService{
    @Value("5165")
    private String client_id;
    @Value("https://testgateway.ekenya.co.ke:8443/ServiceLayer/pgsms/send")
    private String url;
    @Value("fieldagent")
    private String username;
    @Value("a7d11f3c635c69ca81d8bd5da9f4e7e97ceb2aa296bc6061e5f477db9d6b8b1b2d08ccdb701e94d9f0f097fbd0d43fac1b67218c427abadf2e6221bd74c3e301")
    private String password;
    @Value("")
    private String otpExpiresIn;
    private final static Logger logger = Logger.getLogger(SmsServiceImpl.class.getName());


    @Autowired
    private OTPRepository otpRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    static final long ONE_MINUTE_IN_MILLIS=60000;//millisecs

    @Transactional
    @Override
    public ResponseEntity<?> sendOTP(String phoneNumber,String staffId) {
        LinkedHashMap<String, Object> responseObject = new LinkedHashMap<>();
        //check if user exists by phoneNo and staffId
        try {
            Optional<UserApp> optionalUser = userRepository.findByPhoneNumberAndStaffId(phoneNumber,staffId);
            if(!optionalUser.isPresent())
                throw new RuntimeException("User does not exist");
            UserApp user = optionalUser.get();
            if(user.getIsVerified())
                throw new RuntimeException("User is already verified");
            int otpNumber = Utility.generateOtp();
            String message = "User verification code is "+ otpNumber;
            System.out.println("OTP: "+otpNumber);
            JsonObject smsResponse = sendSMS(message, phoneNumber);
            if(smsResponse == null)
                throw new RuntimeException("Unable to send sms");
            int responseCode = smsResponse.get("ResultCode").getAsInt();
            if(responseCode != 0)
                throw new RuntimeException(smsResponse.get("ResultDesc").getAsString());
            OTP otp = new OTP();
            otp.setOtpNumber(otpNumber);
            otp.setRecipient(phoneNumber);
            otp.setActive(true);
            otp.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            otpRepository.save(otp);
            responseObject.put("status", "success");
            responseObject.put("message", "OTP successfully sent");
            return ResponseEntity.ok().body(responseObject);
        }
        catch (Exception e){
            responseObject.put("status", "error");
            responseObject.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(responseObject);
        }

    }
    @Scheduled(fixedDelay = 1000)
    public void expireOtps(){
        List<OTP> activeOtps= otpRepository.findAllByActiveTrue();
        if(!activeOtps.isEmpty()){
            activeOtps
                    .parallelStream()
                    .filter(otp-> otpExpiryValidator.test(otp.getCreatedOn()))
                    .forEach(otp-> {
                        otp.setActive(false);
                        otpRepository.save(otp);
                    });
        }
    }

    static Predicate<Date> otpExpiryValidator=date->{
        long diffInMillies = Math.abs(new Date().getTime() - date.getTime());
        long diff = TimeUnit.SECONDS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        return diff>1200;
    };
    @Override
    public ResponseEntity<?> validateOtp(ValidateOTPRequest validateOTPRequest) {
        LinkedHashMap<String, Object> responseObject = new LinkedHashMap<>();
        LinkedHashMap<String, Object> responseParams = new LinkedHashMap<>();
        try {
            Optional<OTP> optionalOTP =
                    otpRepository.findByRecipientAndOtpNumberAndActiveTrue(
                            validateOTPRequest.getPhoneNumber(), validateOTPRequest.getOtpNumber());
            if(!optionalOTP.isPresent())
                throw new RuntimeException("Invalid OTP");
            OTP otp = optionalOTP.get();
            otp.setActive(false);
//            String otpExpiresIn = environment.getProperty("otp.expirytime");
            Date dateCreated = otp.getCreatedOn();
            Calendar cal = Calendar.getInstance();
            cal.setTime(dateCreated);
            long t= cal.getTimeInMillis();
            Date dateCreatedAfterAddingMins=new Date(
                    t + (Integer.parseInt(otpExpiresIn) * ONE_MINUTE_IN_MILLIS));
            if(dateCreatedAfterAddingMins.
                    compareTo(Utility.getPostgresCurrentTimeStampForInsert()) > 0)
                throw new RuntimeException("OTP has already expired");
            //update user as verified
            Optional<UserApp> optionalUser = userRepository.findByPhoneNumberAndStaffId(
                    validateOTPRequest.getPhoneNumber(),validateOTPRequest.getStaffId());
            if(!optionalUser.isPresent())
                throw new RuntimeException("User does not exist");
            UserApp user = optionalUser.get();
            user.setIsVerified(true);
            userRepository.save(user);
            responseObject.put("status", "success");
            responseObject.put("message", "OTP validated successfully");
            responseObject.put("data", responseParams);
            return ResponseEntity.ok().body(responseObject);
        }catch (Exception e){
            responseObject.clear();
            responseObject.put("status","failed");
            responseObject.put("message","OTP is invalid");
            return ResponseEntity.ok().body(responseObject);
        }
    }

    @Scheduled(fixedRate = 1)

    public JsonObject sendSMS(String message, String phoneNo){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
////        String url = environment.getProperty("sms_gateway.url");
//        String client_id=
//        String username =
//        String password =
        JsonObject jsonObjectBody = new JsonObject();
        jsonObjectBody.addProperty("to", phoneNo);
        jsonObjectBody.addProperty("message", message);
        jsonObjectBody.addProperty("message", message);
        jsonObjectBody.addProperty("from", "ECLECTICS");
        jsonObjectBody.addProperty("transactionID", "ZHD839278X@");
        jsonObjectBody.addProperty("clientid", client_id);
        jsonObjectBody.addProperty("username", username);
        jsonObjectBody.addProperty("password", password);
        logger.info("Message body is: "+jsonObjectBody);
        HttpEntity<String> entity = new HttpEntity<>( jsonObjectBody.toString(), headers);
        ResponseEntity<String> responseEntity = null;
        try {
            logger.info("Sending sms" );
            responseEntity = restTemplate.postForEntity(url,
                    entity , String.class);
            logger.info("After Sending sms" );
            JsonObject payload = JsonParser.parseString(responseEntity.getBody()).getAsJsonObject();
            return  payload;
        }catch (Exception e){
            logger.info("Error is "+e.getMessage());
            return  null;
        }
    }
    //check is first time login if yes gerate first time password and let user change it
    @Override
    public ResponseEntity<?> firstTimeLogin(FirstTimeLoginRequest firstTimeLoginRequest) {
        LinkedHashMap<String, Object> responseObject = new LinkedHashMap<>();
        LinkedHashMap<String, Object> responseParams = new LinkedHashMap<>();
        try {
            Optional<UserApp> optionalUser = userRepository.findByPhoneNumberAndStaffId(
                    firstTimeLoginRequest.getPhoneNumber(),firstTimeLoginRequest.getStaffId());
            if(!optionalUser.isPresent())
                throw new RuntimeException("User does not exist");
            UserApp user = optionalUser.get();
            if(!user.getFirstLogin())
                throw new RuntimeException("User has already logged in");
            String password = Utility.generatePassword();
            //send password to user
            String message = "Your first time password is "+password;
            JsonObject jsonObject = sendSMS(message, firstTimeLoginRequest.getPhoneNumber());
            user.setPassword(passwordEncoder.encode(password));
            user.setFirstLogin(false);
            userRepository.save(user);
            responseObject.put("status", "success");
            responseObject.put("message", "User verified successfully");
            responseParams.put("password", password);
            responseObject.put("data", responseParams);
            return ResponseEntity.ok().body(responseObject);
        }catch (Exception e){
            responseObject.clear();
            responseObject.put("status","failed");
            responseObject.put("message",e.getMessage());
            return ResponseEntity.ok().body(responseObject);
        }
    }
    //validate first time password


}