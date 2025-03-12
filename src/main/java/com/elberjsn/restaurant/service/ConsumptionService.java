package com.elberjsn.restaurant.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elberjsn.restaurant.models.Consumption;
import com.elberjsn.restaurant.repository.ConsumptionRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ConsumptionService {

    @Autowired
    ConsumptionRepository consumptionRepository;
@Transactional
    public Consumption createConsumption(Consumption consumption){
        return consumptionRepository.save(consumption);
    }
    public List<Consumption> allConsumption(){
        return consumptionRepository.findAll();
    }
   
    public Consumption consumptionById(Long id){
        return consumptionRepository.findById(id).orElseThrow(
            ()-> new EntityNotFoundException("Consumo não encontrado!")
        );
    }
    
    @Transactional
    public Consumption editConsumption(Consumption consumption){
        var newConsumption = consumptionById(consumption.getId());

        newConsumption.setItem(consumption.getItem());
        newConsumption.setObservations(consumption.getObservations());
        newConsumption.setQuantity(consumption.getQuantity());
        newConsumption.setStatus(consumption.getStatus());
        newConsumption.setValueUnit(consumption.getValueUnit());
        newConsumption.setValueUnit(consumption.getValueUnit() * consumption.getQuantity());
        
        return createConsumption(consumption);
    }
    
    @Transactional
    public void deleteConsumption(Consumption consumption){
        consumptionRepository.deleteById(consumption.getId());
    }

    public Double valueTotalByControlId(Long id){
        return consumptionRepository.findByControlId(id).stream().mapToDouble(Consumption::getValueTotal).sum();
    }
}
