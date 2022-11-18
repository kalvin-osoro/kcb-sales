package com.ekenya.rnd.backend.fskcb.AdminModule.services;

import com.ekenya.rnd.backend.fskcb.AdminModule.AddBranchRequest;
import com.ekenya.rnd.backend.fskcb.AdminModule.datasource.entities.BranchEntity;
import com.ekenya.rnd.backend.fskcb.AdminModule.datasource.repositories.IBranchesRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.util.List;

@Slf4j
@Service
public class SettingsService implements ISettingsService {
    @Autowired
    ObjectMapper mObjectWrapper;
    @Autowired
    IBranchesRepository branchesRepository;
    @Autowired
    private DateFormat dateFormat;

    @Override
    public boolean createBranch(AddBranchRequest model) {

        try{

            if(!(branchesRepository.existsByName(model.getName()) || branchesRepository.existsByCode(model.getName()))){
                //
                BranchEntity branchEntity = new BranchEntity();
                branchEntity.setName(model.getName());
                branchEntity.setCode(model.getCode());
                //
                branchesRepository.save(branchEntity);
            }else{
                //Already exist...
            }
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }
        return false;
    }

    @Override
    public ArrayNode loadAllBranches() {

        try{

            ArrayNode list = mObjectWrapper.createArrayNode();

            for(BranchEntity e : branchesRepository.findAll()){
                //
                ObjectNode node = mObjectWrapper.createObjectNode();
                node.put("code",e.getCode());
                node.put("name",e.getName());
                node.put("id",e.getId());
                list.add(node);
            }
            return list;
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }

        return null;
    }

    @Override
    public ObjectNode getById(Long branchId) {
        try{
            BranchEntity branch = branchesRepository.findById(branchId).get();

            ObjectNode node = mObjectWrapper.createObjectNode();
            node.put("code",branch.getCode());
            node.put("name",branch.getName());
            node.put("id",branch.getId());
            node.put("dateCreated",dateFormat.format(branch.getDateCreated()));
            node.put("status",branch.getId());

            return node;
        }catch (Exception ex){

        }
        return null;
    }
}
