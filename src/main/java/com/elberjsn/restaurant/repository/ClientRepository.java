package com.elberjsn.restaurant.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elberjsn.restaurant.models.Client;

public interface ClientRepository extends JpaRepository<Client,Long> {
    List<Client> findAllClientInRestaurantId(Long id);
    Optional<Client> findByPhone(String phone);
    Optional<Client> findByEmail(String email);

}
