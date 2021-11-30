package com.example.springboot.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class Hero {

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long id;
    @Min(4)
    @Max(14)
    public String name;
    @Min(0)
    @Max(30)
    public int level;
    public String ultimate;

    public Hero(long id, String name, int level, String ultimate) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.ultimate = ultimate;
    }

    public Hero() {
    }

    public Hero(String name, int level, String ultimate) {
        this.name = name;
        this.level = level;
        this.ultimate = ultimate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getUltimate() {
        return ultimate;
    }

    public void setUltimate(String ultimate) {
        this.ultimate = ultimate;
    }

    @Override
    public String toString() {
        return String.format("ID: %s | Имя: %s | Уровень: %s | Ultimate : %s",
                this.id, this.name, this.level, this.ultimate);
    }
}