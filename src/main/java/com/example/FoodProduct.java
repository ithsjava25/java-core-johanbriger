package com.example;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.UUID;

public class FoodProduct extends Product implements Perishable, Shippable {

    private final LocalDate expirationDate;
    private final BigDecimal weight;

    public FoodProduct(UUID id, String name,Category category, BigDecimal price, LocalDate expirationDate, BigDecimal weight) {
        super(id,name,category,price);

        if(price == null || price.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Price cannot be negative.");}
        if(weight == null || weight.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Weight cannot be negative.");
        }
        if(expirationDate == null) {
            throw new IllegalArgumentException("Expiration Date can not be null");
        }

        this.expirationDate = expirationDate;
        this.weight = weight;

    }


    @Override
    public LocalDate expirationDate() {
        return expirationDate;
    }

    @Override
    public boolean isExpired() {
        return Perishable.super.isExpired();
    }


    @Override
    public double weight() {
        return weight.doubleValue();
    }

    @Override
    public BigDecimal calculateShippingCost() {
        return weight.multiply(BigDecimal.valueOf(50)).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String productDetails() {
        // Format: "Food: Milk, Expires: 2025-12-24"
        return String.format("Food: %s, Expires: %s", name(), expirationDate());
    }


}
