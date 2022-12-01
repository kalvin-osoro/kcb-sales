package com.ekenya.rnd.backend.fskcb.CrmAdapters.services.vooma;

import com.ekenya.rnd.backend.fskcb.CrmAdapters.models.VoomTillRequest;
import com.ekenya.rnd.backend.fskcb.CrmAdapters.models.VoomaPaybillRequest;
import com.ekenya.rnd.backend.fskcb.CrmAdapters.services.CRMService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Call;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

//@Service
@Slf4j
public class VoomaCRMAdapter extends CRMService implements IVoomaCRMAdapter{

    private final String VOOMA_URL = "http://172.16.114.7:30001/payment/services/APIRequestMgrService";
    @Override
    public boolean createVoomaPaybill(VoomaPaybillRequest model) {

        String Version = "1.0";
        String ThirdPartyID = "";
        String CommandID = "CreateTopOrg";
        String OriginatorConversationID = java.util.UUID.randomUUID().toString().substring(0,29);
        String CallerType = "2";
        String Password = "NykEQz7IBFjYtaEzG08MmScbxSv4rPfhxZtSq10Fv5HtX4Wj8mImawjoWAspaROkgT5Wb57i8A45FEvZ5nJ4ges9ioza83eCB3WjEwuOztEc/C0ntD4YWWj/ONrfSNCDiQOspv1xjc6/wiSu73kF1d7LEPP9aDCmsxkM3tXAZDdCRp93L0aaSPjS1EWFF6tJe8eOtj2iFoO69zz3p3LVl/DU6oE2inTKnKVgTmutrmow3F/FL5CR5uE7ZFXMRX3QNu0fI4e0PD6YuCi842EKFGdYzy+k/qzcrjqINWjb9p9zLa3EgLR+1yhm6odFAo2Ko7prWnBXZvy2KDYoYZmH6hPwZV7KBTylVjAteRlX1rrS6a5WcL6Ie9HQen3dUt8CmeqfVKNkO2eGk2q0Imoku5wh8JCjy+TN3A3AdfGhrsueYOEWReTYl3W9sUotpfT9KeuxJQzR0mC17MKIatrdR0J/RqFcCshkmEVz3Ja7o/wm6LHk8+1zJvxnFY3/K+hBuJEpf1Ho512JGRdMPOdniCyicSomaZhIAKYkYhA7TA30l5oXMRdc6E1Vh91nDpLjSUQZ2fdy5o6ljFNfwfcq8ynS95E0uWnCAwbDVEs2m3onhBHmHKoyQpivfk/EvMvTdRTUu6Xn/pJ0q6AVjUB9ldBqgARjwOWPXEdXs5PA0Qo=";

        String ResultURL = "http://172.16.114.7:8001/result";

        String KeyOwner = "1";

        String TimeStamp = "20221102152345";

        String IdentifierType = "14";

        String Identifier = "Identifier";

        String SecurityCredential = "jH/U+kcMAYybyz76d3MNBHpTOhNreCtMf8uHFpgEklN6Axc90tWE50rytt2IVs5u3cLwOdq9PGuhTbHtPcguf1gDB+tN8MytGxpJziEYB2uSY1x149zSCTXItKCKJACU3qeK5qohcmHqDccA/CBMx6aGU+X7yEMRmV+kebvc7ZW+q4teYoDqzL6PaZWLLm/hMAU42/7j2Xu9oUC0uj14SlQzv1hOQoqnIileV2tipCejj63Kr6P8ZFtPNvJfz9uZUmbpBaaETwsUpCniMTnCur0FMkghdR6WDL1cJhyS8y4qs+0RG1/H6y5sUbih1/mt3+X02VqrGpa1ahxwVHV0OC/38ae6lFQIeh4xg9X6hgR/7ZnIrOvpNjEVq2wfyDDJZ+Zp28843Telch2Adl1h2+3Rwvf4bo/urdXXFZ2F+Ug6+BvXmsDN3QJjubgQ9Dd48g/Hs0L8Ieg8tjoLtwEpc//DO8beV4OmYB5cLSh4bPOuP7+8XCNV6BmGbZ31vDEAamfCHGB7xsqwrLhOL8A6PByqfdI5La6ZYztwutVfkuWhpmCdwHdWkPDIAT/GMCf2qQUQBCrUGqq/0iL0cioCU4mND3A5jT8l3B/oqW3qAYevW/NMOSEIgbIN1zVBK0f3ob1IjssvX2VQjQPoQTd0tvqcbx8yiNjEFBXghOXYo6U=";

        String ShortCode = "1636845";

        String OrganizationName = "Test Organization";

        String RuleProfileID = "62020";

        String ChargeProfileID = "10";

        String CentralOwnedAcctModel = "10000";

        String AggregatorAcctModel = "2000";

        String DocumentType = "02";

        String DocumentNumber = "NO343563";

        String Region = "054";

        String OrganizationName1 = "Test Organization";

        String PreferredNotificationChannel = "1001";

        String NotificationReceivingMSISDN = "254111111111";

        String PreferredNotificationLanguage = "en";

        String ContactType = "01";

        String ContactFirstName = "EMMANUEL";

        String ContactIDType = "01";

        String ContactIDNumber = "05545487";

        String ContactSecondName = "K";

        String ContactSurname = "KIMELI";

        String ContactPhoneNumber = "254111111112";

        String ContactEmail = "test1@gmail.com";

        String ProductID = "37";

        String EffectiveDate = "20221130";//yyyyMMdd

        String ExpiryDate = "20881231";

        String xmlString = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                "xmlns:api=\"http://cps.huawei.com/cpsinterface/api_requestmgr\" " +
                "xmlns:req=\"http://cps.huawei.com/cpsinterface/request\" " +
                "xmlns:com=\"http://cps.huawei.com/cpsinterface/common\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <api:Request>\n" +
                "         <req:Header>\n" +
                "            <req:Version>"+Version+"</req:Version>\n" +
                "            <req:CommandID>"+CommandID+"</req:CommandID>\n" +
                "            <req:OriginatorConversationID>"+OriginatorConversationID+"</req:OriginatorConversationID>\n" +
                "            <req:Caller>\n" +
                "               <req:CallerType>"+ CallerType +"</req:CallerType>\n" +
                "               <req:ThirdPartyID>"+ThirdPartyID+"</req:ThirdPartyID>\n" +
                "               <req:Password>"+Password+"</req:Password>\n" +
                "               <req:ResultURL>"+ResultURL+"</req:ResultURL>\n" +
                "            </req:Caller>\n" +
                "            <req:KeyOwner>"+KeyOwner+"</req:KeyOwner>\n" +
                "            <req:Timestamp>"+TimeStamp+"</req:Timestamp>\n" +
                "         </req:Header>\n" +
                "         <req:Body>\n" +
                "            <req:Identity>\n" +
                "               <req:Initiator>\n" +
                "                  <req:IdentifierType>"+IdentifierType+"</req:IdentifierType>\n" +
                "                  <req:Identifier>"+Identifier+"</req:Identifier>\n" +
                "                  <req:SecurityCredential>"+SecurityCredential+"</req:SecurityCredential>\n" +
                "               </req:Initiator>\n" +
                "            </req:Identity>\n" +
                "            <req:CreateTopOrgRequest>\n" +
                "               <req:ShortCode>"+ShortCode+"</req:ShortCode>\n" +
                "               <req:OrganizationName>"+OrganizationName+"</req:OrganizationName>\n" +
                "               <req:RuleProfileID>"+RuleProfileID+"</req:RuleProfileID>\n" +
                "               <!--<req:ChargeProfileID>"+ChargeProfileID+"</req:ChargeProfileID>\n" +
                "               <req:CentralOwnedAcctModel>"+CentralOwnedAcctModel+"</req:CentralOwnedAcctModel>\n" +
                "               <req:AggregatorAcctModel>"+AggregatorAcctModel+"</req:AggregatorAcctModel>-->\n" +
                "               <req:SimpleKYCUpdateData>\n" +
                "                  <req:AddField>\n" +
                "                     <com:KYCName>[KYC][Organization Details][Document Type]</com:KYCName>\n" +
                "                     <com:KYCValue>"+DocumentType+"</com:KYCValue>\n" +
                "                  </req:AddField>\n" +
                "                  <req:AddField>\n" +
                "                     <com:KYCName>[KYC][Organization Details][Document Number]</com:KYCName>\n" +
                "                     <com:KYCValue>"+DocumentNumber+"</com:KYCValue>\n" +
                "                  </req:AddField>\n" +
                "                  <req:AddField>\n" +
                "                     <com:KYCName>[KYC][Address Details][Region]</com:KYCName>\n" +
                "                     <com:KYCValue>"+Region+"</com:KYCValue>\n" +
                "                  </req:AddField>\n" +
                "                  <req:AddField>\n" +
                "                     <com:KYCName>[KYC][Organization Details][Organization Name]</com:KYCName>\n" +
                "                     <com:KYCValue>"+OrganizationName1+"</com:KYCValue>\n" +
                "                  </req:AddField>\n" +
                "                  <req:AddField>\n" +
                "                     <com:KYCName>[KYC][Contact Details][Preferred Notification Channel]</com:KYCName>\n" +
                "                     <com:KYCValue>"+PreferredNotificationChannel+"</com:KYCValue>\n" +
                "                  </req:AddField>\n" +
                "                  <req:AddField>\n" +
                "                     <com:KYCName>[KYC][Contact Details][Notification Receiving MSISDN]</com:KYCName>\n" +
                "                     <com:KYCValue>"+NotificationReceivingMSISDN+"</com:KYCValue>\n" +
                "                  </req:AddField>\n" +
                "                  <req:AddField>\n" +
                "                     <com:KYCName>[KYC][Contact Details][Preferred Notification Language]</com:KYCName>\n" +
                "                     <com:KYCValue>"+PreferredNotificationLanguage+"</com:KYCValue>\n" +
                "                  </req:AddField>\n" +
                "                  <!--<req:AddField>\n" +
                "                     <com:KYCName>[KYC][Organization Contact Details][Contact Type]</com:KYCName>\n" +
                "                     <com:KYCValue>"+ContactType+"</com:KYCValue>\n" +
                "                  </req:AddField>\n" +
                "                  <req:AddField>\n" +
                "                     <com:KYCName>[KYC][Organization Contact Details][Contact First Name]</com:KYCName>\n" +
                "                     <com:KYCValue>"+ContactFirstName+"</com:KYCValue>\n" +
                "                  </req:AddField>\n" +
                "                             <req:AddField>\n" +
                "                     <com:KYCName>[KYC][Organization Contact Details][Contact ID Type]</com:KYCName>\n" +
                "                     <com:KYCValue>"+ContactIDType+"</com:KYCValue>\n" +
                "                  </req:AddField>\n" +
                "                             <req:AddField>\n" +
                "                     <com:KYCName>[KYC][Organization Contact Details][Contact ID Number]</com:KYCName>\n" +
                "                     <com:KYCValue>"+ContactIDNumber+"</com:KYCValue>\n" +
                "                  </req:AddField>-->\n" +
                "               </req:SimpleKYCUpdateData>\n" +
                "               <!--<req:UpdateOrganizationContactDetails>\n" +
                "                  <req:AddContactRecord>\n" +
                "                     <com:ContactTypeValue>"+ContactType+"</com:ContactTypeValue>\n" +
                "                     <com:ContactFirstName>"+ContactFirstName+"</com:ContactFirstName>\n" +
                "                     <com:ContactSecondName>"+ContactSecondName+"</com:ContactSecondName>\n" +
                "                     <com:ContactSurname>"+ContactSurname+"</com:ContactSurname>\n" +
                "                     <com:ContactPhoneNumber>"+ContactPhoneNumber+"</com:ContactPhoneNumber>\n" +
                "                     <com:ContactIDTypeValue>"+ContactIDType+"</com:ContactIDTypeValue>\n" +
                "                     <com:ContactIDNumber>"+ContactIDNumber+"</com:ContactIDNumber>\n" +
                "                     <com:ContactEmail>"+ContactEmail+"</com:ContactEmail>\n" +
                "                  </req:AddContactRecord>\n" +
                "               </req:UpdateOrganizationContactDetails>-->\n" +
                "               <req:UpdateProductsData>\n" +
                "                  <req:AddProduct>\n" +
                "                     <req:ProductID>"+ProductID+"</req:ProductID>\n" +
                "                     <req:EffectiveDate>"+EffectiveDate+"</req:EffectiveDate>\n" +
                "                     <req:ExpiryDate>"+ExpiryDate+"</req:ExpiryDate>\n" +
                "                  </req:AddProduct>\n" +
                "               </req:UpdateProductsData>\n" +
                "            </req:CreateTopOrgRequest>\n" +
                "         </req:Body>\n" +
                "      </api:Request>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";

        //
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //
        HttpEntity<String> entity = new HttpEntity(xmlString);
        //
        try {
            RestTemplate restTemplate = new RestTemplate();
            //
            ResponseEntity<String> responseEntity =
                    restTemplate.postForEntity(VOOMA_URL,entity, String.class);

            //RESPONSE
            String resp = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                    "   <soapenv:Body>\n" +
                    "      <api:Response xmlns:api=\"http://cps.huawei.com/cpsinterface/api_requestmgr\" xmlns:res=\"http://cps.huawei.com/cpsinterface/response\">\n" +
                    "         <res:Header>\n" +
                    "            <res:Version>1.0</res:Version>\n" +
                    "            <res:OriginatorConversationID>0a073f37-eb69-4028-9d29-c944e</res:OriginatorConversationID>\n" +
                    "            <res:ConversationID>AG_20221130_1130484bec810bb20565</res:ConversationID>\n" +
                    "         </res:Header>\n" +
                    "         <res:Body>\n" +
                    "            <res:ResponseCode>0</res:ResponseCode>\n" +
                    "            <res:ResponseDesc>Accept the service request successfully.</res:ResponseDesc>\n" +
                    "            <res:ServiceStatus>0</res:ServiceStatus>\n" +
                    "         </res:Body>\n" +
                    "      </api:Response>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";
            //
            JSONObject payload = XML.toJSONObject(responseEntity.getBody());

            //
            int ResponseCode = payload.getJSONObject("Envelope")
                    .getJSONObject("Body")
                    .getJSONObject("Response")
                    .getJSONObject("Body").getInt("ResponseCode");
            //
            int ServiceStatus = payload.getJSONObject("Envelope")
                    .getJSONObject("Body")
                    .getJSONObject("Response")
                    .getJSONObject("Body").getInt("ResponseCode");

            //
            return ResponseCode == 0;
        }catch (Exception ex){
            log.info(ex.getMessage());
        }
        return false;
    }


    public void processVoomaPaybillResult(String result){

        String expectedResult = "<?xml version='1.0' encoding='UTF-8'?>\n" +
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "         <soapenv:Body>\n" +
                "                 <api:Result xmlns:api=\"http://cps.huawei.com/cpsinterface/api_resultmgr\"\n" +
                "                             xmlns:res=\"http://cps.huawei.com/cpsinterface/result\">\n" +
                "                          <res:Header>\n" +
                "                                  <res:Version>1.0</res:Version>\n" +
                "                                  <res:OriginatorConversationID>0a073f37-eb69-4028-9d29-c944e</res:OriginatorConversationID>\n" +
                "                                   <res:ConversationID>AG_20221130_1130484bec810bb20565</res:ConversationID>\n" +
                "                          </res:Header>\n" +
                "                          <res:Body>\n" +
                "                                  <res:ResultType>0</res:ResultType>\n" +
                "                                  <res:ResultCode>0</res:ResultCode>\n" +
                "                                  <res:ResultDesc>Process service request successfully.</res:ResultDesc>\n" +
                "                                  <res:CreateTopOrgResult>\n" +
                "                                           <res:BOCompletedTime>20221130135000</res:BOCompletedTime>\n" +
                "                                  </res:CreateTopOrgResult>\n" +
                "                          </res:Body>\n" +
                "                 </api:Result>\n" +
                "         </soapenv:Body>\n" +
                "</soapenv:Envelope>";


        //
        JSONObject payload = XML.toJSONObject(result);
        //
        int ResultCode = payload.getJSONObject("Envelope")
                .getJSONObject("Body")
                .getJSONObject("Result")
                .getJSONObject("Body")
                .getInt("ResultCode");
        //
        int ResultType = payload.getJSONObject("Envelope")
                .getJSONObject("Body")
                .getJSONObject("Result")
                .getJSONObject("Body")
                .getInt("ResultType");

        //
        JSONObject CreateTopOrgResult = payload.getJSONObject("Envelope")
                .getJSONObject("Body")
                .getJSONObject("Result")
                .getJSONObject("Body")
                .getJSONObject("CreateTopOrgResult");


    }

    @Override
    public boolean createVoomaTill(VoomTillRequest model) {


        String Version = "1.0";
        String ThirdPartyID = "POS_Broker";
        String CommandID = "CreateTill";
        String OriginatorConversationID = java.util.UUID.randomUUID().toString().substring(0,29);
        String CallerType = "2";
        String Password = "NykEQz7IBFjYtaEzG08MmScbxSv4rPfhxZtSq10Fv5HtX4Wj8mImawjoWAspaROkgT5Wb57i8A45FEvZ5nJ4ges9ioza83eCB3WjEwuOztEc/C0ntD4YWWj/ONrfSNCDiQOspv1xjc6/wiSu73kF1d7LEPP9aDCmsxkM3tXAZDdCRp93L0aaSPjS1EWFF6tJe8eOtj2iFoO69zz3p3LVl/DU6oE2inTKnKVgTmutrmow3F/FL5CR5uE7ZFXMRX3QNu0fI4e0PD6YuCi842EKFGdYzy+k/qzcrjqINWjb9p9zLa3EgLR+1yhm6odFAo2Ko7prWnBXZvy2KDYoYZmH6hPwZV7KBTylVjAteRlX1rrS6a5WcL6Ie9HQen3dUt8CmeqfVKNkO2eGk2q0Imoku5wh8JCjy+TN3A3AdfGhrsueYOEWReTYl3W9sUotpfT9KeuxJQzR0mC17MKIatrdR0J/RqFcCshkmEVz3Ja7o/wm6LHk8+1zJvxnFY3/K+hBuJEpf1Ho512JGRdMPOdniCyicSomaZhIAKYkYhA7TA30l5oXMRdc6E1Vh91nDpLjSUQZ2fdy5o6ljFNfwfcq8ynS95E0uWnCAwbDVEs2m3onhBHmHKoyQpivfk/EvMvTdRTUu6Xn/pJ0q6AVjUB9ldBqgARjwOWPXEdXs5PA0Qo=";

        String ResultURL = "http://172.16.114.7:8001/result";

        String KeyOwner = "1";

        String TimeStamp = "20221102152345";

        String IdentifierType = "14";

        String Identifier = "Identifier";

        String SecurityCredential = "jH/U+kcMAYybyz76d3MNBHpTOhNreCtMf8uHFpgEklN6Axc90tWE50rytt2IVs5u3cLwOdq9PGuhTbHtPcguf1gDB+tN8MytGxpJziEYB2uSY1x149zSCTXItKCKJACU3qeK5qohcmHqDccA/CBMx6aGU+X7yEMRmV+kebvc7ZW+q4teYoDqzL6PaZWLLm/hMAU42/7j2Xu9oUC0uj14SlQzv1hOQoqnIileV2tipCejj63Kr6P8ZFtPNvJfz9uZUmbpBaaETwsUpCniMTnCur0FMkghdR6WDL1cJhyS8y4qs+0RG1/H6y5sUbih1/mt3+X02VqrGpa1ahxwVHV0OC/38ae6lFQIeh4xg9X6hgR/7ZnIrOvpNjEVq2wfyDDJZ+Zp28843Telch2Adl1h2+3Rwvf4bo/urdXXFZ2F+Ug6+BvXmsDN3QJjubgQ9Dd48g/Hs0L8Ieg8tjoLtwEpc//DO8beV4OmYB5cLSh4bPOuP7+8XCNV6BmGbZ31vDEAamfCHGB7xsqwrLhOL8A6PByqfdI5La6ZYztwutVfkuWhpmCdwHdWkPDIAT/GMCf2qQUQBCrUGqq/0iL0cioCU4mND3A5jT8l3B/oqW3qAYevW/NMOSEIgbIN1zVBK0f3ob1IjssvX2VQjQPoQTd0tvqcbx8yiNjEFBXghOXYo6U=";

        String ShortCode = "1636845";

        String OrganizationName = "Test Organization";

        String TillNumber = "6031110";

        String LanguageCode = "en";

        String MSISDN = "254111111113";

        String ProductID = "59";

        String POSDeviceID = "254759265946";

        String requestData = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                "xmlns:api=\"http://cps.huawei.com/cpsinterface/api_requestmgr\" " +
                "xmlns:req=\"http://cps.huawei.com/cpsinterface/request\" " +
                "xmlns:com=\"http://cps.huawei.com/cpsinterface/common\" " +
                "xmlns:cus=\"http://cps.huawei.com/cpsinterface/customizedrequest\">\n" +
                "      <soapenv:Header/>\n" +
                "      <soapenv:Body>\n" +
                "         <api:Request>\n" +
                "            <req:Header>\n" +
                "               <req:Version>"+Version+"</req:Version>\n" +
                "               <req:CommandID>"+CommandID+"</req:CommandID>\n" +
                "               <req:OriginatorConversationID>>"+OriginatorConversationID+"</req:OriginatorConversationID>\n" +
                "               <req:Caller>\n" +
                "                  <req:CallerType>"+CallerType+"</req:CallerType>\n" +
                "                  <req:ThirdPartyID>"+ThirdPartyID+"</req:ThirdPartyID>\n" +
                "                  <req:Password>"+Password+"</req:Password>\n" +
                "                  <req:ResultURL>"+ResultURL+"</req:ResultURL>\n" +
                "               </req:Caller>\n" +
                "               <req:KeyOwner>"+KeyOwner+"</req:KeyOwner>\n" +
                "               <req:Timestamp>"+TimeStamp+"</req:Timestamp>\n" +
                "            </req:Header>\n" +
                "            <req:Body>\n" +
                "               <req:Identity>\n" +
                "                  <req:Initiator>\n" +
                "                     <req:IdentifierType>"+IdentifierType+"</req:IdentifierType>\n" +
                "                     <req:Identifier>"+Identifier+"</req:Identifier>\n" +
                "                     <req:SecurityCredential>"+SecurityCredential+"</req:SecurityCredential>\n" +
                "                  </req:Initiator>\n" +
                "               </req:Identity>\n" +
                "               <req:CreateTillRequest>\n" +
                "                  <req:ShortCode>"+ShortCode+"</req:ShortCode>\n" +
                "                  <req:TillNumber"+TillNumber+"</req:TillNumber>\n" +
                "                  <req:LanguageCode>"+LanguageCode+"</req:LanguageCode>\n" +
                "                  <req:SIMDevice>\n" +
                "                     <req:MSISDN>"+MSISDN+"</req:MSISDN>\n" +
                "                  </req:SIMDevice>\n" +
                "                  <req:UpdateProductsData>\n" +
                "                     <req:AddProduct>\n" +
                "                        <req:ProductID>"+ProductID+"</req:ProductID>\n" +
                "                     </req:AddProduct>\n" +
                "                  </req:UpdateProductsData>\n" +
                "               </req:CreateTillRequest>\n" +
                "               <req:ReferenceData>\n" +
                "                  <req:ReferenceItem>\n" +
                "                     <com:Key>POSDeviceID</com:Key>\n" +
                "                     <com:Value>"+POSDeviceID+"</com:Value>\n" +
                "                  </req:ReferenceItem>\n" +
                "               </req:ReferenceData>\n" +
                "            </req:Body>\n" +
                "         </api:Request>\n" +
                "      </soapenv:Body>\n" +
                "   </soapenv:Envelope>\n" +
                "";
        //
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //
        HttpEntity<String> entity = new HttpEntity(requestData);
        //
        try {
            RestTemplate restTemplate = new RestTemplate();
            //
            ResponseEntity<String> responseEntity =
                    restTemplate.postForEntity(VOOMA_URL,entity, String.class);

            //RESPONSE
            String resp = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                    "   <soapenv:Body>\n" +
                    "      <api:Response xmlns:api=\"http://cps.huawei.com/cpsinterface/api_requestmgr\" xmlns:res=\"http://cps.huawei.com/cpsinterface/response\">\n" +
                    "         <res:Header>\n" +
                    "            <res:Version>1.0</res:Version>\n" +
                    "            <res:OriginatorConversationID>>5543444f-374a-4d3a-82f5-80906</res:OriginatorConversationID>\n" +
                    "            <res:ConversationID>AG_20221130_11304aca680fe09cd3f0</res:ConversationID>\n" +
                    "         </res:Header>\n" +
                    "         <res:Body>\n" +
                    "            <res:ResponseCode>0</res:ResponseCode>\n" +
                    "            <res:ResponseDesc>Accept the service request successfully.</res:ResponseDesc>\n" +
                    "            <res:ServiceStatus>0</res:ServiceStatus>\n" +
                    "         </res:Body>\n" +
                    "      </api:Response>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";
            //
            JSONObject payload = XML.toJSONObject(responseEntity.getBody());

            //
            int ResponseCode = payload.getJSONObject("Envelope")
                    .getJSONObject("Body")
                    .getJSONObject("Response")
                    .getJSONObject("Body")
                    .getInt("ResponseCode");
            //
            int ServiceStatus = payload.getJSONObject("Envelope")
                    .getJSONObject("Body")
                    .getJSONObject("Response")
                    .getJSONObject("Body")
                    .getInt("ResponseCode");

            //
            return ResponseCode == 0;
        }catch (Exception ex){
            log.info(ex.getMessage());
        }

        return false;
    }


    public void processCreateVoomaTillResult(String result){

        String expectedResult = "<?xml version='1.0' encoding='UTF-8'?>\n" +
                "<soapenv:Envelope xmlns:soapenv=http://schemas.xmlsoap.org/soap/envelope/>\n" +
                "               <soapenv:Body>\n" +
                "                              <api:Result xmlns:api=http://cps.huawei.com/cpsinterface/api_resultmgr\n" +
                "                                          xmlns:res=http://cps.huawei.com/cpsinterface/result\n" +
                "                                          xmlns:com=http://cps.huawei.com/cpsinterface/common>\n" +
                "                                             <res:Header>\n" +
                "                                                            <res:Version>1.0</res:Version>\n" +
                "                                                            <res:OriginatorConversationID>&gt;5543444f-374a-4d3a-82f5-80906</res:OriginatorConversationID>\n" +
                "                                                            <res:ConversationID>AG_20221130_11304aca680fe09cd3f0</res:ConversationID>\n" +
                "                                             </res:Header>\n" +
                "                                             <res:Body>\n" +
                "                                                            <res:ResultType>0</res:ResultType>\n" +
                "                                                            <res:ResultCode>0</res:ResultCode>\n" +
                "                                                            <res:ResultDesc>Process service request successfully.</res:ResultDesc>\n" +
                "                                                            <res:CreateTillResult>\n" +
                "                                                                           <res:BOCompletedTime>20221130140756</res:BOCompletedTime>\n" +
                "                                                            </res:CreateTillResult>\n" +
                "                                                            <res:ReferenceData>\n" +
                "                                                                           <res:ReferenceItem>\n" +
                "                                                                                          <com:Key>POSDeviceID</com:Key>\n" +
                "                                                                                          <com:Value>254759265946</com:Value>\n" +
                "                                                                           </res:ReferenceItem>\n" +
                "                                                            </res:ReferenceData>\n" +
                "                                             </res:Body>\n" +
                "                              </api:Result>\n" +
                "               </soapenv:Body>\n" +
                "</soapenv:Envelope>";


        //
        JSONObject payload = XML.toJSONObject(result);
        //
        int ResultCode = payload.getJSONObject("Envelope")
                .getJSONObject("Body")
                .getJSONObject("Result")
                .getJSONObject("Body")
                .getInt("ResultCode");
        //
        int ResultType = payload.getJSONObject("Envelope")
                .getJSONObject("Body")
                .getJSONObject("Result")
                .getJSONObject("Body")
                .getInt("ResultType");

        //
        JSONObject CreateTillResult = payload.getJSONObject("Envelope")
                .getJSONObject("Body")
                .getJSONObject("Result")
                .getJSONObject("Body")
                .getJSONObject("CreateTillResult");

        //
        JSONObject ReferenceItem = payload.getJSONObject("Envelope")
                .getJSONObject("Body")
                .getJSONObject("Result")
                .getJSONObject("Body")
                .getJSONObject("ReferenceData")
                .getJSONObject("ReferenceItem");

    }

    public boolean searchVoomaCustomer(String customerID){

        String Version = "1.0";
        String ThirdPartyID = "POS_Broker";
        String CommandID = "QueryOrganizationInfo";
        String OriginatorConversationID = java.util.UUID.randomUUID().toString().substring(0,29);
        String CallerType = "2";
        String Password = "NykEQz7IBFjYtaEzG08MmScbxSv4rPfhxZtSq10Fv5HtX4Wj8mImawjoWAspaROkgT5Wb57i8A45FEvZ5nJ4ges9ioza83eCB3WjEwuOztEc/C0ntD4YWWj/ONrfSNCDiQOspv1xjc6/wiSu73kF1d7LEPP9aDCmsxkM3tXAZDdCRp93L0aaSPjS1EWFF6tJe8eOtj2iFoO69zz3p3LVl/DU6oE2inTKnKVgTmutrmow3F/FL5CR5uE7ZFXMRX3QNu0fI4e0PD6YuCi842EKFGdYzy+k/qzcrjqINWjb9p9zLa3EgLR+1yhm6odFAo2Ko7prWnBXZvy2KDYoYZmH6hPwZV7KBTylVjAteRlX1rrS6a5WcL6Ie9HQen3dUt8CmeqfVKNkO2eGk2q0Imoku5wh8JCjy+TN3A3AdfGhrsueYOEWReTYl3W9sUotpfT9KeuxJQzR0mC17MKIatrdR0J/RqFcCshkmEVz3Ja7o/wm6LHk8+1zJvxnFY3/K+hBuJEpf1Ho512JGRdMPOdniCyicSomaZhIAKYkYhA7TA30l5oXMRdc6E1Vh91nDpLjSUQZ2fdy5o6ljFNfwfcq8ynS95E0uWnCAwbDVEs2m3onhBHmHKoyQpivfk/EvMvTdRTUu6Xn/pJ0q6AVjUB9ldBqgARjwOWPXEdXs5PA0Qo=";

        String ResultURL = "http://172.16.114.7:8001/result";

        String KeyOwner = "1";

        String TimeStamp = "20221102152345";

        String IdentifierType = "14";

        String Identifier = "1636845";

        String SecurityCredential = "jH/U+kcMAYybyz76d3MNBHpTOhNreCtMf8uHFpgEklN6Axc90tWE50rytt2IVs5u3cLwOdq9PGuhTbHtPcguf1gDB+tN8MytGxpJziEYB2uSY1x149zSCTXItKCKJACU3qeK5qohcmHqDccA/CBMx6aGU+X7yEMRmV+kebvc7ZW+q4teYoDqzL6PaZWLLm/hMAU42/7j2Xu9oUC0uj14SlQzv1hOQoqnIileV2tipCejj63Kr6P8ZFtPNvJfz9uZUmbpBaaETwsUpCniMTnCur0FMkghdR6WDL1cJhyS8y4qs+0RG1/H6y5sUbih1/mt3+X02VqrGpa1ahxwVHV0OC/38ae6lFQIeh4xg9X6hgR/7ZnIrOvpNjEVq2wfyDDJZ+Zp28843Telch2Adl1h2+3Rwvf4bo/urdXXFZ2F+Ug6+BvXmsDN3QJjubgQ9Dd48g/Hs0L8Ieg8tjoLtwEpc//DO8beV4OmYB5cLSh4bPOuP7+8XCNV6BmGbZ31vDEAamfCHGB7xsqwrLhOL8A6PByqfdI5La6ZYztwutVfkuWhpmCdwHdWkPDIAT/GMCf2qQUQBCrUGqq/0iL0cioCU4mND3A5jT8l3B/oqW3qAYevW/NMOSEIgbIN1zVBK0f3ob1IjssvX2VQjQPoQTd0tvqcbx8yiNjEFBXghOXYo6U=";


        String requestData = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"" +
                " xmlns:api=\"http://cps.huawei.com/cpsinterface/api_requestmgr\" " +
                "xmlns:req=\"http://cps.huawei.com/cpsinterface/request\" " +
                "xmlns:com=\"http://cps.huawei.com/cpsinterface/common\">\n" +
                "  <soapenv:Header/>\n" +
                "  <soapenv:Body>\n" +
                "    <api:Request>\n" +
                "      <req:Header>\n" +
                "        <req:Version>"+Version+"</req:Version>\n" +
                "        <req:CommandID>"+CommandID+"</req:CommandID>\n" +
                "        <req:OriginatorConversationID>"+OriginatorConversationID+"</req:OriginatorConversationID>\n" +
                "        <req:Caller>\n" +
                "          <req:CallerType>"+CallerType+"</req:CallerType>\n" +
                "               <req:ThirdPartyID>"+ThirdPartyID+"</req:ThirdPartyID>\n" +
                "               <req:Password>"+Password+"</req:Password>\n" +
                "          <req:ResultURL>"+ResultURL+"</req:ResultURL>\n" +
                "        </req:Caller>\n" +
                "        <req:KeyOwner>"+KeyOwner+"</req:KeyOwner>\n" +
                "        <req:Timestamp>"+TimeStamp+"</req:Timestamp>\n" +
                "      </req:Header>\n" +
                "      <req:Body>\n" +
                "        <req:Identity>\n" +
                "          <req:Initiator>\n" +
                "            <req:IdentifierType>"+IdentifierType+"</req:IdentifierType>\n" +
                "            <req:Identifier>"+Identifier+"</req:Identifier>\n" +
                "            <req:SecurityCredential>"+SecurityCredential+"</req:SecurityCredential>\n" +
                "          </req:Initiator>\n" +
                "          <req:ReceiverParty>\n" +
                "            <req:IdentifierType>"+IdentifierType+"</req:IdentifierType>\n" +
                "            <req:Identifier>"+Identifier+"</req:Identifier>\n" +
                "          </req:ReceiverParty>\n" +
                "        </req:Identity>\n" +
                "        <req:QueryOrganizationInfoRequest>\n" +
                "        </req:QueryOrganizationInfoRequest>\n" +
                "      </req:Body>\n" +
                "    </api:Request>\n" +
                "  </soapenv:Body>\n" +
                "</soapenv:Envelope>";
        //
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //
        HttpEntity<String> entity = new HttpEntity(requestData);
        //
        try {
            RestTemplate restTemplate = new RestTemplate();
            //
            ResponseEntity<String> responseEntity =
                    restTemplate.postForEntity(VOOMA_URL,entity, String.class);

            //RESPONSE
            String resp = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                    "   <soapenv:Body>\n" +
                    "      <api:Response xmlns:api=\"http://cps.huawei.com/cpsinterface/api_requestmgr\" xmlns:res=\"http://cps.huawei.com/cpsinterface/response\">\n" +
                    "         <res:Header>\n" +
                    "            <res:Version>1.0</res:Version>\n" +
                    "            <res:OriginatorConversationID>e4b50832-bd1e-45c3-9d7e-2625d</res:OriginatorConversationID>\n" +
                    "            <res:ConversationID>AG_20221130_1130778c6fb50b7abcc9</res:ConversationID>\n" +
                    "         </res:Header>\n" +
                    "         <res:Body>\n" +
                    "            <res:ResponseCode>0</res:ResponseCode>\n" +
                    "            <res:ResponseDesc>Accept the service request successfully.</res:ResponseDesc>\n" +
                    "            <res:ServiceStatus>0</res:ServiceStatus>\n" +
                    "         </res:Body>\n" +
                    "      </api:Response>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";
            //
            JSONObject payload = XML.toJSONObject(responseEntity.getBody());

            //
            int ResponseCode = payload.getJSONObject("Envelope")
                    .getJSONObject("Body")
                    .getJSONObject("Response")
                    .getJSONObject("Body")
                    .getInt("ResponseCode");
            //
            int ServiceStatus = payload.getJSONObject("Envelope")
                    .getJSONObject("Body")
                    .getJSONObject("Response")
                    .getJSONObject("Body")
                    .getInt("ResponseCode");

            //
            return ResponseCode == 0;
        }catch (Exception ex){
            log.info(ex.getMessage());
        }
        return false;
    }

    public void processVoomaCustomerSearchResult(String result){

        String expectedResult = "<?xml version='1.0' encoding='UTF-8'?>\n" +
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "               <soapenv:Body>\n" +
                "                              <api:Result xmlns:api=\"http://cps.huawei.com/cpsinterface/api_resultmgr\" xmlns:res=\"http://cps.huawei.com/cpsinterface/result\">\n" +
                "                                             <res:Header>\n" +
                "                                                            <res:Version>1.0</res:Version>\n" +
                "                                                            <res:OriginatorConversationID>e4b50832-bd1e-45c3-9d7e-2625d</res:OriginatorConversationID>\n" +
                "                                                            <res:ConversationID>AG_20221130_1130778c6fb50b7abcc9</res:ConversationID>\n" +
                "                                             </res:Header>\n" +
                "                                             <res:Body>\n" +
                "                                                            <res:ResultType>0</res:ResultType>\n" +
                "                                                            <res:ResultCode>0</res:ResultCode>\n" +
                "                                                            <res:ResultDesc>Process service request successfully.</res:ResultDesc>\n" +
                "                                                            <res:QueryOrganizationInfoResult>\n" +
                "                                                                           <res:BOCompletedTime>20221130141626</res:BOCompletedTime>\n" +
                "                                                                           <res:OrganizationBasicData>\n" +
                "                                                                                          <res:ShortCode>1636845</res:ShortCode>\n" +
                "                                                                                          <res:OrganizationName>Cybersource Merchant</res:OrganizationName>\n" +
                "                                                                                          <res:IdentityStatus>03</res:IdentityStatus>\n" +
                "                                                                                          <res:CreationDate>20221130</res:CreationDate>\n" +
                "                                                                                          <res:TrustLevel>20</res:TrustLevel>\n" +
                "                                                                                          <res:TrustLevelName>Top Organization Level 1</res:TrustLevelName>\n" +
                "                                                                                          <res:RuleProfileID>62020</res:RuleProfileID>\n" +
                "                                                                                          <res:RuleProfileName>Top Organization Trust Level 1 Rule Profile</res:RuleProfileName>\n" +
                "                                                                                          <res:ChargeProfileID>562</res:ChargeProfileID>\n" +
                "                                                                                          <res:ChargeProfileName>Trust Level 1 Organization Launch Tariff</res:ChargeProfileName>\n" +
                "                                                                                          <res:HierarchyLevel>1</res:HierarchyLevel>\n" +
                "                                                                                          <res:HierarchyModel>None Hierarchy</res:HierarchyModel>\n" +
                "                                                                           </res:OrganizationBasicData>\n" +
                "                                                            </res:QueryOrganizationInfoResult>\n" +
                "                                             </res:Body>\n" +
                "                              </api:Result>\n" +
                "               </soapenv:Body>\n" +
                "</soapenv:Envelope>";

        //
        JSONObject payload = XML.toJSONObject(result);

        //
        JSONObject Response = payload.getJSONObject("Envelope")
                .getJSONObject("Body")
                .getJSONObject("Result")
                .getJSONObject("Body")
                .getJSONObject("QueryOrganizationInfoResult");
        //
        String ShortCode = Response.getJSONObject("OrganizationBasicData")
                .getString("ShortCode");
        //
        String OrganizationName = Response.getJSONObject("OrganizationBasicData")
                .getString("OrganizationName");
        //
        String IdentityStatus = Response.getJSONObject("OrganizationBasicData")
                .getString("IdentityStatus");

        //
        String CreationDate = Response.getJSONObject("OrganizationBasicData")
                .getString("CreationDate");
        //
        String TrustLevelName = Response.getJSONObject("OrganizationBasicData")
                .getString("TrustLevelName");
        //
        String RuleProfileID = Response.getJSONObject("OrganizationBasicData")
                .getString("RuleProfileID");
        //
        String RuleProfileName = Response.getJSONObject("OrganizationBasicData")
                .getString("RuleProfileName");
        //
        String ChargeProfileID = Response.getJSONObject("OrganizationBasicData")
                .getString("ChargeProfileID");
        //
        String HierarchyLevel = Response.getJSONObject("OrganizationBasicData")
                .getString("HierarchyLevel");
        //
        String HierarchyModel = Response.getJSONObject("OrganizationBasicData")
                .getString("HierarchyModel");

        //Return to channel..
    }
}
