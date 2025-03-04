package com.elberjsn.restaurant.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.elberjsn.restaurant.models.Employee;
import com.elberjsn.restaurant.repository.EmployeeRepository;

import jakarta.persistence.EntityNotFoundException;

public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public Employee save(Employee employee){
        return employeeRepository.save(employee);
    }
    public Employee employeeByCpf(String cpf,Long restaurant){
        return employeeRepository.findByCpfInRestaurantId(cpf,restaurant).orElseThrow(()-> new EntityNotFoundException("Funcionario não Emcontrado!"));
    }

    public Employee employeeByName(String name,Long restaurant){
        return employeeRepository.findByNameInRestaurantId(name,restaurant).orElseThrow(()-> new EntityNotFoundException("Funcionario não Emcontrado!"));
    }
    public Employee editEmployee(Employee e){
        var newEmploye = employeeByCpf(e.getCpf(), e.getRestaurant().getId());
        newEmploye.setPosition(e.getPosition());
        newEmploye.setName(e.getName());
        newEmploye.setPassword(e.getPassword());
        newEmploye.setPhone(e.getPhone());
        newEmploye.setRestaurant(e.getRestaurant());
        newEmploye.setSalary(e.getSalary());
        return employeeRepository.save(newEmploye);
    }

    public void deletEmployee(Employee e){
        employeeRepository.delete(e);
    }
}
