package com.ekenya.rnd.backend.fskcb.CrmAdapters.services.vooma;

import com.ekenya.rnd.backend.fskcb.CrmAdapters.models.VoomaPaybillRequest;
import com.ekenya.rnd.backend.fskcb.CrmAdapters.services.ICRMService;

public interface IVoomaCRMAdapter extends ICRMService {

    /**
     * create a Vooma Paybill product
     */

    boolean createVoomaPaybill(VoomaPaybillRequest model);
}
