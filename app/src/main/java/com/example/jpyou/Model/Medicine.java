package com.example.jpyou.Model;

public class Medicine {
    private String id, name, unit, quantity, usage;

    public Medicine(String id, String name, String unit, String quantity, String usage) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.quantity = quantity;
        this.usage = usage;
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