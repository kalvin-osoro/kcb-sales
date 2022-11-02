package com.deltacode.kcb.RetailModule.controllers;

import com.deltacode.kcb.RetailModule.Payload.RetailProductRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController("/retail-setup")
public class RetailSetupController {
    @RequestMapping(value = "/add-retail-product",method = RequestMethod.POST)
    public ResponseEntity<?>addRetailProduct(@RequestBody RetailProductRequest retailProductRequest){
        return null;
    }
    @RequestMapping(value = "/get-all-retail-product",method = RequestMethod.GET)
    public ResponseEntity<?>getAllRetailProducts(){
        return null;
    }
    // delete retail product
    @RequestMapping(value = "/delete-retail-product",method = RequestMethod.DELETE)
    public ResponseEntity<?>deleteRetailProduct(@RequestBody RetailProductRequest retailProductRequest){
        return null;
    }
    //update retail product
    @RequestMapping(value = "/update-retail-product",method = RequestMethod.PUT)
    public ResponseEntity<?>updateRetailProduct(@RequestBody RetailProductRequest retailProductRequest){
        return null;
    }
}
