package com.elberjsn.restaurant.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elberjsn.restaurant.models.Client;
import com.elberjsn.restaurant.repository.ClientRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ClientService {
    @Autowired
    ClientRepository clientRepository;

    @Transactional
    public Client save(Client client){
        return clientRepository.save(client);
    }
    public List<Client> allClient(){
        return clientRepository.findAll();
    }
    public Client clientById(Long id){
        return clientRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Cliente não encontrado"));
    }
    public Client clientByPhone(String phone){
        return clientRepository.findByPhone(phone).orElseThrow(()-> new EntityNotFoundException("Cliente não encontrado"));
    }
    public Client clientByEmail(String phone){
        return clientRepository.findByPhone(phone).orElseThrow(()-> new EntityNotFoundException("Cliente não encontrado"));
    }
    

    @Transactional
    public Client editClient(Client client){
        var newClient= clientById(client.getId());

        newClient.setEmail(client.getEmail());
        newClient.setName(client.getName());
        newClient.setPhone(client.getPhone());
        return save(newClient);
    }

}
