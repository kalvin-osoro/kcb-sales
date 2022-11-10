package com.ekenya.rnd.backend.fskcb.controller;

import com.ekenya.rnd.backend.fskcb.payload.PersonalAccountTypeRequest;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.services.PersonalAccountTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/personal-account-type")
public class PersonalAccountTypeController {
    private final PersonalAccountTypeService personalAccountTypeService;

    public PersonalAccountTypeController(PersonalAccountTypeService personalAccountTypeService) {
        this.personalAccountTypeService = personalAccountTypeService;
    }
    //create personal account type
    @PostMapping()
    public ResponseEntity<?> createPersonalAccountType(@RequestBody PersonalAccountTypeRequest personalAccountTypeRequest){
        return personalAccountTypeService.createPersonalAccountType(personalAccountTypeRequest);
    }
    //edit personal account type
    @PutMapping("/{id}")
    public ResponseEntity<?> editPersonalAccountType(@PathVariable long id, @RequestBody PersonalAccountTypeRequest personalAccountTypeRequest){
        return personalAccountTypeService.editPersonalAccountType(personalAccountTypeRequest);
    }
    //delete personal account type
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePersonalAccountType(@PathVariable long id){
        return personalAccountTypeService.deletePersonalAccountType(id);
    }
    //get all personal account types
    @GetMapping()
    public ResponseEntity<?> getAllAccountTypes(){
        return personalAccountTypeService.getAllAccountTypes();
    }

}
