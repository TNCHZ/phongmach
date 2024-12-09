package com.example.jpyou.Model;

import androidx.annotation.NonNull;

public class Medicine {
    private String id, name, unit;
    private String Usage, Quantity;

    public Medicine(String id, String name, String unit) {
        this.id = id;
        this.name = name;
        this.unit = unit;
    }

    public Medicine(String usage, String quantity) {
        Usage = usage;
        Quantity = quantity;
    }

    public Medicine(String id, String name, String unit, String usage, String quantity) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        Usage = usage;
        Quantity = quantity;
    }

    public String getUsage() {
        return Usage;
    }

    public void setUsage(String usage) {
        Usage = usage;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
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

    @NonNull
    @Override
    public String toString() {
        return this.name +" " + this.unit;
    }
}