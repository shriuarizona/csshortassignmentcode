package com.example;

public class Salad extends Food {

    public Salad() {
        this.price = 5.50;
        this.label = "Fresh Garden Salad";
    }

    @Override
    public double getPrice() {
        return this.price;
    }
}
