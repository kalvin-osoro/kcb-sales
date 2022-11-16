package com.ekenya.rnd.backend.fskcb.TreasuryModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.AcquiringNearbyCustomersRequest;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TreasuryChannelService implements ITreasuryChannelService {

    private ObjectMapper mObjectMapper = new ObjectMapper();
    @Override
    public ObjectNode targetsSummary() {

        //Resp =>
        //{
        //    "visits":{
        //        "achieved":67,
        //        "target":100
        //    },
        //    "leads":{
        //        "achieved":67,
        //        "target":100
        //    },
        //    "campaigns":{
        //        "achieved":67,
        //        "target":100
        //    },
        //    "cur-comission":56000,
        //    "prev-comission":45000
        //}

        String summary = "{\"visits\":{\"achieved\":67,\"target\":100},\"leads\":{\"achieved\":67,\"target\":100},\"campaigns\":{\"achieved\":67,\"target\":100},\"cur-comission\":56000,\"prev-comission\":45000}";

        try {
            return mObjectMapper.readValue(summary, ObjectNode.class);
        } catch (JsonProcessingException e) {

        }
        return null;
    }

    @Override
    public boolean attemptCreateLead(TreasuryAddLeadRequest model) {
        //TODO;
        return false;
    }

    @Override
    public ArrayNode loadDSRLeads(String dsrId) {
        //TODO;
        return mObjectMapper.createArrayNode();
    }

    @Override
    public ArrayNode loadForexCounterRates() {
        //TODO;
        return mObjectMapper.createArrayNode();
    }

    @Override
    public ArrayNode loadForexNegotiatedRates() {
        //TODO
        return mObjectMapper.createArrayNode();
    }

    @Override
    public boolean attemptCreateTradeRequest(TreasuryTradeRequest model) {
        //TODO;
        return false;
    }

    @Override
    public ArrayNode loadDSRTradeRequests() {
        //TODO;
        return mObjectMapper.createArrayNode();
    }

    @Override
    public boolean attemptCreateNegotiationRequest(TreasuryNegRequest model) {
        //TODO
        return false;
    }

    @Override
    public ArrayNode loadDSRNegotiationRequests() {
        //TODO
        return mObjectMapper.createArrayNode();
    }

    @Override
    public ObjectNode searchCustomer(String searchQuery) {
        //TODO;
        return mObjectMapper.createObjectNode();
    }

    @Override
    public ObjectNode getNearbyCustomers(AcquiringNearbyCustomersRequest model) {
        //TODO;
        return mObjectMapper.createObjectNode();
    }

    @Override
    public ObjectNode loadSummary() {
        //TODO;
        return mObjectMapper.createObjectNode();
    }

    @Override
    public boolean attemptScheduleCustomerVisit(TreasuryCustomerVisitsRequest model) {
        return false;
    }

    @Override
    public boolean attemptRescheduleCustomerVisit(TreasuryCustomerVisitsRequest model) {
        return false;
    }

    @Override
    public ArrayNode loadCustomerVisits() {
        return null;
    }

    @Override
    public ArrayNode loadCustomerVisitQuestionnaireResponses(TreasuryCustomerVisitQuestionnaireRequest model) {
        return null;
    }
}
