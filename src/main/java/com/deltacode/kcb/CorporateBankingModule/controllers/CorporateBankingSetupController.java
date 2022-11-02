package com.deltacode.kcb.CorporateBankingModule.controllers;

import com.deltacode.kcb.payload.ProductRequest;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController("/corporate-customer-setup")
public class CorporateBankingSetupController {
    @RequestMapping(value = "/add-corporate-product", method = RequestMethod.POST)
    RequestEntity<?>addCorporateProduct(@RequestBody ProductRequest productRequest){
        return null;
    }
    @RequestMapping(value = "/get-all-corporate-products", method = RequestMethod.GET)
    RequestEntity<?>getAllCorporateProducts(){
        return null;
    }
    //edit product
    @RequestMapping(value = "/edit-corporate-product", method = RequestMethod.PUT)
    RequestEntity<?>editCorporateProduct(@RequestBody ProductRequest productRequest){
        return null;
    }
    //delete product
    @RequestMapping(value = "/delete-corporate-product", method = RequestMethod.DELETE)
    RequestEntity<?>deleteCorporateProduct(@RequestBody ProductRequest productRequest){
        return null;
    }
}
