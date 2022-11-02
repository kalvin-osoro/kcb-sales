package com.deltacode.kcb.repository;

import com.deltacode.kcb.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
