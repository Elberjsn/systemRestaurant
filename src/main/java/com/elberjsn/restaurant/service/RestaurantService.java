package com.elberjsn.restaurant.service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elberjsn.restaurant.models.Restaurant;
import com.elberjsn.restaurant.repository.RestaurantRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RestaurantService {

    @Autowired
    RestaurantRepository restaurantRepository;

    public Restaurant save(Restaurant restaurant){
        return restaurantRepository.save(restaurant);
    }

    public List<Restaurant> allRestaurants(){
        return restaurantRepository.findAll();
    }
    public Restaurant restaurantById(Long id){
        return restaurantRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Restaurante Não Encontrada"));
    }
    public Long restaurantByCnpjReturnLong(String cnpj){
        return restaurantRepository.findByCnpj(cnpj).orElseThrow(()->new EntityNotFoundException("Cnpj Não Encontrada")).getId();
    }
    public Restaurant restaurantByCnpj(String cnpj){
        return restaurantRepository.findByCnpj(cnpj).orElse(new Restaurant());
    }
    public List<LocalTime> openingHours(Long idRetaurant){
        var retaurant = restaurantById(idRetaurant);
        return List.of(retaurant.getOpening(),retaurant.getClosed());
    }
    
    public Restaurant editRestaurantAll(Restaurant r){
        var newRestaurant = restaurantByCnpj(r.getCnpj());
        newRestaurant.setAddress(r.getAddress());
        newRestaurant.setEmail(r.getEmail());
        newRestaurant.setName(r.getName());
        newRestaurant.setOpening(r.getOpening());
        newRestaurant.setClosed(r.getClosed());
        newRestaurant.setPhone(r.getPhone());
        newRestaurant.setSite(r.getSite());
        newRestaurant.setTypeKitchen(r.getTypeKitchen());

        return save(newRestaurant);

    }

    public Restaurant loginRestaurant(Restaurant restaurant){
        Optional<Restaurant> login = restaurantRepository.findByCnpjAndPassword(restaurant.getCnpj(), restaurant.getPassword());
        if (login.isPresent()) {
            return login.get();
        }else{
            return null;
        }
        
    }

}
