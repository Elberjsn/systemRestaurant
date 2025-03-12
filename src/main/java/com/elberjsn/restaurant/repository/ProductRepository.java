package com.elberjsn.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elberjsn.restaurant.models.Product;
import java.util.List;


@Repository

public interface ProductRepository extends JpaRepository<Product,Long>{
    List<Product> findByName(String name);
    List<Product> findByRestaurantId(Long restaurant);

}
