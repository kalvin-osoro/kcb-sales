package com.deltacode.kcb.AgencyBankingModule.controllers;

import com.deltacode.kcb.payload.ProductRequest;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController("/agency-setup")
public class AgencySetupController {
    @RequestMapping(value = "/add-product",method = RequestMethod.POST)
    public RequestEntity<?>addDFSProduct(@RequestBody ProductRequest productRequest){
        return null;
    }
    @RequestMapping(value = "/get-all-product",method = RequestMethod.GET)
    public ResponseEntity<?>getAllProducts(){
        return null;
    }
    //edit product
    @RequestMapping(value = "/edit-product",method = RequestMethod.PUT)
    public RequestEntity<?>editProduct(@RequestBody ProductRequest productRequest){
        return null;
    }
    //delete product
    @RequestMapping(value = "/delete-product",method = RequestMethod.DELETE)
    public RequestEntity<?>deleteProduct(@RequestBody ProductRequest productRequest){
        return null;
    }
}
