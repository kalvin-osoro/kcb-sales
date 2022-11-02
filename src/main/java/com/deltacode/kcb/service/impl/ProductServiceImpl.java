package com.deltacode.kcb.service.impl;

import com.deltacode.kcb.entity.Product;
import com.deltacode.kcb.exception.MessageResponse;
import com.deltacode.kcb.payload.PersonalAccountTypeRequest;
import com.deltacode.kcb.payload.ProductRequest;
import com.deltacode.kcb.repository.ProductRepository;
import com.deltacode.kcb.service.ProductService;
import com.deltacode.kcb.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ProductServiceImpl implements ProductService {
    private static final Logger logger = LoggerFactory.getLogger(PersonalAccountTypeServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ResponseEntity<?> getAllProductCategory() {
        logger.info("getAllProductCategory method call...");
        LinkedHashMap<String, Object> responseObject = new LinkedHashMap<>();
        LinkedHashMap<String, Object> responseParams = new LinkedHashMap<>();
        try {
            List<Product> listProductCategory = productRepository.findAll();
            if (listProductCategory.isEmpty()) {
                responseObject.put("status", "success");
                responseObject.put("message", "No Product category available");
                responseParams.put("productCategories",listProductCategory);
                responseObject.put("data", responseParams);
            }else{
                responseObject.put("status", "success");
                responseObject.put("message", "No Product category available");
                responseParams.put("productCategories",listProductCategory);
                responseObject.put("data", responseParams);
            }
            return ResponseEntity.ok().body(responseObject);
        }catch (Exception e){
            return ResponseEntity.ok().body(new MessageResponse(e.getMessage(),"failed"));
        }
    }

    @Override
    public ResponseEntity<?> createProductCategory(ProductRequest productRequest) {
        ConcurrentHashMap<String, Object> responseObject = new ConcurrentHashMap<>();
        ConcurrentHashMap<String, Object> responseParams = new ConcurrentHashMap<>();
        try {
            logger.info("Product request "+productRequest.toString());
            if(productRequest==null) throw new RuntimeException("Bad request");
            Product product = new Product();
            product.setProductCategory(productRequest.getProductCategory());
            product.setProductName(productRequest.getProductName());
            product.setProductCode(productRequest.getProductCode());
            product.setDescription(productRequest.getDescription());
            product.setProductCategory(productRequest.getProductCategory());
            product.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            logger.info("Product details before saving "+product.toString());
            productRepository.save(product);
            responseObject.put("status", "success");
            responseObject.put("message", "Product Name "
                    +productRequest.getProductName()+" successfully created");
            responseObject.put("data", responseParams);
            return ResponseEntity.ok().body(responseObject);
        }catch (Exception e){
            responseObject.put("status", "failed");
            responseObject.put("message", e.getMessage());
            return ResponseEntity.ok().body(responseObject);
        }    }

    @Override
    public ResponseEntity<?> editProductCategory(PersonalAccountTypeRequest personalAccountTypeRequest) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteProductCategory(long id) {
        return null;
    }
}
