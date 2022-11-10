package com.ekenya.rnd.backend.fskcb.service.impl;

import com.ekenya.rnd.backend.fskcb.entity.PersonalAccountType;
import com.ekenya.rnd.backend.fskcb.exception.MessageResponse;
import com.ekenya.rnd.backend.fskcb.payload.PersonalAccountTypeRequest;
import com.ekenya.rnd.backend.fskcb.repository.PersonalAccountTypeRepository;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.services.PersonalAccountTypeService;
import com.ekenya.rnd.backend.fskcb.utils.Status;
import com.ekenya.rnd.backend.fskcb.utils.Utility;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
@Service
public class PersonalAccountTypeServiceImpl implements PersonalAccountTypeService {
    private static final Logger logger = Logger.getLogger(PersonalAccountTypeServiceImpl.class.getName());
    private final PersonalAccountTypeRepository personalAccountTypeRepository;

    public PersonalAccountTypeServiceImpl(PersonalAccountTypeRepository personalAccountTypeRepository) {
        this.personalAccountTypeRepository = personalAccountTypeRepository;
    }

    @Override
    public ResponseEntity<?> getAllAccountTypes() {
        logger.info("Getting all account types");
        HashMap<String,Object> responseObject =new HashMap<>();
        HashMap<String,Object> responseParams =new HashMap<>();
        try {


            List<PersonalAccountType> personalAccountTypeList =
                    personalAccountTypeRepository.findAllByStatus("A");
            if (
                    personalAccountTypeList.isEmpty()
            ) {
                responseObject.put("status", "error");
                responseObject.put("message", "No account types found");
                responseParams.put("accountTypes", personalAccountTypeList);
                responseObject.put("data", responseParams);
            } else {
                responseObject.put("status", "success");
                responseObject.put("message", "Account types found");
                responseParams.put("accountTypes", personalAccountTypeList);
                responseObject.put("data", responseParams);
            }

            return ResponseEntity.ok().body(responseObject);
        } catch (Exception e) {
            return ResponseEntity.ok().body(new MessageResponse(e.getMessage(),"failed"));
        }


    }

    @Override
    public ResponseEntity<?> createPersonalAccountType(PersonalAccountTypeRequest personalAccountTypeRequest) {
        logger.info("Creating personal account type");
        HashMap<String,Object>responseObject =new HashMap<>();
        HashMap<String,Object>responseParams =new HashMap<>();
        try {
            if (personalAccountTypeRequest==null) throw new RuntimeException("Bad Request");
            PersonalAccountType personalAccountType = new PersonalAccountType();
            personalAccountType.setPersonalAccountTypeName(
                    personalAccountTypeRequest.getPersonalAccountTypeName());
            personalAccountType.setCreatedBy(personalAccountTypeRequest.getCreatedBy());
            personalAccountType.setStatus(Status.ACTIVE);
            personalAccountType.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            personalAccountTypeRepository.save(personalAccountType);
            responseObject.put("status", "success");
            responseObject.put("message", "Account type created successfully" + personalAccountTypeRequest.getPersonalAccountTypeName());
            responseObject.put("data", responseParams);
            return ResponseEntity.ok().body(responseObject);
        } catch (Exception e) {
            responseObject.put("status", "failed");
            responseObject.put("message",e.getMessage());
            return ResponseEntity.ok().body(responseObject);
        }

    }

    @Override
    public ResponseEntity<?> editPersonalAccountType(PersonalAccountTypeRequest personalAccountTypeRequest) {
        logger.info("Editing personal account type");
        HashMap<String,Object>responseObject =new HashMap<>();
        HashMap<String,Object>responseParams =new HashMap<>();
        try {
            if (personalAccountTypeRequest==null) throw new RuntimeException("Bad Request");
            if (!Utility.validateStatus(personalAccountTypeRequest.getStatus()))throw new RuntimeException("Invalid status");
            Optional<PersonalAccountType> personalAccountTypeOptional =
                    personalAccountTypeRepository.findById(personalAccountTypeRequest.getId());
            PersonalAccountType personalAccountType = personalAccountTypeOptional.get();
            personalAccountType.setPersonalAccountTypeName(
                    personalAccountTypeRequest.getPersonalAccountTypeName());
            personalAccountType.setStatus(personalAccountTypeRequest.getStatus());
            personalAccountType.setUpdatedBy(personalAccountTypeRequest.getCreatedBy());
            personalAccountType.setUpdatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            personalAccountTypeRepository.save(personalAccountType);
            responseObject.put("status", "success");
            responseObject.put("message", "Account type updated successfully" + personalAccountTypeRequest.getPersonalAccountTypeName());
            responseObject.put("data", responseParams);
            return ResponseEntity.ok().body(responseObject);
        } catch (Exception e) {

            responseObject.put("message",e.getMessage());
            return ResponseEntity.ok().body(responseObject);
        }
    }

    @Override
    public ResponseEntity<?> deletePersonalAccountType(long id) {
        logger.info("Deleting personal account type");
        HashMap<String,Object>responseObject =new HashMap<>();
        HashMap<String,Object>responseParams =new HashMap<>();
        try {
            Optional<PersonalAccountType> personalAccountTypeOptional =
                    personalAccountTypeRepository.findById(id);
            PersonalAccountType personalAccountType = personalAccountTypeOptional.get();
            personalAccountType.setStatus(Status.DELETED);
            personalAccountType.setUpdatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            personalAccountTypeRepository.save(personalAccountType);
            responseObject.put("status", "success");
            responseObject.put("message", "Account type deleted successfully" + personalAccountType.getPersonalAccountTypeName());
            responseObject.put("data", responseParams);
            return ResponseEntity.ok().body(responseObject);
        } catch (Exception e) {
            responseObject.put("message",e.getMessage());
            return ResponseEntity.ok().body(responseObject);
        }
    }
}
