package com.ekenya.rnd.backend.fskcb.CrmAdapter;

import com.google.gson.JsonObject;

public interface ICRMService {
    String generateOauth2Token();
    JsonObject createCustomer(JsonObject jsonObject);
    JsonObject createLoan(JsonObject jsonObject);
    JsonObject createLead(JsonObject jsonObject);

    JsonObject getCustomerDetails(String accountNo);
    JsonObject getLoanDetails(String crmAccountId);
    JsonObject getLead(String crmUserId);
    JsonObject getCustomerRefNo(String accountNumber);
}
