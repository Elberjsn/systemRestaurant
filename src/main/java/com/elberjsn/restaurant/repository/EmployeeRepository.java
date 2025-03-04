package com.elberjsn.restaurant.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elberjsn.restaurant.models.Employee;


public interface EmployeeRepository extends JpaRepository<Employee,Long>{
    Optional<Employee> findByCpfInRestaurantId(String cpf,Long id);
    Optional<Employee> findByNameInRestaurantId(String name,Long id);
}
