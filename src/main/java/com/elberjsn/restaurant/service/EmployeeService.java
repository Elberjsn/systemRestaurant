package com.elberjsn.restaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elberjsn.restaurant.models.Employee;
import com.elberjsn.restaurant.repository.EmployeeRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public Employee save(Employee employee){
        return employeeRepository.save(employee);
    }
    public Employee employeeByCpf(String cpf){
        return employeeRepository.findByCpf(cpf).orElseThrow(()-> new EntityNotFoundException("Funcionario não Emcontrado!"));
    }

    public Employee employeeByName(String name){
        return employeeRepository.findByName(name).orElseThrow(()-> new EntityNotFoundException("Funcionario não Emcontrado!"));
    }
    public Employee editEmployee(Employee e){
        var newEmploye = employeeByCpf(e.getCpf());
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
