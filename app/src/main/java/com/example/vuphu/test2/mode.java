package com.example.vuphu.test2;

import java.io.Serializable;

public class mode implements Serializable {

    private String name;
    private String text;
    private int color;

    private int[] temp;
    public mode() {
    }

    public mode(String name, String text, int color, int[] temp) {
        this.name = name;
        this.text = text;
        this.color = color;
        this.temp = temp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int[] getTemp() {
        return temp;
    }

    public void setTemp(int[] temp) {
        this.temp = temp;
    }
}
