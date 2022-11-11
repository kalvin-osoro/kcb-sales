package com.ekenya.rnd.backend.fskcb.AcquringModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringLeadEntity;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.repositories.IAcquiringLeadsRepository;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringAddAssetRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringAssignLeadRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor

@Service
public class AcquiringService implements IAcquiringService{

    private IAcquiringLeadsRepository mLeadsRepo;
    AcquiringService(IAcquiringLeadsRepository leadsRepo){
        this.mLeadsRepo = leadsRepo;
    }
    @Override
    public boolean assigneLeadtoDSR(AcquiringAssignLeadRequest model) {



        //TODO;

        return false;
    }

    @Override
    public List<ObjectNode> loadTargets() {
        return null;
    }

    @Override
    public boolean addAsset(AcquiringAddAssetRequest model) {
        return false;
    }

    @Override
    public List<ObjectNode> loadAssets() {
        return null;
    }

    @Override
    public List<ObjectNode> loadQuestionnaires() {

        try{


            //
            List<ObjectNode> items = new ArrayList<>();

            for (AcquiringLeadEntity e: mLeadsRepo.findAll()) {

                ObjectNode node = new ObjectMapper().createObjectNode();

                node.put("id",e.getId());
//                node.put("id",e.getId());
//                node.put("id",e.getId());
//                node.put("id",e.getId());
//                node.put("id",e.getId());



                //
                items.add(node);
            }

            return items;
        }catch (Exception ex){

        }
        //
        return null;
    }
    //
}
