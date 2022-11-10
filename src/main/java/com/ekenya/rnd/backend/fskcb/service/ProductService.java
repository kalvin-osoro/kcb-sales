package com.ekenya.rnd.backend.fskcb.service;

import com.ekenya.rnd.backend.fskcb.payload.PersonalAccountTypeRequest;
import com.ekenya.rnd.backend.fskcb.payload.ProductRequest;
import org.springframework.http.ResponseEntity;

public interface ProductService {
    ResponseEntity<?> getAllProductCategory();
    ResponseEntity<?> createProductCategory(ProductRequest productRequest);
    ResponseEntity<?> editProductCategory(PersonalAccountTypeRequest personalAccountTypeRequest);
    ResponseEntity<?> deleteProductCategory(long id);
}
