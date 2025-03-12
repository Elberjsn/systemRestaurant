package com.elberjsn.restaurant.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elberjsn.restaurant.models.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {
    Optional<Client> findByPhone(String phone);
    Optional<Client> findByEmail(String email);

}
