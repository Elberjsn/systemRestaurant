package com.elberjsn.restaurant.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elberjsn.restaurant.models.Employee;
import java.util.List;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long>{
    Optional<Employee> findByCpf(String cpf);
    Optional<Employee> findByName(String name);
    List<Employee> findByOnline(Boolean online);
}
