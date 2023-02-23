//package com.ekenya.rnd.backend.fskcb.setup.service;
//
//import com.ekenya.rnd.backend.fskcb.setup.datasource.entities.EmailEntities;
//import com.ekenya.rnd.backend.fskcb.setup.datasource.repository.EmailRepository;
//import com.ekenya.rnd.backend.fskcb.setup.model.EmailWrapper;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//@Slf4j
//@RequiredArgsConstructor
//@Service
//public class EmailServeImp implements  EmailService{
////    private final EmailRepository emailRepository;
//
////    @Override
////    public boolean addNewEmail(EmailWrapper emailWrapper) {
////        try {
////            if (emailWrapper==null){
////                return false;
////            }
//////            EmailEntities emailEntities = emailRepository.findByprofileCode(emailWrapper.getProfileCode());
////            //if emailEntities is null then create new Object
////                EmailEntities newEmailEntities = new EmailEntities();
////                newEmailEntities.setBusinessUnit(emailWrapper.getBusinessUnit());
////                newEmailEntities.setProfileCode(emailWrapper.getProfileCode());
////                newEmailEntities.setEmail(emailWrapper.getEmail());
////                emailRepository.save(newEmailEntities);
//////            else{
//////                emailEntities.setBusinessUnit(emailWrapper.getBusinessUnit());
//////                emailEntities.setProfileCode(emailWrapper.getProfileCode());
//////                emailEntities.setEmail(emailWrapper.getEmail());
//////                emailRepository.save(emailEntities);
//////            }
////
////            return true;
////        } catch (Exception e) {
////            log.error("Error adding new email",e);
////        }
////        return false;
////    }
//}
