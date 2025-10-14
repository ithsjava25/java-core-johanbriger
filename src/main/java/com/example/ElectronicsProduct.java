package com.example;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

public class ElectronicsProduct extends Product implements Shippable{

    private final int warrantyMonths;
    private final BigDecimal weight;

    public ElectronicsProduct(UUID id,String name, Category category, BigDecimal price, int warrantyMonths, BigDecimal weight) {
        super (id, name, category,  price);

        if (warrantyMonths < 0) {
            throw new IllegalArgumentException("Warranty months cannot be negative.");
        }
        if (weight == null || weight.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Weight cannot be null");
        }
        this.warrantyMonths = warrantyMonths;
        this.weight = weight;
    }

    private static BigDecimal validatePrice(BigDecimal price){
        if(price == null || price.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Price cannot be null");
        }return price;
    }

    private int warrantyMonths() {
        return warrantyMonths;
    }
    @Override
    public double weight() {
        return weight.doubleValue();
    }

    @Override
    public BigDecimal calculateShippingCost() {
        BigDecimal shippingCost = BigDecimal.ZERO;

        BigDecimal cost = new BigDecimal("79");
        BigDecimal weightThreshold = new BigDecimal("5.0");

        if (weight.compareTo(weightThreshold) > 0) {
            cost = cost.add(new BigDecimal("49"));
        }
        return cost.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String productDetails() {
        return String.format("Electronics: %s, Warranty: %d months", name(), warrantyMonths);
    }
}
