package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.AgencyProductRequest;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AgencySetupVC {
    @RequestMapping(value = "/agency-setups-add-product",method = RequestMethod.POST)
    public RequestEntity<?>addDFSProduct(@RequestBody AgencyProductRequest productRequest){
        return null;
    }
    @RequestMapping(value = "agency-setups-get-all-products",method = RequestMethod.GET)
    public ResponseEntity<?>getAllProducts(){
        return null;
    }
    //edit product
    @RequestMapping(value = "/agency-setups-edit-product",method = RequestMethod.PUT)
    public RequestEntity<?>editProduct(@RequestBody AgencyProductRequest productRequest){
        return null;
    }
    //delete product
    @RequestMapping(value = "/agency-setups-delete-product",method = RequestMethod.DELETE)
    public RequestEntity<?>deleteProduct(@RequestBody AgencyProductRequest productRequest){
        return null;
    }
}
