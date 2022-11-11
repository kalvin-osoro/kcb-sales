package com.ekenya.rnd.backend.fskcb.PersonalBankingModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.payload.ProductRequest;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1")
public class PBSetupVC {
    @RequestMapping(value = "/pb-add-product",method = RequestMethod.POST)
    public RequestEntity<?>addPersonalBankingProduct(@RequestBody ProductRequest productRequest){
        return null;
    }
    @RequestMapping(value = "/pb-get-all-product",method = RequestMethod.GET)
    public RequestEntity<?>getAllProducts(){
        return null;
    }
    //edit product
    @RequestMapping(value = "/pb-edit-product",method = RequestMethod.PUT)
    public RequestEntity<?>editPersonalBankingProduct(@RequestBody ProductRequest productRequest){
        return null;
    }
    //delete product
    @RequestMapping(value ="pb-delete-product",method = RequestMethod.DELETE)
    public RequestEntity<?>deletePersonalBankingProduct(@RequestBody ProductRequest productRequest){
        return null;
        }

}
