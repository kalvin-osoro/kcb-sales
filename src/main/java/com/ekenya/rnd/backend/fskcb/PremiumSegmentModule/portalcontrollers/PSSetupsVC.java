package com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.Payload.PremiumProductRequest;
import com.ekenya.rnd.backend.fskcb.payload.ProductRequest;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/premium-segment-setup")
public class PSSetupsVC {
    @RequestMapping(value = "/create-product",method = RequestMethod.POST)
    public RequestEntity<?> addPremiumProduct(@RequestBody PremiumProductRequest premiumProductRequest){
        return null;
    }
    @RequestMapping(value = "/get-all-premium-product",method = RequestMethod.GET)
    public RequestEntity<?> getAllPremiumProducts(){
        return null;
    }
 //delete premium product
    @RequestMapping(value = "/delete-premium-product",method = RequestMethod.DELETE)
    public RequestEntity<?> deletePremiumProduct(@RequestBody ProductRequest productRequest){
        return null;
    }
    //edit premium product
    @RequestMapping(value = "/edit-premium-product",method = RequestMethod.PUT)
    public RequestEntity<?> editPremiumProduct(@RequestBody PremiumProductRequest premiumProductRequest){
        return null;
    }

}
