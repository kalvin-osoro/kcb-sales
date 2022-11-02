package com.deltacode.kcb.service;

import com.deltacode.kcb.payload.PersonalAccountTypeRequest;
import com.deltacode.kcb.payload.ProductRequest;
import org.springframework.http.ResponseEntity;

public interface ProductService {
    ResponseEntity<?> getAllProductCategory();
    ResponseEntity<?> createProductCategory(ProductRequest productRequest);
    ResponseEntity<?> editProductCategory(PersonalAccountTypeRequest personalAccountTypeRequest);
    ResponseEntity<?> deleteProductCategory(long id);
}
