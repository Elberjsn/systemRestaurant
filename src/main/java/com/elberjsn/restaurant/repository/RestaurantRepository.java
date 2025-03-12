package com.elberjsn.restaurant.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elberjsn.restaurant.models.Restaurant;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {

    Optional<Restaurant> findByCnpj(String restaurant);

    Optional<Restaurant> findByCnpjAndPassword(String cnpj, String password);
}
