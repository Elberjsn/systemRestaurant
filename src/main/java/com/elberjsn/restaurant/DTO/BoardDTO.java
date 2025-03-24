package com.elberjsn.restaurant.DTO;


import lombok.Data;

@Data
public class BoardDTO {
    private int number;
    private int capacity;

    public BoardDTO(int number,int capacity) {
        this.number=number;
        this.capacity=capacity;
    }
}
