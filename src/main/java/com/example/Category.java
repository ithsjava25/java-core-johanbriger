package com.example;

import java.util.HashMap;

public final class Category {

    private static final HashMap<String, Category> CACHE = new HashMap<>();
    private final String name;

    private Category(String name) {
        this.name = name;
    }

    public static Category of(String name) {
        if(name == null) {
            throw new IllegalArgumentException("Category name can't be null");
        }
        String trimmedName = name.trim();
        if(trimmedName.isEmpty()) {
            throw new IllegalArgumentException("Category name can't be blank");
        }

        String normalizeName = trimmedName.substring(0,1).toUpperCase() +
                trimmedName.substring(1).toLowerCase();
        if(!CACHE.containsKey(normalizeName)) {
            CACHE.put(normalizeName, new Category(normalizeName));
        }
        return CACHE.get(normalizeName);
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return name.equals(category.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
