package com.ekenya.rnd.backend.fskcb.CrmAdapters.services;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.logging.Logger;

@Service
public class CRMService implements ICRMService {

    @Resource
    public Environment environment;

    @Autowired
    private RestTemplate restTemplate;
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

        return null;
    }


    @Override
    public JsonObject createLead(JsonObject jsonObject) {
        return null;
    }

    @Override
    public JsonArray fetchStaffAccounts() {
        return null;
    }

    @Override
    public JsonObject getCustomerDetails(String accountNo) {
        return null;
    }

    @Override
    public JsonObject getLoanDetails(String crmAccountId) {
        return null;
    }

    @Override
    public JsonObject getLead(String crmUserId) {
        return null;
    }

    @Override
    public JsonObject getCustomerRefNo(String accountNumber) {
        return null;
    }

    @Override
    public JsonObject fetchAccountOpenedByStaffNo(String staffNo) {
        return null;
    }

    @Override
    public JsonObject fetchLoansSoldByStaffNo(String staffNo) {
        return null;
    }

    @Override
    public JsonObject fetchDeposistsByStaffNo(String staffNo) {
        return null;
    }

    @Override
    public JsonObject fetchInsuranceSoldByStaffNo(String staffNo) {
        return null;
    }

    @Override
    public JsonObject fetchCreditCardsSoldByStaffNo(String staffNo) {
        return null;
    }

    @Override
    public JsonObject fetchVoomaAccountsSoldByStaffNo(String staffNo) {
        return null;
    }

    @Override
    public JsonObject getForexRates() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(environment.getProperty("endpoints.crm.forexRates"),
                    String.class, entity);
            JsonObject payload = JsonParser.parseString(responseEntity.getBody()).getAsJsonObject();
            return payload;

        } catch (RestClientException e) {
            throw new RuntimeException(e);
        } catch (JsonSyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}

