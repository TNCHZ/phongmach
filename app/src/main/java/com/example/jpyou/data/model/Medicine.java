package com.example.jpyou.data.model;

public class Medicine {
    private String id, name, unit;
    private String usage, quantity;

    public Medicine(String id, String name, String unit) {
        this.id = id;
        this.name = name;
        this.unit = unit;
    }

    public Medicine() {
    }

    public Medicine(String id, String name, String unit, String usage, String quantity) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.usage = usage;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}