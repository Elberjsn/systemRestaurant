package com.elberjsn.restaurant.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elberjsn.restaurant.models.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {

    Optional<Restaurant> findByCnpj(String restaurant);
}
