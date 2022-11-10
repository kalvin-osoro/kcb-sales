package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.portalcontroller;

import com.ekenya.rnd.backend.fskcb.payload.ProductRequest;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController("/dfsSetup")
public class VoomaSetupsVC {
    @RequestMapping(value = "/add-dfs-product",method = RequestMethod.POST)
    public RequestEntity<?>addDFSProduct(@RequestBody ProductRequest productRequest){
        return null;
    }
    @RequestMapping(value = "/get-dfs-all-product",method = RequestMethod.GET)
    public ResponseEntity<?>getAllProducts(){
        return null;
    }
    //edit product
    @RequestMapping(value = "/edit-dfs-product",method = RequestMethod.PUT)
    public RequestEntity<?>editProduct(@RequestBody ProductRequest productRequest){
        return null;
    }
    //delete product
    @RequestMapping(value = "/delete-dfs-product",method = RequestMethod.DELETE)
    public RequestEntity<?>deleteProduct(@RequestBody ProductRequest productRequest){
        return null;
    }
}
