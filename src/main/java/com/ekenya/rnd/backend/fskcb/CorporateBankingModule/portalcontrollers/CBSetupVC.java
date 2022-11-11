package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.payload.ProductRequest;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CBSetupVC {
    @RequestMapping(value = "/cb-add-product", method = RequestMethod.POST)
    RequestEntity<?>addCorporateProduct(@RequestBody ProductRequest productRequest){
        return null;
    }
    @RequestMapping(value = "/cb-get-all-products", method = RequestMethod.GET)
    RequestEntity<?>getAllCorporateProducts(){
        return null;
    }
    //edit product
    @RequestMapping(value = "/cb-edit-product", method = RequestMethod.PUT)
    RequestEntity<?>editCorporateProduct(@RequestBody ProductRequest productRequest){
        return null;
    }
    //delete product
    @RequestMapping(value = "/cb-delete-product", method = RequestMethod.DELETE)
    RequestEntity<?>deleteCorporateProduct(@RequestBody ProductRequest productRequest){
        return null;
    }
}
