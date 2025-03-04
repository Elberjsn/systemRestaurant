package com.elberjsn.restaurant.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.elberjsn.restaurant.models.Product;
import com.elberjsn.restaurant.repository.ProductRepository;

import jakarta.persistence.EntityNotFoundException;

public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public Product save(Product p) {
        return productRepository.save(p);
    }

    public Product productById(Long id){
        return productRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Não Encontrada"));
    }
    public List<Product> productByName(String name,Long idRestaurant){
        return productRepository.findByNameContainingInRestaurantId(name,idRestaurant);
    }

    public List<Product> productByRestaurant(Long Id){
        return productRepository.findByRestaurantId(Id);
    }
    public Product editProduct(Product p){
        var newProduct = productById(p.getId());

        newProduct.setCategory(p.getCategory());
        newProduct.setDescrition(p.getDescrition());
        newProduct.setName(p.getName());
        newProduct.setPrice(p.getPrice());

        return productRepository.save(newProduct);
    }
    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }
}
