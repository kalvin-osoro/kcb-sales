package com.ekenya.rnd.backend.fskcb.RetailModule.services;

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface IRetailChannelService {

    ObjectNode loadDSRAnalytics(String staffNo);
    ObjectNode loadDSRGraphData(String staffNo);

    ObjectNode searchCustomer(String accountNo);
}
