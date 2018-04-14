package com.example.vuphu.test2;

import java.io.Serializable;

public class room implements Serializable{

    private String name;
    private int temp;
    private String[] mode;

    public room() {
    }

    public room(String name, int temp, String[] mode) {
        this.name = name;
        this.temp = temp;
        this.mode = mode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public String[] getMode() {
        return mode;
    }

    public void setMode(String[] mode) {
        this.mode = mode;
    }
}
