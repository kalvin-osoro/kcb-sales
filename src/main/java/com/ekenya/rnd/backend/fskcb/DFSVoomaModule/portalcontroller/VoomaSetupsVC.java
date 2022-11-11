package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.portalcontroller;

import com.ekenya.rnd.backend.fskcb.payload.ProductRequest;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VoomaSetupsVC {
    @RequestMapping(value = "/vooma-add-product",method = RequestMethod.POST)
    public RequestEntity<?>addDFSProduct(@RequestBody ProductRequest productRequest){
        return null;
    }
    @RequestMapping(value = "/vooma-get-all-products",method = RequestMethod.GET)
    public ResponseEntity<?>getAllProducts(){
        return null;
    }
    //edit product
    @RequestMapping(value = "/vooma-edit-product",method = RequestMethod.PUT)
    public RequestEntity<?>editProduct(@RequestBody ProductRequest productRequest){
        return null;
    }
    //delete product
    @RequestMapping(value = "/vooma-delete-product",method = RequestMethod.DELETE)
    public RequestEntity<?>deleteProduct(@RequestBody ProductRequest productRequest){
        return null;
    }
}
