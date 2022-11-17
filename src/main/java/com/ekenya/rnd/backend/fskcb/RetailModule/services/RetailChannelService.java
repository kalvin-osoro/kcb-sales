package com.ekenya.rnd.backend.fskcb.RetailModule.services;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RetailChannelService implements IRetailChannelService {
    @Override
    public ObjectNode loadDSRAnalytics(String staffNo) {
        return null;
    }

    @Override
    public ObjectNode loadDSRGraphData(String staffNo) {
        return null;
    }

    @Override
    public ObjectNode searchCustomer(String accountNo) {
        return null;
    }
}
