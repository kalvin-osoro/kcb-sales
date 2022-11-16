package com.ekenya.rnd.backend.fskcb.TreasuryModule.services;

import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Slf4j
@Service
public class TreasuryPortalService implements ITreasuryPortalService {

    private ObjectMapper mObjectMapper = new ObjectMapper();
    @Override
    public ArrayNode loadTargetsSummary(TreasurySummaryRequest model) {
//TODO;
        //Expected Response structure
        //Take last 7 days
        //[{
        //    "target_id":"",
        //    "target_name":"",
        //    "target_value":"",
        //    "target_achieved":"", //Sum of value achieved by department staff
        //    "start_date":"dd-MMM-yyyy",
        //    "target_status":"active",//completed, expired
        //}]
        Date start,end;
        Calendar cal = Calendar.getInstance();
        //cal.setTime(cal.getTime());
        end = cal.getTime();
        cal.add(Calendar.DAY_OF_MONTH, -30);
        start = cal.getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        //
        String summary = "[{ \"target_id\":\"34\", \"target_name\":\"Customer Onboarding\", \"target_value\":100, \"target_achieved\":20, \"start_date\":\""+df.format(start)+"\",\"end_date\":\""+df.format(end)+"\" \"target_status\":\"active\"}]";

        try {
            return mObjectMapper.readValue(summary, ArrayNode.class);
        } catch (JsonProcessingException e) {

        }

        return null;
    }

    @Override
    public ObjectNode loadRequestsSummary(TreasurySummaryRequest model) {
        //TODO;
        //Expected Response structure
        //Take last 7 days
        //[{
        //    "total-trade-reqs":400,
        //    "approved-trade-reqs":400,
        //    "neg-reqs":500,
        //    "trade_reqs_by_cur":{
        //          "USD":500,
        //            "EURO":300,
        //                "POUND":78
        //    },
        //    "trade_reqs":[{"date":"23-10-2022","reqs":{"high":20,"mid":50, "low":300}},{"date":"24-10-2022","reqs":{"high":20,"mid":50, "low":300}},{}],
        //}]


        return mObjectMapper.createObjectNode();
    }

    @Override
    public ObjectNode loadLeadsSummary(TreasurySummaryRequest model) {

//TODO;
        //Expected Response structure
        //Take last 7 days
        //[{
        //    "leads_originated":"400",
        //    "leads_assigned":"500",
        //    "leads_open":"500",
        //    "leads_closed":"500",
        //    "open_leads_by_status":{
        //          "hot":500,
        //            "warm":300,
        //                "cold":78
        //    },
        //    "leads":[{"topic":"","latlng":{"lat":"","lng":""},"date_originated":"dd-mmm-yyyy"},{}],
        //    "our_leads_summary":["date":"dd-mmm-yyyy",{"hot":67,"warm":400,"cold":3}},{}],
        //}]

        return mObjectMapper.createObjectNode();
    }

    @Override
    public ArrayNode loadNegotiationRequests() {

        //TODO;
        return mObjectMapper.createArrayNode();
    }

    @Override
    public boolean approveNegotiationRequest() {
        //TODO;
        return false;
    }

    @Override
    public ArrayNode loadTradeRequests() {
        //TODO;
        return mObjectMapper.createArrayNode();
    }

    @Override
    public ArrayNode loadAllLeads(TreasuryLeadsListRequest model) {
        //TODO;
        return mObjectMapper.createArrayNode();
    }

    @Override
    public boolean assignLead(TreasuryAssignLeadRequest model) {
        //TODO;
        return false;
    }

    @Override
    public boolean createQuestionnaire(TreasuryAddQuestionnaireRequest model) {
        //TODO;
        return false;
    }

    @Override
    public ArrayNode loadAllQuestionnaires() {
        //TODO;
        return mObjectMapper.createArrayNode();
    }

    @Override
    public boolean createTarget(TreasuryAddTargetRequest model) {
        //TODO;
        return false;
    }

    @Override
    public ArrayNode loadAllTargets() {
        //TODO;
        return mObjectMapper.createArrayNode();
    }

    @Override
    public ArrayNode getAgentsInTarget(String targetId) {
        //TODO

        return mObjectMapper.createArrayNode();
    }

    @Override
    public ArrayNode attemptSyncTargetsWithCRM() {
        //TODO;
        return mObjectMapper.createArrayNode();
    }
}
