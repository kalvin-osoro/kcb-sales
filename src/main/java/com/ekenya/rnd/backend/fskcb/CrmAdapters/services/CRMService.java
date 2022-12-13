package com.ekenya.rnd.backend.fskcb.CrmAdapters.services;

import com.ekenya.rnd.backend.fskcb.SpringBootKcbRestApiApplication;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.NegotionRateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.logging.Logger;

@Service
public class CRMService implements ICRMService {

    private static final String GET_CUSTOMER_DETAILS_BY_ACCOUNT_NO = "http://keprecrmappde02:8081/api/Values?entity=accountsbyaccno&paramval=";

    @Resource
    public Environment environment;

    RestTemplate restTemplate = new RestTemplate();
    public static String accessToken;

    private Logger log = Logger.getLogger(getClass().getName());

    @PostConstruct
    void processToken() {
        System.out.println("generating crm token");
        accessToken = generateOauth2Token();
    }

    @Override
    public String generateOauth2Token() {

        String auth = environment.getProperty("credentials.client_id") + ":" + environment.getProperty("credentials.client_secret");

        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.US_ASCII));

        HttpHeaders headers = new HttpHeaders();

        String basicRequest = "Basic " + new String(encodedAuth);

        headers.set("Authorization", basicRequest);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", environment.getProperty("credentials.username"));
        map.add("password", environment.getProperty("credentials.password"));
        map.add("grant_type", environment.getProperty("credentials.grant_type"));
        map.add("resource", environment.getProperty("credentials.resource"));
        map.add("scope", environment.getProperty("credentials.scope"));

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
        try {
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(environment.getProperty("endpoints.crm.generateToken"),
                    entity, String.class);


            JsonObject payload = JsonParser.parseString(responseEntity.getBody()).getAsJsonObject();

            return payload.get("access_token").getAsString();
        } catch (Exception ex) {
            log.info(ex.getMessage());
        }
        return null;
    }

    @Override
    public JsonObject createCustomer(JsonObject jsonObject) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(jsonObject.toString(), headers);

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(environment.getProperty("endpoints.crm.createCustomer"),
                    HttpMethod.POST, entity, String.class);

            JsonObject payload = JsonParser.parseString(responseEntity.getBody()).getAsJsonObject();

            return payload;
        } catch (Exception ex) {
            log.info(ex.getMessage());
        }
        return null;


    }


    @Override
    public JsonObject createLead(JsonObject jsonObject) {
        String token = "Bearer "+ generateOauth2Token();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",token);
        headers.set("Prefer", "return=representation");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(jsonObject.toString(), headers);
        log.info("in createLead ");
        ResponseEntity<String> responseEntity = null;
        try {

            responseEntity = restTemplate.exchange(environment.getProperty("endpoints.crm.createLead"), HttpMethod.POST, entity, String.class);
            return JsonParser.parseString(responseEntity.getBody()).getAsJsonObject();
        }catch (HttpClientErrorException e){
            if (e.getRawStatusCode() == 401) {
                SpringBootKcbRestApiApplication.accessToken = generateOauth2Token();
                createCustomer(jsonObject);
            }
        }

        return null;
    }

    @Override
    public JsonArray fetchStaffAccounts() {
        //fetch staff accounts from crm waiting for the endpoint
        String token = "Bearer "+ generateOauth2Token();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",token);
        headers.set("Prefer", "return=representation");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        log.info("in fetchStaffAccounts ");
        ResponseEntity<String> responseEntity = null;
        try {

            responseEntity = restTemplate.exchange(environment.getProperty("endpoints.crm.fetchStaffAccounts"), HttpMethod.GET, entity, String.class);
            return JsonParser.parseString(responseEntity.getBody()).getAsJsonArray();
        }catch (HttpClientErrorException e){
            if (e.getRawStatusCode() == 401) {
                SpringBootKcbRestApiApplication.accessToken = generateOauth2Token();
                fetchStaffAccounts();
            }

        }


        return null;
    }


    @Override
    public JsonObject getLoanDetails(String crmAccountId) {
        //get loan details from crm waiting for the endpoint
        String token = "Bearer "+ generateOauth2Token();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",token);
        headers.set("Prefer", "return=representation");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        log.info("in getLoanDetails ");
        ResponseEntity<String> responseEntity = null;
        try {

            responseEntity = restTemplate.exchange(environment.getProperty("endpoints.crm.getLoanDetails") + crmAccountId, HttpMethod.GET, entity, String.class);
            return JsonParser.parseString(responseEntity.getBody()).getAsJsonObject();
        }catch (HttpClientErrorException e){
            if (e.getRawStatusCode() == 401) {
                SpringBootKcbRestApiApplication.accessToken = generateOauth2Token();
                getLoanDetails(crmAccountId);
            }

        }

        return null;
    }

    @Override
    public JsonObject getLead(String crmUserId) {
        String token = "Bearer  "+ generateOauth2Token();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",token);
        headers.set("Prefer", "return=representation");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        log.info("in getLead ");
        ResponseEntity<String> responseEntity = null;
        try {

            responseEntity = restTemplate.exchange(environment.getProperty("endpoints.crm.getLead") + crmUserId, HttpMethod.GET, entity, String.class);
            return JsonParser.parseString(responseEntity.getBody()).getAsJsonObject();
        }catch (HttpClientErrorException e){
            if (e.getRawStatusCode() == 401) {
                SpringBootKcbRestApiApplication.accessToken = generateOauth2Token();
                getLead(crmUserId);
            }

        }
        return null;
    }

    @Override
    public JsonObject getCustomerRefNo(String accountNumber) {
        return null;
    }

    @Override
    public JsonObject fetchAccountOpenedByStaffNo(String staffNo) {
        String token = "Bearer "+ generateOauth2Token();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",token);
        headers.set("Prefer", "return=representation");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        log.info("in fetchAccountOpenedByStaffNo ");
        ResponseEntity<String> responseEntity = null;
        try {

            responseEntity = restTemplate.exchange(environment.getProperty("endpoints.crm.fetchAccountOpenedByStaffNo") + staffNo, HttpMethod.GET, entity, String.class);
            return JsonParser.parseString(responseEntity.getBody()).getAsJsonObject();
        }catch (HttpClientErrorException e){
            if (e.getRawStatusCode() == 401) {
                SpringBootKcbRestApiApplication.accessToken = generateOauth2Token();
                fetchAccountOpenedByStaffNo(staffNo);
            }

        }
        return null;
    }

    @Override
    public JsonObject fetchLoansSoldByStaffNo(String staffNo) {
        String token = "Bearer "+ generateOauth2Token();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",token);
        headers.set("Prefer", "return=representation");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        log.info("in fetchLoansSoldByStaffNo ");
        ResponseEntity<String> responseEntity = null;
        try {

            responseEntity = restTemplate.exchange(environment.getProperty("endpoints.crm.fetchLoansSoldByStaffNo") + staffNo, HttpMethod.GET, entity, String.class);
            return JsonParser.parseString(responseEntity.getBody()).getAsJsonObject();
        }catch (HttpClientErrorException e){
            if (e.getRawStatusCode() == 401) {
                SpringBootKcbRestApiApplication.accessToken = generateOauth2Token();
                fetchLoansSoldByStaffNo(staffNo);
            }

        }
        return null;
    }

    @Override
    public JsonObject fetchDeposistsByStaffNo(String staffNo) {
        String token="Bearer "+ generateOauth2Token();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",token);
        headers.set("Prefer", "return=representation");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        log.info("in fetchDeposistsByStaffNo ");
        ResponseEntity<String> responseEntity = null;
        try {

            responseEntity = restTemplate.exchange(environment.getProperty("endpoints.crm.fetchDeposistsByStaffNo") + staffNo, HttpMethod.GET, entity, String.class);
            return JsonParser.parseString(responseEntity.getBody()).getAsJsonObject();
        }catch (HttpClientErrorException e){
            if (e.getRawStatusCode() == 401) {
                SpringBootKcbRestApiApplication.accessToken = generateOauth2Token();
                fetchDeposistsByStaffNo(staffNo);
            }

        }
        return null;
    }

    @Override
    public JsonObject fetchInsuranceSoldByStaffNo(String staffNo) {
        String token="Bearer "+ generateOauth2Token();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",token);
        headers.set("Prefer", "return=representation");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        log.info("in fetchInsuranceSoldByStaffNo ");
        ResponseEntity<String> responseEntity = null;
        try {

            responseEntity = restTemplate.exchange(environment.getProperty("endpoints.crm.fetchInsuranceSoldByStaffNo") + staffNo, HttpMethod.GET, entity, String.class);
            return JsonParser.parseString(responseEntity.getBody()).getAsJsonObject();
        }catch (HttpClientErrorException e){
            if (e.getRawStatusCode() == 401) {
                SpringBootKcbRestApiApplication.accessToken = generateOauth2Token();
                fetchInsuranceSoldByStaffNo(staffNo);
            }

        }

        return null;
    }

    @Override
    public JsonObject fetchCreditCardsSoldByStaffNo(String staffNo) {
        String token = "Bearer "+ generateOauth2Token();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",token);
        headers.set("Prefer", "return=representation");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        log.info("in fetchCreditCardsSoldByStaffNo ");
        ResponseEntity<String> responseEntity = null;
        try {

            responseEntity = restTemplate.exchange(environment.getProperty("endpoints.crm.fetchCreditCardsSoldByStaffNo") + staffNo, HttpMethod.GET, entity, String.class);
            return JsonParser.parseString(responseEntity.getBody()).getAsJsonObject();
        }catch (HttpClientErrorException e){
            if (e.getRawStatusCode() == 401) {
                SpringBootKcbRestApiApplication.accessToken = generateOauth2Token();
                fetchCreditCardsSoldByStaffNo(staffNo);
            }

        }
        return null;
    }

    @Override
    public JsonObject fetchVoomaAccountsSoldByStaffNo(String staffNo) {
        String token="Bearer "+ generateOauth2Token();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",token);
        headers.set("Prefer", "return=representation");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        log.info("in fetchVoomaAccountsSoldByStaffNo ");
        ResponseEntity<String> responseEntity = null;
        try {

            responseEntity = restTemplate.exchange(environment.getProperty("endpoints.crm.fetchVoomaAccountsSoldByStaffNo") + staffNo, HttpMethod.GET, entity, String.class);
            return JsonParser.parseString(responseEntity.getBody()).getAsJsonObject();
        }catch (HttpClientErrorException e){
            if (e.getRawStatusCode() == 401) {
                SpringBootKcbRestApiApplication.accessToken = generateOauth2Token();
                fetchVoomaAccountsSoldByStaffNo(staffNo);
            }

        }
        return null;
    }

    @Override
    public ArrayNode getForexRates() {
        //use currecy exchange api to get forex rates use web client
        try {
            ObjectMapper mapper = new ObjectMapper();
            ArrayNode arrayNode = mapper.createArrayNode();
            ObjectNode node = mapper.createObjectNode();
            node.put("currency", "USD");
            node.put("buying", 100);
            node.put("selling", 100);
            node.put("currencyName","US Dollar");
            arrayNode.add(node);
            return arrayNode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayNode loadForexCounterRates(NegotionRateRequest model) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ArrayNode arrayNode = mapper.createArrayNode();
            ObjectNode node = mapper.createObjectNode();
            node.put("currency", "USD");
            node.put("buying", 100);
            node.put("selling", 100);
            node.put("currencyName","US Dollar");
            arrayNode.add(node);
            return arrayNode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JsonObject getCustomerDetails(long accountNo) {
//        String token = "Bearer "+ generateOauth2Token();
        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization",token);
        String url =GET_CUSTOMER_DETAILS_BY_ACCOUNT_NO+accountNo;

        HttpEntity<?> entity = new HttpEntity<>(headers);

        try {
            //log.info("getCustomerDetails ",accountNo);
            ResponseEntity<String> responseEntity = null;


            responseEntity = restTemplate.exchange(url, HttpMethod.GET,entity,String.class);
            return JsonParser.parseString(responseEntity.getBody()).getAsJsonObject();
        }catch (HttpClientErrorException e){

            e.printStackTrace();
            if (e.getRawStatusCode() == 401) {
                SpringBootKcbRestApiApplication.accessToken = generateOauth2Token();

                return getCustomerDetails(accountNo);
            }
        }

        return null;
    }



}

