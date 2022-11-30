package com.ekenya.rnd.backend.fskcb.CrmAdapters.services.vooma;

import com.ekenya.rnd.backend.fskcb.CrmAdapters.models.VoomTillRequest;
import com.ekenya.rnd.backend.fskcb.CrmAdapters.models.VoomaPaybillRequest;
import com.ekenya.rnd.backend.fskcb.CrmAdapters.services.ICRMService;

public interface IVoomaCRMAdapter extends ICRMService {

    /**
     * create a Vooma Paybill product
     */

    boolean createVoomaPaybill(VoomaPaybillRequest model);

    boolean createVoomaTill(VoomTillRequest model);

    void processCreateVoomaTillResult(String result);

    boolean searchVoomaCustomer(String customerID);

    void processVoomaCustomerSearchResult(String result);
}
