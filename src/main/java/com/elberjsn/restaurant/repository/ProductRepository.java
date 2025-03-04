package com.elberjsn.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elberjsn.restaurant.models.Product;
import java.util.List;


public interface ProductRepository extends JpaRepository<Product,Long>{
    List<Product> findByNameContainingInRestaurantId(String name,Long id);
    List<Product> findByRestaurantId(Long restaurant);

}
