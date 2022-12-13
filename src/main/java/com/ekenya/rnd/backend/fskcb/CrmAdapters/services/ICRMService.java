package com.ekenya.rnd.backend.fskcb.CrmAdapters.services;

import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.NegotionRateRequest;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public interface ICRMService {
    String generateOauth2Token();
    JsonObject createCustomer(JsonObject jsonObject);
    JsonObject createLead(JsonObject jsonObject);
    JsonArray fetchStaffAccounts();
//    JsonObject getCustomerDetails(String accountNo);
    JsonObject getLoanDetails(String crmAccountId);
    JsonObject getLead(String crmUserId);
    JsonObject getCustomerRefNo(String accountNumber);
    /**
     * For Retail Team
     * @param staffNo
     * @return
     */
    JsonObject fetchAccountOpenedByStaffNo(String staffNo);
    /**
     * For Retail Team
     * @param staffNo
     * @return
     */
    JsonObject fetchLoansSoldByStaffNo(String staffNo);
    /**
     * For Retail Team
     * @param staffNo
     * @return
     */
    JsonObject fetchDeposistsByStaffNo(String staffNo);
    /**
     * For Retail Team
     * @param staffNo
     * @return
     */
    JsonObject fetchInsuranceSoldByStaffNo(String staffNo);
    /**
     * For Retail Team
     * @param staffNo
     * @return
     */
    JsonObject fetchCreditCardsSoldByStaffNo(String staffNo);

    /**
     * For Retail Team
     * @param staffNo
     * @return
     */
    JsonObject fetchVoomaAccountsSoldByStaffNo(String staffNo);

    public ArrayNode getForexRates();


    ArrayNode loadForexCounterRates(NegotionRateRequest model);
    JsonObject getCustomerDetails(long accountNo);

}
